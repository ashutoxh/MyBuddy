package com.ashutoxh.buddy.buddy.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.ashutoxh.buddy.buddy.service.WorkingSaturdayServiceImpl;

@Configuration
@EnableScheduling
public class Scheduler {

	@Autowired
	WorkingSaturdayServiceImpl workSatServiceImpl;

	@Scheduled(cron = "0 0 0 ? * SUN", zone = "IST") // 6 fields: sec, min, hr, day of month, month, day(s) of week
	public void incrementCompOff() {
		workSatServiceImpl.incrementCompOff();
	}
}
