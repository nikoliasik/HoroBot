package com.fuyusan.horobot.util;

import com.fuyusan.horobot.database.DataBase;
import sx.blah.discord.handle.obj.IUser;

import java.util.HashMap;

public class Cooldowns {

	private static final HashMap<String, HashMap<String, Long>> COOLDOWNS = new HashMap<>();

	/**
	 * Makes a cooldown for user and bucket
	 *
	 * @param bucket The bucket (cooldown name)
	 * @param length Length of the cooldown
	 * @param user The user to cool down
	 *
	 * @throws OnCooldownException If the cooldown isn't over
	 */
	public void cooldown(String bucket, long length, IUser user) throws OnCooldownException {
		DataBase.createCooldownSchema();
		DataBase.createCooldownTable();
		HashMap<String, Long> bket = COOLDOWNS.computeIfAbsent(bucket, d -> new HashMap<>());
		if(System.currentTimeMillis() - bket.putIfAbsent(user.getID(), 0L) < length)
			throw new OnCooldownException();
	}

	/**
	 * Puts user onto a cooldown in bucket
	 *
	 * @param bucket The bucket
	 * @param user The user
	 */
	public void putOnCooldown(String bucket, IUser user){
		HashMap<String, Long> bket = COOLDOWNS.computeIfAbsent(bucket, d -> new HashMap<>());
		bket.put(user.getID(), System.currentTimeMillis());
		DataBase.insertCooldown(bucket, user, bket.get(user.getID()));
	}

	private class OnCooldownException extends Exception {
	}
}
