package com.winter.horobot.util;

public enum FilterReason {

	MASS_USER_MENTION("Mass User Mention"),
	MASS_CHANNEL_MENTION("Mass Channel Mention"),
	MASS_ROLE_MENTION("Mass Role Mention"),
	SPAM("Spam"),
	LINK("Link");

	private String humanReadable;

	FilterReason(String humanReadable) {
		this.humanReadable = humanReadable;
	}

	public String getHumanReadable() {
		return humanReadable;
	}
}
