package com.winter.horobot.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.EmbedBuilder;

import java.awt.*;
import java.time.ZoneId;

public class StatusUtil {

	public static final Logger LOGGER = LoggerFactory.getLogger(StatusUtil.class);

	public static boolean ping(MessageReceivedEvent e) {
		long gatewayPing = e.getClient().getOurUser().getShard().getResponseTime();
		long messageSent = e.getMessage().getTimestamp().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() - 1000;
		LOGGER.debug("Message sent at `" + messageSent + "`...");
		long beforeSend = System.currentTimeMillis();
		IMessage m = e.getChannel().sendMessage("Receive latency: **" + (beforeSend - messageSent) + "ms**");
		long afterSend = System.currentTimeMillis();
		m.edit("Receive latency: **" + (beforeSend - messageSent) + "ms**\nSend latency: **" + (afterSend - beforeSend) + "ms**");
		long afterEdit = System.currentTimeMillis();
		EmbedBuilder eb = new EmbedBuilder();
		eb.withColor(Color.getHSBColor(((float) (afterEdit - messageSent)) / 360.0f, 1.0f, 1.0f));
		LOGGER.debug(String.format("Hue of Ping embed: %f", ((float) (afterEdit - messageSent)) / 360.0f));
		eb.appendDescription(">Message timestamp: " + messageSent + "\n>");
		eb.appendDescription("Before send: " + beforeSend + "\n>");
		eb.appendDescription("After send: " + afterSend + "\n>");
		eb.appendDescription("After edit: " + afterEdit + "\n\n**");
		eb.appendDescription("Gateway response time: " + gatewayPing + "ms**");
		m.edit("Receive latency: **" + (beforeSend - messageSent) + "ms**\nSend latency: **" + (afterSend - beforeSend) + "ms**\nEdit latency: **" + (afterEdit - afterSend) + "ms**", eb.build());
		return true;
	}

}
