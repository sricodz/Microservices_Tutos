package com.hotel.user.service;

import java.util.List;

import com.hotel.user.entities.User;

public interface UserService {

	public User saveUser(User u);
	public List<User> getAllUser();
	public User getUser(String UserId);
}
