package com.ashutoxh.buddy.buddy.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.ashutoxh.buddy.buddy.entity.User;
import com.ashutoxh.buddy.buddy.repository.UserRepository;

@Component
public interface UserService extends UserRepository {

	public List<User> getUsers();
	
	public User addUser(String name);

}
