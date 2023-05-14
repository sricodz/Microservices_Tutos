package com.hotel.user.configs;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;


@Configuration
public class Cofig {

	@Bean
	@LoadBalanced
	public RestTemplate template() {
		RestTemplate temp = new RestTemplate();
		return temp;
	}
}
