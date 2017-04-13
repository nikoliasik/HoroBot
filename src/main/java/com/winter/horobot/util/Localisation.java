/*
	HoroBot - An open-source Discord bot
	Copyright (C) 2017	WiNteR

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as
    published by the Free Software Foundation, either version 3 of the
    License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.winter.horobot.util;

import com.winter.horobot.database.DataBase;

import java.util.ResourceBundle;

public class Localisation {

	public static ResourceBundle enLang = ResourceBundle.getBundle("locale.en");
	public static ResourceBundle nlLang = ResourceBundle.getBundle("locale.nl");
	public static ResourceBundle esLang = ResourceBundle.getBundle("locale.es");
	public static ResourceBundle ptLang = ResourceBundle.getBundle("locale.pt");

	public static String checkLanguage(String guildID) {
		return DataBase.guildQuery(guildID, "language");
	}

	public static boolean changeLanguage (String guildID, String language) {
		switch (language) {
			case "en":
				updateGuildLanguage(guildID, "en");
				return true;
			case "nl":
				updateGuildLanguage(guildID, "nl");
				return true;
			case "es":
				updateGuildLanguage(guildID, "es");
				return true;
			case "pt":
				updateGuildLanguage(guildID, "pt");
				return true;
		}
		return false;
	}

	public static void updateGuildLanguage (String guildID, String language) {
		DataBase.insertGuild(guildID);
		DataBase.updateGuild(guildID, "language", language);
	}
	
	public static String getMessage(String guildID, String str) {
		String lang = checkLanguage(guildID);
		if(lang.equals("en")) {
			if (enLang.containsKey(str)) {
				return enLang.getString(str);
			}
		} else if(lang.equals("nl")) {
			if (nlLang.containsKey(str)) {
				return nlLang.getString(str);
			}
		} else if(lang.equals("es")) {
			if (esLang.containsKey(str)) {
				return esLang.getString(str);
			}
		} else if(lang.equals("pt")) {
			if (ptLang.containsKey(str)) {
				return ptLang.getString(str);
			}
		}
		return "Localisation error please report this error in the Discord server so it can be fixed as quickly as possible; https://discord.gg/MCUTSZz";
	}
	
	public static String getPMMessage(String str) {
		if(enLang.containsKey(str))
			return enLang.getString(str);
		return "Localisation error please report this error in the Discord server so it can be fixed as quickly as possible; https://discord.gg/MCUTSZz";
	}
}