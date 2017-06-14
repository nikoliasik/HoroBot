package com.winter.horobot;

import com.winter.horobot.data.cache.GuildMeta;
import com.winter.horobot.data.cache.HoroCache;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.GuildCreateEvent;

public class HoroEventListener {

	@EventSubscriber
	public void onGuildCreated(GuildCreateEvent e) {
		if (e.getGuild() != null) {
			GuildMeta guild = HoroCache.get(e.getGuild());
		}
		// TODO: Do stuff I guess, you need to make this into a custom listener thing too
	}
}