package com.ashutoxh.buddy.buddy.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ashutoxh.buddy.buddy.entity.NonWorkingSaturday;
import com.ashutoxh.buddy.buddy.entity.User;
import com.ashutoxh.buddy.buddy.entity.WorkingSaturday;

@Service
public class WorkingSaturdayServiceImpl {

	public final String NON_WORKING_SATURDAY = "NON WORKING";

	@Autowired
	WorkingSaturdayService workingSaturdayService;
	@Autowired
	UserServiceImpl userServiceImpl;
	@Autowired
	NonWorkingSaturdayServiceImpl nonSatServiceImpl;

	LocalDate workingDate = null;

	Logger logger = LoggerFactory.getLogger(WorkingSaturdayServiceImpl.class);

	public List<WorkingSaturday> getWorkingSaturdays() {
		return workingSaturdayService.findAll();
	}

	public List<WorkingSaturday> getWorkingSaturdaysForMonth(String month) {
		List<WorkingSaturday> workSatList = new ArrayList<WorkingSaturday>();
		int monthNumber = 0;
		for (int i = 1; i <= 12; i++)
			if (month.equalsIgnoreCase(Month.of(i).toString()))
				monthNumber = i;
		LocalDate monthStartDate = LocalDate.of(Year.now(ZoneId.of("Asia/Kolkata")).getValue(), monthNumber, 1);
		if (monthNumber != 0) {
			workSatList = workingSaturdayService.findByWorkingDateBetween(monthStartDate, monthStartDate.plusMonths(1));
		}
		return workSatList;
	}

	public List<WorkingSaturday> swapSaturdays(List<String> nameList) {
		List<WorkingSaturday> workSatList = new ArrayList<WorkingSaturday>();
		workSatList = workingSaturdayService.findByWorkingDateBetweenAndNameIn(LocalDate.now(ZoneId.of("Asia/Kolkata")),
				LocalDate.now(ZoneId.of("Asia/Kolkata")).plusMonths(1), nameList);
		if (workSatList != null) {
			LocalDate tempDT1 = workSatList.get(0).getWorkingDate();
			LocalDate tempDT2 = workSatList.get(1).getWorkingDate();
			workSatList.get(0).setWorkingDate(tempDT2);
			workSatList.get(1).setWorkingDate(tempDT1);
			workingSaturdayService.saveAll(workSatList);
		}
		return workSatList;
	}

	public List<WorkingSaturday> assignWorkingSaturday() {
		List<User> userList = userServiceImpl.getUsers(); // Get all users
		workingSaturdayService.deleteAll();
		List<WorkingSaturday> workSatList = getWSListForAssignReassign(userList);

		workSatList = workingSaturdayService.saveAll(workSatList);
		return workSatList;
	}

	public List<WorkingSaturday> reassignWorkingSaturday(User user) {
		List<User> userList = userServiceImpl.getUsers(); // Get all users
		int size = userList.size();
		userList.clear();
		LocalDate registeredDate = LocalDate.now(ZoneId.of("Asia/Kolkata")); // Get current date

		List<WorkingSaturday> workSatList = new ArrayList<WorkingSaturday>();
		try {
			workSatList = workingSaturdayService.findByWorkingDateGreaterThanEqual(registeredDate);
			if (workSatList != null)
				workingSaturdayService.deleteInBatch(workSatList);

			if (user != null) {
				size -= 1; // Since we want no of users before the new user was added
			}

			for (int i = 0; i < size; i++) { //
				userList.add(new User(workSatList.get(i).getName()));
			}
			if (user != null) {
				userList.add(user); // To add new user
			}
			workSatList.clear();

			workSatList = getWSListForAssignReassign(userList);

			workSatList = workingSaturdayService.saveAll(workSatList);
		} catch (Exception e) {
			logger.error("WorkingSaturdayServiceImpl : reassignWorkingSaturday() : Exception : {} ", e.getMessage());
		}
		return workSatList;
	}

	public List<WorkingSaturday> getWSListForAssignReassign(List<User> userList) {
		List<WorkingSaturday> workSatList = new ArrayList<WorkingSaturday>();
		LocalDate registeredDate = LocalDate.now(ZoneId.of("Asia/Kolkata")); // Get current date
		workingDate = registeredDate.with(TemporalAdjusters.next(DayOfWeek.SATURDAY)); // next Saturday

		List<NonWorkingSaturday> nonWorkDateList = nonSatServiceImpl.getNonWorkingSaturdays(); // list of holidays

		Year year = Year.now(ZoneId.of("Asia/Kolkata"));
		while (!workingDate.isAfter(LocalDate.of(year.getValue(), 12, 31))) {
			for (int i = 0; i < userList.size();) {
				if (nonWorkDateList != null && nonWorkDateList.stream()
						.filter(nws -> nws.getWorkingDate().equals(workingDate)).findFirst().isPresent()) {
					workSatList.add(new WorkingSaturday(NON_WORKING_SATURDAY, workingDate));
				} else {
					workSatList.add(new WorkingSaturday(userList.get(i).getName(), workingDate));
					i++;
				}
				workingDate = workingDate.plusWeeks(1);
			}
		}

		return workSatList;
	}

	public String setNonWorkingSaturday(LocalDate nonWorkingDate) {
		String response = null;
		List<WorkingSaturday> workList = workingSaturdayService.findByWorkingDateGreaterThanEqual(nonWorkingDate);
		List<NonWorkingSaturday> nonWorkSat = nonSatServiceImpl.getNonWorkingSaturdays();

		if (nonWorkSat != null && nonWorkSat.stream().filter(nws -> nws.getWorkingDate().equals(nonWorkingDate))
				.findFirst().isPresent()) {
			response = "Already set as holiday";
		} else {
			if (workList != null) {
				for (WorkingSaturday ws : workList) {
					if (ws.getName().equals(NON_WORKING_SATURDAY))
						continue;
					ws.setWorkingDate(ws.getWorkingDate().plusWeeks(1));
				}
				workList.add(new WorkingSaturday(NON_WORKING_SATURDAY, nonWorkingDate));
				Collections.sort(workList);
			}
			System.out.println("workList : " + workList.toString());
			workList = workingSaturdayService.saveAll(workList);
			nonSatServiceImpl.setNonWorkingSaturday(nonWorkingDate);
			response = "is set to holiday";
		}
		return response;
	}

	public WorkingSaturday getLastWorkingUser() {
		return workingSaturdayService
				.findTop1ByWorkingDateLessThanOrderByWorkingDateDesc(LocalDate.now(ZoneId.of("Asia/Kolkata")));
	}

	public void incrementCompOff() {
		WorkingSaturday workingSaturdays = getLastWorkingUser();
		if (workingSaturdays != null && !workingSaturdays.getName().equals(NON_WORKING_SATURDAY)) {
			User user = userServiceImpl.getExistingUserByName(workingSaturdays.getName());
			user.setPendingCompOffs(user.getPendingCompOffs() + 1);
			userServiceImpl.addUser(user);
		}
	}
}
