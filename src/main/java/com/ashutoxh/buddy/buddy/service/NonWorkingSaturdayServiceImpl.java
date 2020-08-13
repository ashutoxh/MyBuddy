package com.ashutoxh.buddy.buddy.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ashutoxh.buddy.buddy.entity.NonWorkingSaturday;

@Service
public class NonWorkingSaturdayServiceImpl {

	@Autowired
	NonWorkingSaturdayService nonWorkingSaturdayService;

	public List<NonWorkingSaturday> getNonWorkingSaturdays() {
		return nonWorkingSaturdayService.findAll();
	}

	public void setNonWorkingSaturday(LocalDate nonWorkingDate) {
		nonWorkingSaturdayService.save(new NonWorkingSaturday(nonWorkingDate));
	}

	public void removeNonWorkingSaturday(LocalDate nonWorkingDate) {
		nonWorkingSaturdayService.delete(new NonWorkingSaturday(nonWorkingDate));
	}

	public void removeAllNonWorkingSaturdays() {
		nonWorkingSaturdayService.deleteAll();
	}
}
