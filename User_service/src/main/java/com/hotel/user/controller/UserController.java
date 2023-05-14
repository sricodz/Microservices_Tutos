package com.hotel.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hotel.user.entities.User;
import com.hotel.user.service.UserService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userServ;
	
	@PostMapping
	public ResponseEntity<User> createUser(@RequestBody User user){
		User u = userServ.saveUser(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(u);
	}
	
	
	/*'CircuitBreaker':In this api we are calling other two microservices i.e hotel and rating so we r implementing 
	 * 					circuit breaker here.
	 * 'Retry' : it is used to send request to api until get response based on yml configuration attempts it retry the request,
	 * 			still you didnt get any response i.e service is down it executes the given fallBack method
	 * 'RateLimiter' : It is used for limiting the number of requests to the service, based on yml configuration the limit the 
	 * 					request is exceeded it execute the fallback method
	 * */
	@GetMapping("/{userId}")
	//@CircuitBreaker(name = "ratingHotelBreaker" , fallbackMethod = "ratingHotelFallBack")
	//@Retry(name="ratingHotelService", fallbackMethod="ratingHotelFallBack")
	@RateLimiter(name="userRateLimiter", fallbackMethod = "ratingFallBack")
	public ResponseEntity<User> getSingleUser(@PathVariable String userId){
		User u = userServ.getUser(userId);
		return ResponseEntity.ok(u);
	}
	
	//this is fallback method when service is down this method will show dummy details
	public ResponseEntity<User> ratingHotelFallBack(String userId,Exception ex){
		User u = User.builder()
					.email("dummy@gmail.com")
					.name("dummy")
					.about("It is dummy data bcz the service is down dude")
					.build();
		return ResponseEntity.ok(u);
	}
	
	@GetMapping
	public ResponseEntity<List<User>> getAllUser(){
		List<User> allUsers = userServ.getAllUser();
		return ResponseEntity.ok(allUsers);
 	}
}
