package com.ashutoxh.buddy.buddy.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.ashutoxh.buddy.buddy.entity.User;
import com.ashutoxh.buddy.buddy.repository.UserRepository;

@Component
public interface UserService extends UserRepository {

	public List<User> getUsers();

	public String addUser(String name);

	public String addUserWithoutReassign(String name);

	public User findByName(String name);

	public String removeUser(String name);

	public User getExistingUserByName(String name);

}
