package com.ashutoxh.buddy.buddy.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.lang.NonNull;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "working_saturdays")
public class WorkingSaturdays {

	@Id
	@GeneratedValue
	Integer id;
	@NonNull
	String name;
	@JsonFormat(pattern = "dd-MM-yyyy")
	LocalDate workingDate;

	public WorkingSaturdays() {
		// TODO Auto-generated constructor stub
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getWorkingDate() {
		return workingDate;
	}

	public void setWorkingDate(LocalDate workingDate) {
		this.workingDate = workingDate;
	}

}
