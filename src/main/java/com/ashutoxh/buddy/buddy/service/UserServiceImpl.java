package com.ashutoxh.buddy.buddy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ashutoxh.buddy.buddy.entity.User;

@Component
public class UserServiceImpl {

	@Autowired
	UserService userService;
	@Autowired
	WorkingSaturdayServiceImpl workSatServiceImpl;

	public List<User> getUsers(){
		return (List<User>) userService.findAll();
	}
	
	public User addUser(String name) {
		User user = userService.save(new User(name));
		workSatServiceImpl.reassignWorkingSaturday();
		return user;
	}

}
