package com.winter.horobot.data;

import com.winter.horobot.exceptions.UpdateFailedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.handle.obj.IGuild;

import java.util.ResourceBundle;

public class Localisation {

	/* TODO
	 * IMPORTANT: THIS CLASS IS TEMPORARY AND SHOULD NOT BE MODIFIED!!!!
	 * LET WINTER DO THIS PLEASE!!!!!!!!
	 */

	/**
	 * All of the languages available, some with UTF8Control for out of range characters
	 */
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

	/**
	 * Check a guilds language
	 * @param guild The guild to check the language of
	 * @return Returns the 2 letter code for the language
	 */
	private static String checkLanguage(IGuild guild) {
		return HoroCache.get(guild).getLanguage();
	}

	/**
	 * Change a guilds language to something else
	 * @param guild The guild to change
	 * @param language The language to change to
	 * @return true on success, false on failure
	 */
	public static boolean changeLanguage(IGuild guild, String language) throws UpdateFailedException {
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

	/**
	 * Update a guilds language in the database
	 * @param guild The guild to update
	 * @param language The language to update to
	 */
	private static void updateGuildLanguage(IGuild guild, String language) throws UpdateFailedException {
		HoroCache.get(guild).setLanguage(language);
	}

	/**
	 * Get a localized message
	 * @param guild The guild to get the message for
	 * @param messageKey The key of the message to get
	 * @return Returns a localized message
	 */
	public static String getMessage(IGuild guild, String messageKey) {
		String lang = checkLanguage(guild);
		switch (lang) {
			case "en":
				if (enLang.containsKey(messageKey))
					return enLang.getString(messageKey);
				break;
			case "nl":
				if (nlLang.containsKey(messageKey))
					return nlLang.getString(messageKey);
				break;
			case "es":
				if (esLang.containsKey(messageKey))
					return esLang.getString(messageKey);
				break;
			case "pt":
				if (ptLang.containsKey(messageKey))
					return ptLang.getString(messageKey);
				break;
			case "hi":
				if (hiLang.containsKey(messageKey))
					return hiLang.getString(messageKey);
				break;
			case "fr":
				if (frLang.containsKey(messageKey))
					return frLang.getString(messageKey);
				break;
			case "de":
				if (deLang.containsKey(messageKey))
					return deLang.getString(messageKey);
				break;
			case "ru":
				if (ruLang.containsKey(messageKey))
					return ruLang.getString(messageKey);
				break;
			case "ro":
				if (roLang.containsKey(messageKey))
					return roLang.getString(messageKey);
				break;
		}
		return "Localisation error please report this error in the Discord server so it can be fixed as quickly as possible; https://discord.gg/MCUTSZz";
	}

	/**
	 * Shortcut for getMessage
	 *
	 * @param key
	 * @param g
	 * @return
	 */
	public static String of(String key, IGuild g) {
		return getMessage(g, key);
	}

	/**
	 * If it's not a guild you wish to get the message for, get the default English localization for the message
	 * @param messageKey The key of the message you wish to get
	 * @return Returns the localized message
	 */
	public static String getDefaultMessage(String messageKey) {
		if(enLang.containsKey(messageKey))
			return enLang.getString(messageKey);
		return "Localisation error please report this error in the Discord server so it can be fixed as quickly as possible; https://discord.gg/MCUTSZz";
	}
}