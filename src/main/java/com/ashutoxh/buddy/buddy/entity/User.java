package com.ashutoxh.buddy.buddy.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.lang.NonNull;

@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue
	Integer id;
	@NonNull
	String name;
	Integer pendingCompOffs;

	public User() {
		// TODO Auto-generated constructor stub
	}

	public User(String name) {
		this.name = name;
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

	public Integer getPendingCompOffs() {
		return pendingCompOffs;
	}

	@Override
	public String toString() {
		return "name=" + name;
	}
}
