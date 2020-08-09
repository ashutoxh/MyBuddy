package com.ashutoxh.buddy.buddy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ashutoxh.buddy.buddy.entity.User;
import com.ashutoxh.buddy.buddy.repository.UserRepository;

@Component
public class UserService {

	@Autowired
	UserRepository userRepository;

	public List<User> getUsers(){
		return (List<User>) userRepository.findAll();
	}
	
	public User addUser(String name) {
		User user = userRepository.save(new User(name));
		return user;
	}

}
