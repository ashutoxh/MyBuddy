package com.ashutoxh.buddy.buddy.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "users")
public class User implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6578737314457851434L;

	@Id
	@GeneratedValue
	@JsonIgnore
	Integer id;

	@NotBlank
	String name;
	int pendingCompOffs;

	public User() {
	}

	public User(String name) {
		this.name = name;
	}
	
	public User(String name, int pendingCompOffs) {
		this.name = name;
		this.pendingCompOffs = pendingCompOffs;
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
