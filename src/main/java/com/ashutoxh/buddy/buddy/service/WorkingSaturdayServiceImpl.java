package com.ashutoxh.buddy.buddy.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ashutoxh.buddy.buddy.entity.User;
import com.ashutoxh.buddy.buddy.entity.WorkingSaturdays;
import com.ashutoxh.buddy.buddy.repository.UserRepository;

@Service
public class WorkingSaturdayServiceImpl {

	@Autowired
	WorkingSaturdayService workingSaturdayService;
	@Autowired
	UserRepository userRepository;

	public List<WorkingSaturdays> getWorkingSaturdays() {
		return workingSaturdayService.findAll();
	}

	public List<WorkingSaturdays> getWorkingSaturdaysForMonth(String month) {
		List<WorkingSaturdays> workSatList = new ArrayList<WorkingSaturdays>();
		int monthNumber = 0;
		for (int i = 1; i <= 12; i++)
			if (month.equalsIgnoreCase(Month.of(i).toString()))
				monthNumber = i;
		LocalDate monthStartDate = LocalDate.of(Year.now().getValue(), monthNumber, 1);
		if (monthNumber != 0) {
			workSatList = workingSaturdayService.findByWorkingDateBetween(monthStartDate,
					monthStartDate.plusMonths(1));
		}
		return workSatList;
	}

	public List<WorkingSaturdays> swapSaturdays(List<String> nameList) {
		List<WorkingSaturdays> workSatList = new ArrayList<WorkingSaturdays>();
		workSatList = workingSaturdayService.findByWorkingDateBetweenAndNameIn(LocalDate.now(),
				LocalDate.now().plusMonths(1), nameList);
		LocalDate tempDT1 = workSatList.get(0).getWorkingDate();
		LocalDate tempDT2 = workSatList.get(1).getWorkingDate();
		workSatList.get(0).setWorkingDate(tempDT2);
		workSatList.get(1).setWorkingDate(tempDT1);
		workingSaturdayService.saveAll(workSatList);
		return workSatList;
	}

	public String reassignWorkingSaturday(User user) {
		List<User> userList = userRepository.findAll();		//Get all users
		int size = userList.size();
		userList.clear();
		LocalDate registeredDate = LocalDate.now();			//Get current date
		
		List<WorkingSaturdays> workSatList = new ArrayList<WorkingSaturdays>();
		workSatList = workingSaturdayService.findByWorkingDateGreaterThan(registeredDate);
		workingSaturdayService.deleteAll(workSatList);
		
		LocalDate workingDate = registeredDate.with(TemporalAdjusters.next(DayOfWeek.SATURDAY));  //Get next Saturday from current date
		workingDate = workingDate.plusWeeks(userList.size());			//Skip weeks equal to number of current users
		
		for(int i=0; i<size-1; i++) {
			userList.add(new User(workSatList.get(i).getName()));
		}
		userList.add(user);
		workSatList.clear();
		
		Year year = Year.now();
		while (!workingDate.isAfter(LocalDate.of(year.getValue(), 12, 31))) {
			for (User u : userList) {
				workSatList.add(new WorkingSaturdays(u.getName(), workingDate));
				workingDate = workingDate.plusDays(7);
			}
		}
		workSatList = workingSaturdayService.saveAll(workSatList);
		return workSatList!=null? "Success":"Failed";
	}

	public String setSaturdayDatesForYear() {
		List<WorkingSaturdays> woList = getAllSaturdaysList();
		workingSaturdayService.deleteAll();
		woList = workingSaturdayService.saveAll(woList);
		return woList != null ? "Success" : "Failed";
	}

	public List<WorkingSaturdays> getAllSaturdaysList() {
		List<WorkingSaturdays> woList = new ArrayList<WorkingSaturdays>();
		Year year = Year.now();
		List<User> userList = userRepository.findAll();
		LocalDate workingDate = LocalDate.of(year.getValue(), 1, 1)
				.with(TemporalAdjusters.firstInMonth(DayOfWeek.SATURDAY)); // Gives 1st Saturday of current year
		while (!workingDate.isAfter(LocalDate.of(year.getValue(), 12, 31))) {
			for (User user : userList) {
				woList.add(new WorkingSaturdays(user.getName(), workingDate));
				workingDate = workingDate.plusDays(7);
			}
		}
		return woList;
	}
}
