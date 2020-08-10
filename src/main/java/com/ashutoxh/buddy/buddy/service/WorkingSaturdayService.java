package com.ashutoxh.buddy.buddy.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Component;

import com.ashutoxh.buddy.buddy.entity.WorkingSaturdays;
import com.ashutoxh.buddy.buddy.repository.WorkingSaturdayRepository;
	
@Component
public interface WorkingSaturdayService extends WorkingSaturdayRepository {

	public List<WorkingSaturdays> getWorkingSaturdays();

	public List<WorkingSaturdays> getWorkingSaturdaysForMonth(String month);

	public List<WorkingSaturdays> swapSaturdays(List<String> nameList);

	public String reassignWorkingSaturday();

	public String setSaturdayDatesForYear();

	public List<WorkingSaturdays> getAllSaturdaysList();

	public List<WorkingSaturdays> findByWorkingDateGreaterThan(LocalDate registeredDate);

	public List<WorkingSaturdays> findByWorkingDateBetween(LocalDate firstDate, LocalDate secondDate);

	public List<WorkingSaturdays> findByWorkingDateBetweenAndNameIn(LocalDate firstDate, LocalDate secondDate,
			List<String> nameList);

}
