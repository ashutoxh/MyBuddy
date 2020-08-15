package com.ashutoxh.buddy.buddy.controller;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ashutoxh.buddy.buddy.entity.NonWorkingSaturday;
import com.ashutoxh.buddy.buddy.entity.User;
import com.ashutoxh.buddy.buddy.entity.WorkingSaturday;
import com.ashutoxh.buddy.buddy.genericResponse.GenericResponse;
import com.ashutoxh.buddy.buddy.scheduler.Scheduler;
import com.ashutoxh.buddy.buddy.service.NonWorkingSaturdayServiceImpl;
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
	NonWorkingSaturdayServiceImpl nonSatServiceImpl;
	@Autowired
	Scheduler scheduler;

	@PutMapping(path = "/registerUser/{name}")
	@CacheEvict(value = "getAllUsers", allEntries = true)
	@ApiOperation(value = "Add new users. Saturdays will be recalculated after registration. New user will be assigned Saturday after 1 cycle")
	public GenericResponse registerUser(@PathVariable String name) {
		String response = userServiceImpl.addUser(name);
		return new GenericResponse("Success", name + " : " + response);
	}

	@GetMapping(path = "/getAllUsers")
	@Cacheable(value = "getAllUsers")
	@ApiOperation(value = "Get list of names and pending comoffs of all users")
	public List<User> getUsers() {
		return userServiceImpl.getUsers();
	}

	@DeleteMapping(path = "/removeUser/{name}")
	@CacheEvict(value = "getAllUsers", allEntries = true)
	@ApiOperation(value = "Remove users. Saturdays will be recalculated after removal.")
	public GenericResponse removeUser(@PathVariable String name) {
		String response = userServiceImpl.removeUser(name);
		return new GenericResponse("Success", name + " : " + response);
	}

	@GetMapping(path = "/getAllSaturdays")
	@ApiOperation(value = "Get list working saturdays for entire year")
	public List<WorkingSaturday> getSaturdayForAll() {
		List<WorkingSaturday> workList = workSatServiceImpl.getWorkingSaturdays();
		Collections.sort(workList);
		return workList;
	}

	@GetMapping(path = "/getAllSaturdays/{month}")
	@ApiOperation(value = "Get list of working saturdays for entire month. eg: july")
	public List<WorkingSaturday> getSaturdayForMonth(@PathVariable String month) {
		List<WorkingSaturday> workList = workSatServiceImpl.getWorkingSaturdaysForMonth(month);
		Collections.sort(workList);
		return workList;
	}

	@GetMapping(path = "/swapSaturdays/first/{first}/second/{second}")
	@ApiOperation(value = "Swap working saturdays in this month with names in order of working saturdays")
	public List<WorkingSaturday> swapSaturdays(@PathVariable String first, @PathVariable String second) {
		List<WorkingSaturday> workList = workSatServiceImpl
				.swapSaturdays(Arrays.asList(new String[] { first, second }));
		Collections.sort(workList);
		return workList;
	}

	@GetMapping(path = "/getAllNonWorkingSaturdays")
	@ApiOperation(value = "Get list working saturdays for entire year")
	public List<NonWorkingSaturday> getAllNonWorkingSaturdays() {
		List<NonWorkingSaturday> nonWorkList = nonSatServiceImpl.getNonWorkingSaturdays();
		Collections.sort(nonWorkList);
		return nonWorkList;
	}

	@PutMapping(path = "/setNonWorkingSaturday/{nonWorkingDate}")
	@ApiOperation(value = "Set a non working Saturday [dd-MM-yyyy]. Saturdays will be recalculated")
	public GenericResponse setHoliday(@PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate nonWorkingDate) {
		if (!nonWorkingDate.isBefore(LocalDate.now(ZoneId.of("Asia/Kolkata")))
				&& nonWorkingDate.getDayOfWeek() == DayOfWeek.SATURDAY) {
			String response = workSatServiceImpl.setNonWorkingSaturday(nonWorkingDate);
			return new GenericResponse("Success", nonWorkingDate + " : " + response);
		}
		return new GenericResponse("Success", nonWorkingDate + " : is in past or is not a Saturday");
	}

	// ADMIN CONTROLLED PART BELOW:

	@PutMapping(path = "/admin/addUserWithoutReassign/{name}")
	@ApiOperation(value = "Add new users. Saturdays will not be recalculated after registration")
	public GenericResponse addUserWithoutReassign(@PathVariable String name) {
		String response = userServiceImpl.addUserWithoutReassign(name);
		return new GenericResponse("Success", name + " : " + response);
	}

	@PutMapping(path = "/admin/setAllSaturdays")
	@ApiOperation(value = "Admin: Resets entire working saturday list and recalculates from current day with existing registered users")
	public List<WorkingSaturday> setSaturdays() {
		List<WorkingSaturday> workList = workSatServiceImpl.assignWorkingSaturday();
		Collections.sort(workList);
		return workList;
	}

	@DeleteMapping(path = "/admin/deleteAllNonWorkingSaturdays")
	@ApiOperation(value = "Admin: Resets entire non working saturday list")
	public GenericResponse deleteAllNonWorkingSaturdays() {
		nonSatServiceImpl.removeAllNonWorkingSaturdays();
		return new GenericResponse("Success", "All non working Saturdays deleted");
	}

	@GetMapping(path = "/admin/testRunCronJob")
	@ApiOperation(value = "Admin: Runs job manually to increase comp off of previous working saturday")
	public void runJobForCompOff() {
		scheduler.incrementCompOff();
	}

	@GetMapping(path = "/admin/checkServerDateTime")
	@ApiOperation(value = "Admin: Checks current date of server (IST used)")
	public LocalDateTime checkCurrentDateTime() {
		return LocalDateTime.now(ZoneId.of("Asia/Kolkata"));
	}
}
