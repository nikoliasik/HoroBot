package com.winter.horobot.data;

import sx.blah.discord.handle.obj.IGuild;

import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;

public class HoroCache {

	public static Map<IGuild, GuildMeta> getGuildCache() {
		return guildCache;
	}

	private static Map<IGuild, GuildMeta> guildCache = Collections.synchronizedMap(new WeakHashMap<>());

	public static GuildMeta get(IGuild guild) {
		return guildCache.getOrDefault(guild, new GuildMeta(guild));
	}
}