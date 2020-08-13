package com.ashutoxh.buddy.buddy.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Component;

import com.ashutoxh.buddy.buddy.repository.NonWorkingSaturdayRepository;

@Component
public interface NonWorkingSaturdayService extends NonWorkingSaturdayRepository {

	public List<LocalDate> getNonWorkingSaturdays();

	public void setNonWorkingSaturday(LocalDate nonWorkingDate);

	public void removeNonWorkingSaturday(LocalDate nonWorkingDate);

	public void removeAllNonWorkingSaturdays();

}
