package com.ashutoxh.buddy.buddy.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "working_saturdays")
public class WorkingSaturday implements Comparable<WorkingSaturday> {

	@Id
	@GeneratedValue
	@JsonIgnore
	Integer id;

	@NotBlank
	String name;
	@JsonFormat(pattern = "dd-MM-yyyy")
	LocalDate workingDate;

	public WorkingSaturday() {
	}

	public WorkingSaturday(String name, LocalDate workingDate) {
		this.name = name;
		this.workingDate = workingDate;
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

	@Override
	public String toString() {
		return "WorkingSaturdays [name=" + name + ", workingDate=" + workingDate + "]";
	}

	@Override
	public int compareTo(WorkingSaturday o) {
		return getWorkingDate().compareTo(o.getWorkingDate());
	}

}
