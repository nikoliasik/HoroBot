package com.winter.horobot.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.handle.obj.IGuild;

public class Localisation {

	public static final Logger LOGGER = LoggerFactory.getLogger(Localisation.class);

	public static String of(String v, IGuild g) {
		LOGGER.debug(String.format("Translation of `%s` requested...", v));
		return "[" + v + "]"; // TODO wintery-sama pls
	}

}
