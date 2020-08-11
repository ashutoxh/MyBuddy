package com.ashutoxh.buddy.buddy.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.ashutoxh.buddy.buddy.entity.User;
import com.ashutoxh.buddy.buddy.entity.WorkingSaturdays;
import com.ashutoxh.buddy.buddy.service.UserService;
import com.ashutoxh.buddy.buddy.service.WorkingSaturdayServiceImpl;

@Configuration
@EnableScheduling
public class Scheduler {

	@Autowired
	WorkingSaturdayServiceImpl workSatServiceImpl;
	@Autowired
	UserService userService;

	@Scheduled(cron = "0 0 0 ? * SUN", zone = "IST") 	//There are only 6 fields: second, minute, hour, day of month, month, day(s) of week
	public void incrementCompOff() {
		WorkingSaturdays workingSaturdays = workSatServiceImpl.getLastWorkingUser();
		if(workingSaturdays != null) {
			User user = userService.findByName(workingSaturdays.getName());
			user.setPendingCompOffs(user.getPendingCompOffs()+1);
			userService.save(user);
		}
	}
}
