package com.winter.horobot.data;

import sx.blah.discord.handle.obj.IGuild;

import java.util.ResourceBundle;

public class Localisation {

	private static ResourceBundle enLang = ResourceBundle.getBundle("locale.en");
	private static ResourceBundle nlLang = ResourceBundle.getBundle("locale.nl");
	private static ResourceBundle esLang = ResourceBundle.getBundle("locale.es");
	private static ResourceBundle ptLang = ResourceBundle.getBundle("locale.pt");
	private static ResourceBundle hiLang = ResourceBundle.getBundle("locale.hi");
	private static ResourceBundle frLang = ResourceBundle.getBundle("locale.fr");
	private static ResourceBundle deLang = ResourceBundle.getBundle("locale.de", new UTF8Control());
	private static ResourceBundle ruLang = ResourceBundle.getBundle("locale.ru", new UTF8Control());
	private static ResourceBundle roLang = ResourceBundle.getBundle("locale.ro");
  
  public static final Logger LOGGER = LoggerFactory.getLogger(Localisation.class);

	public static String of(String v, IGuild g) {
		LOGGER.debug(String.format("Translation of `%s` requested...", v));
		return "[" + v + "]"; // TODO wintery-sama pls
	}

	private static String checkLanguage(IGuild guild) {
		return (String) Database.get("SELECT language FROM guilds.guild WHERE id=?;", guild.getStringID()).get("language");
	}

	public static boolean changeLanguage (IGuild guild, String language) {
		switch (language) {
			case "en":
				updateGuildLanguage(guild, "en");
				return true;
			case "nl":
				updateGuildLanguage(guild, "nl");
				return true;
			case "es":
				updateGuildLanguage(guild, "es");
				return true;
			case "pt":
				updateGuildLanguage(guild, "pt");
				return true;
			case "hi":
				updateGuildLanguage(guild, "hi");
				return true;
			case "fr":
				updateGuildLanguage(guild, "fr");
				return true;
			case "de":
				updateGuildLanguage(guild, "de");
				return true;
			case "ru":
				updateGuildLanguage(guild, "ru");
				return true;
			case "ro":
				updateGuildLanguage(guild, "ro");
				return true;
		}
		return false;
	}

	private static void updateGuildLanguage (IGuild guild, String language) {
		Database.set("UPDATE guilds.guild SET language=? WHERE id=?", language, guild.getStringID());
	}

	public static String getMessage(IGuild guild, String str) {
		String lang = checkLanguage(guild);
		switch (lang) {
			case "en":
				if (enLang.containsKey(str))
					return enLang.getString(str);
				break;
			case "nl":
				if (nlLang.containsKey(str))
					return nlLang.getString(str);
				break;
			case "es":
				if (esLang.containsKey(str))
					return esLang.getString(str);
				break;
			case "pt":
				if (ptLang.containsKey(str))
					return ptLang.getString(str);
				break;
			case "hi":
				if (hiLang.containsKey(str))
					return hiLang.getString(str);
				break;
			case "fr":
				if (frLang.containsKey(str))
					return frLang.getString(str);
				break;
			case "de":
				if (deLang.containsKey(str))
					return deLang.getString(str);
				break;
			case "ru":
				if (ruLang.containsKey(str))
					return ruLang.getString(str);
				break;
			case "ro":
				if (roLang.containsKey(str))
					return roLang.getString(str);
				break;
		}
		return "Localisation error please report this error in the Discord server so it can be fixed as quickly as possible; https://discord.gg/MCUTSZz";
	}

	public static String getDefaultMessage(String str) {
		if(enLang.containsKey(str))
			return enLang.getString(str);
		return "Localisation error please report this error in the Discord server so it can be fixed as quickly as possible; https://discord.gg/MCUTSZz";
	}
}