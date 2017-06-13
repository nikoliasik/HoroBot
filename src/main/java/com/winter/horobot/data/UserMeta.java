package com.winter.horobot.data;

import com.winter.horobot.exceptions.UpdateFailedException;
import sx.blah.discord.handle.impl.obj.User;
import sx.blah.discord.handle.obj.IUser;

import java.util.Map;

public class UserMeta extends User {

	private volatile String description;
	private volatile int level;
	private volatile int xp;
	private volatile int maxXP;
	private volatile int coins;
	private volatile String background; // TODO definitely not using strings for items, we'll have to figure something out together

	/**
	 * Constructor for user metadata object
	 * @param user The user to construct metadata for
	 */
	public UserMeta(IUser user) {
		super(user.getShard(), user.getName(), user.getLongID(), user.getDiscriminator(), user.getAvatar(), user.getPresence(), user.isBot());
		Map<String, Object> settings = Database.get("SELECT * FROM users.user WHERE id=?", user.getStringID());
		this.description = (String) settings.getOrDefault("description", "I like trains");
		this.level = (int) settings.getOrDefault("level", 1);
		this.xp = (int) settings.getOrDefault("xp", 0);
		this.maxXP = (int) settings.getOrDefault("maxxp", 300);
		this.coins = (int) settings.getOrDefault("foxcoins", 0);
		this.background = (String) settings.getOrDefault("background", "NONE0"); // TODO No.
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) throws UpdateFailedException {
		if (!Database.set("UPDATE users.user SET description=? WHERE id=?", description, this.getStringID()))
			throw new UpdateFailedException("Failed to update guild metadata");
		this.description = description;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) throws UpdateFailedException {
		if (!Database.set("UPDATE users.user SET level=? WHERE id=?", level, this.getStringID()))
			throw new UpdateFailedException("Failed to update guild metadata");
		this.level = level;
	}

	public int getXp() {
		return xp;
	}

	public void setXp(int xp) throws UpdateFailedException {
		if (!Database.set("UPDATE users.user SET xp=? WHERE id=?", xp, this.getStringID()))
			throw new UpdateFailedException("Failed to update guild metadata");
		this.xp = xp;
	}

	public int getMaxXP() {
		return maxXP;
	}

	public void setMaxXP(int maxXP) throws UpdateFailedException {
		if (!Database.set("UPDATE users.user SET maxxp=? WHERE id=?", maxXP, this.getStringID()))
			throw new UpdateFailedException("Failed to update guild metadata");
		this.maxXP = maxXP;
	}

	public int getCoins() {
		return coins;
	}

	public void setCoins(int coins) throws UpdateFailedException {
		if (!Database.set("UPDATE users.user SET foxcoins=? WHERE id=?", coins, this.getStringID()))
			throw new UpdateFailedException("Failed to update guild metadata");
		this.coins = coins;
	}

	public String getBackground() {
		return background;
	}

	public void setBackground(String background) throws UpdateFailedException {
		if (!Database.set("UPDATE users.user SET background=? WHERE id=?", background, this.getStringID()))
			throw new UpdateFailedException("Failed to update guild metadata");
		this.background = background;
	}
}