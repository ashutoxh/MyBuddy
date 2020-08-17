package com.ashutoxh.buddy.buddy.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Component;

import com.ashutoxh.buddy.buddy.entity.User;
import com.ashutoxh.buddy.buddy.entity.WorkingSaturday;
import com.ashutoxh.buddy.buddy.repository.WorkingSaturdayRepository;

@Component
public interface WorkingSaturdayService extends WorkingSaturdayRepository {

	public List<WorkingSaturday> findByWorkingDateGreaterThanEqual(LocalDate registeredDate);

	public WorkingSaturday findTop1ByWorkingDateLessThanOrderByWorkingDateDesc(LocalDate sundayDate);

	public List<WorkingSaturday> findByWorkingDateBetween(LocalDate firstDate, LocalDate secondDate);

	public List<WorkingSaturday> findByWorkingDateBetweenAndNameIn(LocalDate firstDate, LocalDate secondDate,
			List<String> nameList);

	public List<WorkingSaturday> getWorkingSaturdays();

	public List<WorkingSaturday> getWorkingSaturdaysForMonth(String month);

	public WorkingSaturday getLastWorkingUser();

	public List<WorkingSaturday> swapSaturdays(List<String> nameList);

	public List<WorkingSaturday> assignWorkingSaturday();

	public List<WorkingSaturday> reassignWorkingSaturday(User user);

	public List<WorkingSaturday> getWSListForAssignReassign(List<User> userList);

	public void setNonWorkingSaturday(LocalDate nonWorkingDate);

	public void incrementCompOff();

}
