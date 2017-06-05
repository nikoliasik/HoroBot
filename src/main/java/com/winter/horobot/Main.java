package com.winter.horobot;

import com.winter.horobot.command.Commands;
import com.winter.horobot.data.Database;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;

import java.io.*;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;
import java.util.Properties;

public class Main {

	private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
	public static Map<ConfigValue, String> config = new EnumMap<>(ConfigValue.class);
	private static IDiscordClient client = null;

	/**
	 *
	 * @param args Args taken in from the run parameters
	 * @throws IOException Upon failure to read the config
	 */
	public static void main(String[] args) throws IOException {
		InputStream configProperties = null;
		if(args.length > 0) {
			try {
				configProperties = new FileInputStream(new File(args[0]));
			} catch (FileNotFoundException e) {
				LOGGER.error("Couldn't find the passed in config file!");
				System.exit(1);
			}
		} else {
			configProperties = Main.class.getResourceAsStream("/config.properties");
		}
		Properties p = new Properties();
		p.load(configProperties);
		p.keySet().forEach(k -> config.put(ConfigValue.from(k.toString()), p.getProperty(k.toString())));

		ClientBuilder b = new ClientBuilder();
		if(config.get(ConfigValue.DEBUG).equals("false")) {
			b.withToken(config.get(ConfigValue.TOKEN));
		} else {
			b.withToken(config.get(ConfigValue.DEBUG_TOKEN));
		}

		b.registerListener(new Commands());

		Database.connect();
		if (!Database.setup()) {
			LOGGER.error("Failed to setup database for bot use!");
			System.exit(1);
		}

		client = b.login();
	}

	public static IDiscordClient getClient() {
		return client;
	}

	public enum ConfigValue {
		DEBUG("debug"),
		TOKEN("token"),
		DEBUG_TOKEN("debug-token"),
		DB_PASS("db-password"),
		BOTS_PW_TOKEN("bots-pw-token"),
		BOTS_ORG_TOKEN("bots-org-token"),
		MAL_CREDENTIALS("mal-credentials"),
		MASHAPE_KEY("mashape-key"),
		YOUTUBE_KEY("youtube-key"),
		;

		public static ConfigValue from(String key) {
			return Arrays.stream(ConfigValue.values()).filter(v -> v.getName().equals(key)).findFirst().orElseThrow(() -> new IllegalArgumentException("Not a config value!"));
		}

		private final String name;

		ConfigValue(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
	}

}
