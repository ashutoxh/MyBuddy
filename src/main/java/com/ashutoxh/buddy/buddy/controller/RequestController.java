package com.ashutoxh.buddy.buddy.controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.ashutoxh.buddy.buddy.entity.User;
import com.ashutoxh.buddy.buddy.entity.WorkingSaturdays;
import com.ashutoxh.buddy.buddy.scheduler.Scheduler;
import com.ashutoxh.buddy.buddy.service.UserServiceImpl;
import com.ashutoxh.buddy.buddy.service.WorkingSaturdayServiceImpl;

import io.swagger.annotations.ApiOperation;

@RestController
public class RequestController {

	@Autowired
	UserServiceImpl userServiceImpl;
	@Autowired
	WorkingSaturdayServiceImpl workSatServiceImpl;
	@Autowired
	Scheduler scheduler;

	@GetMapping(path = "/registerUser/{name}")
	@ApiOperation(value = "Add new users. Saturdays will be recalculated after registration. New user will be assigned Saturday after 1 cycle")
	public User registerUser(@PathVariable String name) {
		User user = userServiceImpl.addUser(name);
		return user;
	}

	@GetMapping(path = "/getAllUsers")
	@ApiOperation(value = "Get list of names and pending comoffs of all users")
	public List<User> getUsers() {
		return userServiceImpl.getUsers();
	}

	@DeleteMapping(path = "/removeUser/{name}")
	@ApiOperation(value = "Remove users. Saturdays will be recalculated after removal.")
	public void removeUser(@PathVariable String name) {
		userServiceImpl.removeUser(name);
	}

	@GetMapping(path = "/getAllSaturdays")
	@ApiOperation(value = "Get list working saturdays for entire year")
	public List<WorkingSaturdays> getSaturdayForAll() {
		List<WorkingSaturdays> workList = workSatServiceImpl.getWorkingSaturdays();
		Collections.sort(workList);
		return workList;
	}

	@GetMapping(path = "/getAllSaturdays/{month}")
	@ApiOperation(value = "Get list of working saturdays for entire month. eg: july")
	public List<WorkingSaturdays> getSaturdayForMonth(@PathVariable String month) {
		List<WorkingSaturdays> workList = workSatServiceImpl.getWorkingSaturdaysForMonth(month);
		Collections.sort(workList);
		return workList;
	}

	@GetMapping(path = "/swapSaturdays/first/{first}/second/{second}")
	@ApiOperation(value = "Swap working saturdays in this month with names in order of working saturdays")
	public List<WorkingSaturdays> swapSaturdays(@PathVariable String first, @PathVariable String second) {
		List<WorkingSaturdays> workList = workSatServiceImpl
				.swapSaturdays(Arrays.asList(new String[] { first, second }));
		Collections.sort(workList);
		return workList;
	}

	// ADMIN CODES BELOW:

	@GetMapping(path = "/admin/setAllSaturdays")
	@ApiOperation(value = "Admin: Resets entire working saturday list and recalculates from current day with existing registered users")
	public List<WorkingSaturdays> setSaturdays() {
		List<WorkingSaturdays> workList = workSatServiceImpl.setSaturdayDatesForYear();
		return workList;
	}

	@GetMapping(path = "/admin/testRunCronJob")
	@ApiOperation(value = "Admin: Runs job manually to increase comp offs")
	public void runJobForCompOff() {
		scheduler.incrementCompOff();
	}
}
