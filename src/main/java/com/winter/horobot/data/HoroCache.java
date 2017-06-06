package com.winter.horobot.data;

import sx.blah.discord.handle.obj.IGuild;

import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;

public class HoroCache {

	private static Map<IGuild, GuildMeta> guildCache = Collections.synchronizedMap(new WeakHashMap<>());

	public static GuildMeta get(IGuild guild) {
		return guildCache.getOrDefault(guild, new GuildMeta(guild));
	}
}