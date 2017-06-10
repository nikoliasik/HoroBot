package com.winter.horobot.data;

import sx.blah.discord.handle.impl.obj.User;
import sx.blah.discord.handle.obj.IUser;

import java.util.Map;

public class UserMeta extends User {

	// TODO

	public UserMeta(IUser user) {
		super(user.getShard(), user.getName(), user.getLongID(), user.getDiscriminator(), user.getAvatar(), user.getPresence(), user.isBot());
		Map<String, Object> settings = Database.get("SELECT * FROM users.user WHERE id=?", user.getStringID());
	}


}