package com.winter.horobot.util;

import sx.blah.discord.handle.obj.IMessage;

import java.util.Arrays;
import java.util.stream.Collectors;

public class MessageUtil {

	public static String[] argsArray(IMessage m) {
		return Arrays.copyOfRange(m.getContent().split("\\s+"), 1, m.getContent().split("\\s+").length);
	}

	public static String args(IMessage m) {
		return Arrays.stream(argsArray(m)).collect(Collectors.joining(" "));
	}

}
