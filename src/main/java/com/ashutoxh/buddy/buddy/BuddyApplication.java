package com.ashutoxh.buddy.buddy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import com.ashutoxh.buddy.buddy.controller.RequestController;

@SpringBootApplication
@EnableCaching
public class BuddyApplication {

	@Autowired
	RequestController restController;
	
	public static void main(String[] args) {
		SpringApplication.run(BuddyApplication.class, args);
	}
}
