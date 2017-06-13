package com.winter.horobot.data.cache;

import com.winter.horobot.Main;
import com.winter.horobot.data.Database;
import com.winter.horobot.exceptions.UpdateFailedException;
import sx.blah.discord.api.internal.DiscordClientImpl;
import sx.blah.discord.handle.impl.obj.Guild;
import sx.blah.discord.handle.impl.obj.VoiceChannel;
import sx.blah.discord.handle.obj.IGuild;

import java.util.Map;
import java.util.Optional;

public class GuildMeta extends Guild {

	private volatile String prefix;
	private volatile String language;
	private volatile String welcome;
	private volatile String pm;
	private volatile long autorole;
	private volatile boolean levelupNotifications;
	private volatile boolean presentBan;
	private volatile boolean botIgnore;

	/**
	 * Constructor for guild metadata object
	 * @param guild The guild object to construct metadata for
	 */
	public GuildMeta(IGuild guild) {
		super(guild.getShard(), guild.getName(), guild.getLongID(), guild.getIcon(), guild.getOwnerLongID(), Optional.ofNullable(guild.getAFKChannel()).orElse(
				new VoiceChannel((DiscordClientImpl) Main.getClient(), "", 0L, guild, "", -1, -1, -1, null, null)
		).getLongID(), guild.getAFKTimeout(), guild.getRegion().getID(), guild.getVerificationLevel().ordinal());
		Map<String, Object> settings = Database.get("SELECT * FROM guilds.guild WHERE id=?", guild.getStringID());
		this.prefix = (String) settings.getOrDefault("prefix", ".horo");
		this.language = (String) settings.getOrDefault("language", "en");
		this.welcome = (String) settings.getOrDefault("welcome", "none");
		this.pm = (String) settings.getOrDefault("pm", "none");
		// TODO vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
		String temp = (String) settings.getOrDefault("role", "none");
		if (temp.equalsIgnoreCase("none")) this.autorole = 0L;
		else this.autorole = Long.parseUnsignedLong(temp);
		// TODO ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ This is a no-go obviously but it is a temporary solution
		this.levelupNotifications = (boolean) settings.getOrDefault("lvlup", true);
		this.presentBan = (boolean) settings.getOrDefault("bpresentban", true);
		this.botIgnore = (boolean) settings.getOrDefault("bignore", false);
	}

	public String getPrefix() {
		return prefix;
	}

	public synchronized void setPrefix(String prefix) throws UpdateFailedException {
		if (!Database.set("UPDATE guilds.guild SET prefix=? WHERE id=?", prefix, this.getStringID()))
			throw new UpdateFailedException("Failed to update guild metadata");
		this.prefix = prefix;
	}

	public String getLanguage() {
		return language;
	}

	public synchronized void setLanguage(String language) throws UpdateFailedException {
		if (!Database.set("UPDATE guilds.guild SET language=? WHERE id=?", language, this.getStringID()))
			throw new UpdateFailedException("Failed to update guild metadata");
		this.language = language;
	}

	public String getWelcome() {
		return welcome;
	}

	public synchronized void setWelcome(String welcome) throws UpdateFailedException {
		if (!Database.set("UPDATE guilds.guild SET welcome=? WHERE id=?", welcome, this.getStringID()))
			throw new UpdateFailedException("Failed to update guild metadata");
		this.welcome = welcome;
	}

	public String getPm() {
		return pm;
	}

	public synchronized void setPm(String pm) throws UpdateFailedException {
		if (!Database.set("UPDATE guilds.guild SET pm=? WHERE id=?", pm, this.getStringID()))
			throw new UpdateFailedException("Failed to update guild metadata");
		this.pm = pm;
	}

	public long getAutorole() {
		return autorole;
	}

	public synchronized void setAutorole(long autorole) throws UpdateFailedException {
		if (!Database.set("UPDATE guilds.guild SET role=? WHERE id=?", String.valueOf(autorole), this.getStringID()))
			throw new UpdateFailedException("Failed to update guild metadata");
		this.autorole = autorole;
	}

	public boolean isLevelupNotifications() {
		return levelupNotifications;
	}

	public synchronized void setLevelupNotifications(boolean levelupNotifications) throws UpdateFailedException {
		if (!Database.set("UPDATE guilds.guild SET lvlup=? WHERE id=?", levelupNotifications, this.getStringID()))
			throw new UpdateFailedException("Failed to update guild metadata");
		this.levelupNotifications = levelupNotifications;
	}

	public boolean isPresentBan() {
		return presentBan;
	}

	public synchronized void setPresentBan(boolean presentBan) throws UpdateFailedException {
		if (!Database.set("UPDATE guilds.guild SET bpresentban=? WHERE id=?", presentBan, this.getStringID()))
			throw new UpdateFailedException("Failed to update guild metadata");
		this.presentBan = presentBan;
	}

	public boolean isBotIgnore() {
		return botIgnore;
	}

	public synchronized void setBotIgnore(boolean botIgnore) throws UpdateFailedException {
		if (!Database.set("UPDATE guilds.guild SET bignore=? WHERE id=?", botIgnore, this.getStringID()))
			throw new UpdateFailedException("Failed to update guild metadata");
		this.botIgnore = botIgnore;
	}
}