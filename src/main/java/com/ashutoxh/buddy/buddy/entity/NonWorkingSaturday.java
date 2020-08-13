package com.ashutoxh.buddy.buddy.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "non_working_saturdays")
public class NonWorkingSaturday implements Comparable<NonWorkingSaturday> {

	@Id
	@GeneratedValue
	Integer id;

	LocalDate nonWorkingDate;

	public NonWorkingSaturday() {
	}

	public NonWorkingSaturday(LocalDate nonWorkingDate) {
		this.nonWorkingDate = nonWorkingDate;
	}

	public LocalDate getWorkingDate() {
		return nonWorkingDate;
	}

	public void setWorkingDate(LocalDate nonWorkingDate) {
		this.nonWorkingDate = nonWorkingDate;
	}

	@Override
	public String toString() {
		return "NonWorkingSaturday [nonWorkingDate=" + nonWorkingDate + "]";
	}

	@Override
	public int compareTo(NonWorkingSaturday o) {
		return getWorkingDate().compareTo(o.getWorkingDate());
	}

}
