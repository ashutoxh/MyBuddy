package com.ashutoxh.buddy.buddy.controller;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ashutoxh.buddy.buddy.entity.WorkingSaturday;
import com.ashutoxh.buddy.buddy.genericResponse.GenericResponse;
import com.ashutoxh.buddy.buddy.scheduler.Scheduler;
import com.ashutoxh.buddy.buddy.service.NonWorkingSaturdayServiceImpl;
import com.ashutoxh.buddy.buddy.service.UserServiceImpl;
import com.ashutoxh.buddy.buddy.service.WorkingSaturdayServiceImpl;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	UserServiceImpl userServiceImpl;
	@Autowired
	WorkingSaturdayServiceImpl workSatServiceImpl;
	@Autowired
	NonWorkingSaturdayServiceImpl nonSatServiceImpl;
	@Autowired
	Scheduler scheduler;

	@PutMapping(path = "/addUserWithoutReassign/{name}")
	@CacheEvict(value = "getAllUsers", allEntries = true)
	@ApiOperation(value = "Add new users. Saturdays will not be recalculated after registration")
	public GenericResponse addUserWithoutReassign(@PathVariable String name) {
		String response = userServiceImpl.addUserWithoutReassign(name);
		return new GenericResponse("Success", name + " : " + response);
	}

	@PutMapping(path = "/setAllSaturdays")
	@CacheEvict(value = "getAllSaturdays", allEntries = true)
	@ApiOperation(value = "Admin: Resets entire working saturday list and recalculates from current day with existing registered users")
	public List<WorkingSaturday> setSaturdays() {
		List<WorkingSaturday> workList = workSatServiceImpl.assignWorkingSaturday();
		Collections.sort(workList);
		return workList;
	}

	@DeleteMapping(path = "/deleteAllNonWorkingSaturdays")
	@CacheEvict(value = "getAllNonWorkingSaturdays", allEntries = true)
	@ApiOperation(value = "Admin: Resets entire non working saturday list")
	public GenericResponse deleteAllNonWorkingSaturdays() {
		nonSatServiceImpl.removeAllNonWorkingSaturdays();
		return new GenericResponse("Success", "All non working Saturdays deleted");
	}

	@GetMapping(path = "/testRunCronJob")
	@ApiOperation(value = "Admin: Runs job manually to increase comp off of previous working saturday")
	public void runJobForCompOff() {
		scheduler.incrementCompOff();
	}

	@GetMapping(path = "/checkServerDateTime")
	@ApiOperation(value = "Admin: Checks current date of server (IST used)")
	public LocalDateTime checkCurrentDateTime() {
		return LocalDateTime.now(ZoneId.of("Asia/Kolkata"));
	}
}
