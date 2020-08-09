package com.ashutoxh.buddy.buddy.buddycontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ashutoxh.buddy.buddy.entity.User;
import com.ashutoxh.buddy.buddy.service.UserService;

@RestController
public class RequestController {

	@Autowired
	UserService userService;

	@GetMapping("/users")
	public List<User> getUsers() {
		return userService.getUsers();
	}

	@GetMapping("/register")
	public User registerUser(String name) {
		User user = userService.addUser(name);
		return user;
	}

	@GetMapping("/saturday")
	public List<User> getSaturdayForAll() {
		return null;
	}

	@GetMapping("/saturday/{month}")
	public List<User> getSaturdayForMonth(@PathVariable String month) {
		return null;
	}
}
