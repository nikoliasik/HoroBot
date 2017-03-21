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

package com.fuyusan.horobot.util;

import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;

public class Message {
	
	public static void reply (String str, IMessage message) {
		try {
			message.reply(Localisation.getMessage(message.getGuild().getID(), str));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void replyRaw (String str, IMessage message) {
		try {
			message.reply(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void replyInChannel (String str, IMessage message) {
		try {
			message.getChannel().sendMessage(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void sendPM(String message, IUser user) {
		try {
			user.getOrCreatePMChannel().sendMessage(Localisation.getPMMessage(message));
		} catch(Exception e) {

		}
	}
	
	public static void sendRawPM (String message, IUser user) {
		try {
			user.getOrCreatePMChannel().sendMessage(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void sendPMPic (String message, MessageReceivedEvent event) {
		try {
			String tags[] = { "holo" };
			event.getMessage().getAuthor().getOrCreatePMChannel().sendMessage(HTMLHandler.requestKona(tags, HTMLHandler.KONA_RATING.SAFE));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}