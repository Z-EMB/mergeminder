package com.mcs.mergeminder.properties;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@EnableConfigurationProperties
@ConfigurationProperties(prefix = "mm.slack")
@Validated
public class SlackProperties {

	@NotNull String botToken;
	String notificationChannel = null;
	Boolean notifyUsers = false;

	public String getBotToken() {
		return botToken;
	}

	public void setBotToken(String botToken) {
		this.botToken = botToken;
	}

	public String getNotificationChannel() {
		return notificationChannel;
	}

	public void setNotificationChannel(String notificationChannel) {
		this.notificationChannel = notificationChannel;
	}

	public Boolean getNotifyUsers() {
		return notifyUsers;
	}

	public void setNotifyUsers(Boolean notifyUsers) {
		this.notifyUsers = notifyUsers;
	}

}
