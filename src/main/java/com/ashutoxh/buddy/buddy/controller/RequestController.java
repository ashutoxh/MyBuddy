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
import com.ashutoxh.buddy.buddy.service.UserServiceImpl;
import com.ashutoxh.buddy.buddy.service.WorkingSaturdayServiceImpl;

@RestController
public class RequestController {

	@Autowired
	UserServiceImpl userServiceImpl;
	@Autowired
	WorkingSaturdayServiceImpl workSatServiceImpl;

	@GetMapping(path="/getAllUsers", produces = "application/json")
	public List<User> getUsers(){
		return userServiceImpl.getUsers();
	}

	@GetMapping(path="/registerUser", produces = "application/json")
	public String registerUser(String name) {
		String response = userServiceImpl.addUser(name);
		return response;
	}

	@GetMapping(path="/getAllSaturdays", produces = "application/json")
	public List<WorkingSaturdays> getSaturdayForAll() {
		List<WorkingSaturdays> workList = workSatServiceImpl.getWorkingSaturdays();
		Collections.sort(workList);
		return workList;
	}

	@GetMapping(path="/getAllSaturdays/{month}", produces = "application/json")
	public List<WorkingSaturdays> getSaturdayForMonth(@PathVariable String month) {
		List<WorkingSaturdays> workList = workSatServiceImpl.getWorkingSaturdaysForMonth(month);
		Collections.sort(workList);
		return workList;
	}
	
	@GetMapping(path="/swapSaturdays/first/{first}/second/{second}", produces = "application/json")
	public List<WorkingSaturdays> swapSaturdays(@PathVariable String first, @PathVariable String second) {
		List<WorkingSaturdays> workList = workSatServiceImpl.swapSaturdays(Arrays.asList(new String[] {first,second}));
		Collections.sort(workList);
		return workList;
	}
	
	@GetMapping(path="/admin/setAllSaturdays", produces = "application/json")
	public String setSaturdays() {
		String result = workSatServiceImpl.setSaturdayDatesForYear();
		return result;
	}
}
