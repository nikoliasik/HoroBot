package com.winter.horobot.util;

import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.EmbedBuilder;

import java.awt.*;

public class StatusUtil {

	public static boolean ping(MessageReceivedEvent e) {
		long beforeSend = System.currentTimeMillis();
		IMessage m = e.getChannel().sendMessage("Pinging...");
		long afterSend = System.currentTimeMillis();
		m.edit("Send latency: **" + (afterSend - beforeSend) + "ms**");
		long afterEdit = System.currentTimeMillis();
		EmbedBuilder eb = new EmbedBuilder();
		eb.withColor(Color.getHSBColor(((float) afterEdit - beforeSend) / 360.0f, 1.0f, 1.0f));
		eb.appendDescription("Before send: " + beforeSend + "\n");
		eb.appendDescription("After send: " + afterSend + "\n");
		eb.appendDescription("After edit: " + afterEdit);
		m.edit("Send latency: **" + (afterSend - beforeSend) + "ms**\nEdit latency: **" + (afterEdit - afterSend) + "**", eb.build());
		return true;
	}

}
