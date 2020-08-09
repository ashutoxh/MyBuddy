package com.ashutoxh.buddy.buddy.buddycontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.ashutoxh.buddy.buddy.entity.User;
import com.ashutoxh.buddy.buddy.entity.WorkingSaturdays;
import com.ashutoxh.buddy.buddy.service.UserService;
import com.ashutoxh.buddy.buddy.service.WorkingSaturdayService;

@RestController
public class RequestController {

	@Autowired
	UserService userService;
	@Autowired
	WorkingSaturdayService workSatService;

	@GetMapping(path="/getAllUsers", produces = "application/json")
	public List<User> getUsers(){
		return userService.getUsers();
	}

	@GetMapping(path="/registerUser", produces = "application/json")
	public User registerUser(String name) {
		User user = userService.addUser(name);
		return user;
	}

	@GetMapping(path="/getAllSaturdays" ,produces = "application/json")
	public List<WorkingSaturdays> getSaturdayForAll() {
		return workSatService.getWorkingSaturdays();
	}

	@GetMapping(path="/getAllSaturdays/{month}" ,produces = "application/json")
	public List<User> getSaturdayForMonth(@PathVariable String month) {
		return null;
	}
	
	@GetMapping(path="/admin/setAllSaturdays" ,produces = "application/json")
	public String setSaturdays() {
		String result = workSatService.setSaturdayDatesForYear();
		return result;
	}
}
