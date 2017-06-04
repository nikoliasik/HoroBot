package com.winter.horobot.util;

import com.winter.horobot.data.Localisation;
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
		IMessage m = e.getChannel().sendMessage(Localisation.of("receive-latency", e.getGuild()) + ": **" + (beforeSend - messageSent) + "ms**");
		long afterSend = System.currentTimeMillis();
		m.edit(Localisation.of("receive-latency", e.getGuild()) + ": **" + (beforeSend - messageSent) + "ms**\n" + Localisation.of("send-latency", e.getGuild()) + ": **" + (afterSend - beforeSend) + "ms**");
		long afterEdit = System.currentTimeMillis();
		EmbedBuilder eb = new EmbedBuilder();
		eb.withColor(Color.getHSBColor(((float) (afterEdit - messageSent)) / 360.0f, 1.0f, 1.0f));
		LOGGER.debug(String.format("Hue of Ping embed: %f", ((float) (afterEdit - messageSent)) / 360.0f));
		eb.appendDescription(">" + Localisation.of("message-timestamp", e.getGuild()) + ": " + messageSent + "\n>");
		eb.appendDescription(Localisation.of("before-send", e.getGuild()) + ": " + beforeSend + "\n>");
		eb.appendDescription(Localisation.of("after-send", e.getGuild()) + ": " + afterSend + "\n>");
		eb.appendDescription(Localisation.of("after-edit", e.getGuild()) + ": " + afterEdit + "\n\n**");
		eb.appendDescription(Localisation.of("gateway-response-time", e.getGuild()) + ": " + gatewayPing + "ms**");
		m.edit(Localisation.of("receive-latency", e.getGuild()) + ": **" + (beforeSend - messageSent) + "ms**\n" + Localisation.of("send-latency", e.getGuild()) + ": **" + (afterSend - beforeSend) + "ms**\n" + Localisation.of("edit-latency", e.getGuild()) + ": **" + (afterEdit - afterSend) + "ms**", eb.build());
		return true;
	}

}
