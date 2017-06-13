package com.winter.horobot.data;

import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IUser;

import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;

public class HoroCache {

	public static Map<IGuild, GuildMeta> getGuildCache() {
		return guildCache;
	}
	public static Map<IUser, UserMeta> getUserCache() { return userCache; }

	private static Map<IGuild, GuildMeta> guildCache = Collections.synchronizedMap(new WeakHashMap<>());
	private static Map<IUser, UserMeta> userCache = Collections.synchronizedMap(new WeakHashMap<>());

	public static GuildMeta get(IGuild guild) {
		if (!guildCache.containsKey(guild)) {
			Database.set("INSERT INTO guilds.guild (id, language, prefix, welcome, role) VALUES (?, ?, ?, ?) ON CONFLICT DO NOTHING;", guild.getStringID(), "en", ".horo", "none", "none");
			guildCache.put(guild, new GuildMeta(guild));
		}
		return guildCache.get(guild);
	}

	public static UserMeta get(IUser user) {
		if (!userCache.containsKey(user)) {
			Database.set("INSERT INTO users.user (id) VALUES (?) ON CONFLICT DO NOTHING;", user.getStringID());
			userCache.put(user, new UserMeta(user));
		}
		return userCache.get(user);
	}
}