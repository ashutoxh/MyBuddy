package com.ashutoxh.buddy.buddy.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Year;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ashutoxh.buddy.buddy.entity.User;
import com.ashutoxh.buddy.buddy.entity.WorkingSaturdays;
import com.ashutoxh.buddy.buddy.repository.UserRepository;
import com.ashutoxh.buddy.buddy.repository.WorkingSaturdayRepository;

@Component
public class WorkingSaturdayService {

	@Autowired
	WorkingSaturdayRepository workingSaturdayRepository;
	@Autowired
	UserRepository userRepository;

	public List<WorkingSaturdays> getWorkingSaturdays() {
		return workingSaturdayRepository.findAll();
	}
	
	public String reassignWorkingSaturday() {

		return null;
	}

	public String setSaturdayDatesForYear() {
		List<WorkingSaturdays> woList = getAllSaturdaysList();
		workingSaturdayRepository.deleteAll();
		woList = workingSaturdayRepository.saveAll(woList);
		return woList != null ? "Success" : "Failed";
	}

	private List<WorkingSaturdays> getAllSaturdaysList() {
		List<WorkingSaturdays> woList = new ArrayList<WorkingSaturdays>();
		Year year = Year.now();
		int noOfWeekends = 100;
		LocalDate workingDate = LocalDate.of(year.getValue(), 1, 1)
				.with(TemporalAdjusters.firstInMonth(DayOfWeek.SATURDAY)); 	// Gives 1st Saturday of current year
		
		List<User> userList = userRepository.findAll();
		
		for (int i = 0; i < Math.ceil(noOfWeekends/userList.size()); i++) {
			for(User user : userList) {
				woList.add(new WorkingSaturdays(user.getName(), workingDate));
				workingDate = workingDate.plusDays(7);
			}
			if(workingDate.isAfter(LocalDate.of(year.getValue(),12,31)))
				break;
		}
		return woList;
	}
}
