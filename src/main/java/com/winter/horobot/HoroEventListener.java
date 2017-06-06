package com.winter.horobot;

import com.winter.horobot.data.GuildMeta;
import com.winter.horobot.data.HoroCache;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.GuildCreateEvent;
import sx.blah.discord.handle.obj.IGuild;

public class HoroEventListener {

	@EventSubscriber
	public void onGuildCreated(GuildCreateEvent e) {
		GuildMeta guild = HoroCache.get(e.getGuild());
		// TODO: Do stuff I guess, you need to make this into a custom listener thing too
	}
}