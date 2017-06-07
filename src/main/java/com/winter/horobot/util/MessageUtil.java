package com.winter.horobot.util;

import com.winter.horobot.data.Localisation;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.RequestBuffer;

import java.util.Arrays;
import java.util.stream.Collectors;

public class MessageUtil {

	public static String[] argsArray(IMessage m) {
		return m.getContent().substring(GuildUtil.getPrefix(m.getGuild()).length()).split("\\s+");
	}

	public static String args(IMessage m) {
		return Arrays.stream(argsArray(m)).collect(Collectors.joining(" "));
	}

	/**
	 * Send a message in a channel, params are what the %s in the message will be replaced with
	 * @param channel The channel to send the message in
	 * @param messageKey The localisation key of the message
	 * @param params The replacements for %s in the message
	 */
	public static void sendMessage(IChannel channel, String messageKey, Object... params) {
		RequestBuffer.request(() -> channel.sendMessage(Localisation.getMessage(channel.getGuild(), messageKey, params)));
	}
}
