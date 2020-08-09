package com.ashutoxh.buddy.buddy.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ashutoxh.buddy.buddy.entity.WorkingSaturdays;

@Repository
public interface WorkingSaturdayRepository extends JpaRepository<WorkingSaturdays, Integer> {

	public List<WorkingSaturdays> findByWorkingDateGreaterThan(LocalDate registeredDate);
	public List<WorkingSaturdays> findByWorkingDateBetween(LocalDate firstDate, LocalDate secondDate);
	public List<WorkingSaturdays> findByWorkingDateBetweenAndNameIn(LocalDate firstDate, LocalDate secondDate,List<String> nameList);
	
}
