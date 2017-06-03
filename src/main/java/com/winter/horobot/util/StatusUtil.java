package com.winter.horobot.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.EmbedBuilder;

import java.awt.*;

public class StatusUtil {

	public static Logger LOGGER = LoggerFactory.getLogger(StatusUtil.class);

	public static boolean ping(MessageReceivedEvent e) {
		long beforeSend = System.currentTimeMillis();
		IMessage m = e.getChannel().sendMessage("Pinging...");
		long afterSend = System.currentTimeMillis();
		m.edit("Send latency: **" + (afterSend - beforeSend) + "ms**");
		long afterEdit = System.currentTimeMillis();
		EmbedBuilder eb = new EmbedBuilder();
		eb.withColor(Color.getHSBColor(((float) (afterEdit - beforeSend)) / 360.0f, 1.0f, 1.0f));
		LOGGER.debug(String.format("Hue of Ping embed: %f", ((float) (afterEdit - beforeSend)) / 360.0f));
		eb.appendDescription(">Before send: " + beforeSend + "\n>");
		eb.appendDescription("After send: " + afterSend + "\n>");
		eb.appendDescription("After edit: " + afterEdit);
		m.edit("Send latency: **" + (afterSend - beforeSend) + "ms**\nEdit latency: **" + (afterEdit - afterSend) + "ms**", eb.build());
		return true;
	}

}
