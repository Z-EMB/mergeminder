package com.dst.mergeminder.util;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TimeSchedule {

	private static final Logger logger = LoggerFactory.getLogger(TimeSchedule.class);

	private DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mma");

	@Value("${mergeminder.beginAlertHour:9}")
	Integer beginAlertHour;
	@Value("${mergeminder.endAlertHour:18}")
	Integer endAlertHour;
	@Value("${mergeminder.alertOnWeekends:false}")
	boolean alertOnWeekends;

	public TimeSchedule() {
		// empty constructor
	}

	@PostConstruct
	public void init() {
		ZoneId zoneId = ZoneId.of("America/New_York");
		ZonedDateTime currentEasternTime = ZonedDateTime.ofInstant(Instant.now(), zoneId);
		logger.info("TimeSchedule initialization complete.  Begin alerting at {}:00, stop alerting at {}:00.  DO {} alert on weekends. Current Eastern time is: {}",
			beginAlertHour, endAlertHour, alertOnWeekends ? "" : "NOT", dateFormat.format(currentEasternTime));
	}

	public boolean shouldAlertNow() {

		ZoneId zoneId = ZoneId.of("America/New_York");
		ZonedDateTime currentEasternTime = ZonedDateTime.ofInstant(Instant.now(), zoneId);
		logger.debug("Current Eastern time is: {}", dateFormat.format(currentEasternTime));
		if (alertOnWeekends && (currentEasternTime.getDayOfWeek() == DayOfWeek.SATURDAY || currentEasternTime.getDayOfWeek() == DayOfWeek.SUNDAY)) {
			logger.debug("It's the weekend.  No notifications.");
			return false;
		}
		if (currentEasternTime.getHour() < beginAlertHour || currentEasternTime.getHour() > endAlertHour) {
			logger.debug("It's too early or too late.  No notifications.");
			return false;
		}

		return true;
	}

}
