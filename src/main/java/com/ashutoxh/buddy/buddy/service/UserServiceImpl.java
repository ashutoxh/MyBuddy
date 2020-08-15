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

	public List<User> getUsers() {
		return (List<User>) userService.findAll();
	}

	public String addUser(String name) {
		User user = getExistingUserByName(name);
		if (user == null) {
			user = userService.save(new User(name));
			workSatServiceImpl.reassignWorkingSaturday(user);
			return "Added";
		}
		return "Already exists";
	}

	public String addUserWithoutReassign(String name) {
		User user = getExistingUserByName(name);
		if (user == null) {
			user = userService.save(new User(name));
			return "Added";
		}
		return "Already exists";
	}

	public String removeUser(String name) {
		User user = getExistingUserByName(name);
		if (user != null) {
			userService.deleteById(user.getId());
			workSatServiceImpl.reassignWorkingSaturday(null);
			return "Deleted";
		}
		return "User doesn't exists";
	}

	public User getExistingUserByName(String name) {
		return userService.findByName(name);
	}
}
