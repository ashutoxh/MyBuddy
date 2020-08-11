package com.ashutoxh.buddy.buddy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class BuddyApplication {

	public static void main(String[] args) {
		SpringApplication.run(applicationClass, args);
	}

	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(applicationClass);
	}

	private static Class<BuddyApplication> applicationClass = BuddyApplication.class;

}
