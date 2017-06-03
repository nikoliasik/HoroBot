package com.winter.horobot.util;

import com.winter.horobot.Main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.handle.obj.IUser;

public class ParsingUtil {

	public static Logger LOGGER = LoggerFactory.getLogger(ParsingUtil.class);

	public static IUser getUser(String s) {
		LOGGER.debug(String.format("Passed in with `%s`...", s));
		if (s.matches("<@!?\\d+>")) {
			long id = Long.parseLong(s.replaceAll("<@!?(\\d+)>", "$1"));
			return Main.getClient().getUserByID(id);
		} else if (s.matches("\\d+")) {
			long id = Long.parseLong(s);
			return Main.getClient().getUserByID(id);
		} else if (s.matches(".+#\\d{4}")) {
			return Main.getClient().getUsersByName(s.replaceAll("(.+)#\\d{4}", "$1")).parallelStream().filter(u -> u.getDiscriminator().equals(s.replaceAll(".+#(\\d{4})", "$1"))).findFirst().orElseGet(null);
		}
		throw new IllegalArgumentException("Not a valid user string!");
	}

}
