package com.ashutoxh.buddy.buddy.controller;

import java.util.Arrays;
import java.util.Collections;
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

	@GetMapping(path="/getAllSaturdays", produces = "application/json")
	public List<WorkingSaturdays> getSaturdayForAll() {
		List<WorkingSaturdays> workList = workSatService.getWorkingSaturdays();
		Collections.sort(workList);
		return workList;
	}

	@GetMapping(path="/getAllSaturdays/{month}", produces = "application/json")
	public List<WorkingSaturdays> getSaturdayForMonth(@PathVariable String month) {
		List<WorkingSaturdays> workList = workSatService.getWorkingSaturdaysForMonth(month);
		Collections.sort(workList);
		return workList;
	}
	
	@GetMapping(path="/swapSaturdays/first/{first}/second/{second}", produces = "application/json")
	public List<WorkingSaturdays> swapSaturdays(@PathVariable String first, @PathVariable String second) {
		List<WorkingSaturdays> workList = workSatService.swapSaturdays(Arrays.asList(new String[] {first,second}));
		Collections.sort(workList);
		return workList;
	}
	
	@GetMapping(path="/admin/setAllSaturdays", produces = "application/json")
	public String setSaturdays() {
		String result = workSatService.setSaturdayDatesForYear();
		return result;
	}
}
