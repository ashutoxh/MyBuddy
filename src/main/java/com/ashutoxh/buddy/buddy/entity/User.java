package com.ashutoxh.buddy.buddy.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue
	@JsonIgnore
	Integer id;

	@NotBlank
	String name;
	int pendingCompOffs;

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

	public int getPendingCompOffs() {
		return pendingCompOffs;
	}
	
	public void setPendingCompOffs(int pendingCompOffs) {
		this.pendingCompOffs=pendingCompOffs;
	}

	@Override
	public String toString() {
		return "name=" + name;
	}
}
