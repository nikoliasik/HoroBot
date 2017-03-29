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

import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.impl.obj.Embed;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.RequestBuffer;

public class Message {

	public static void sendEmbed(IChannel channel, String message, EmbedObject embed, boolean inline) {
		RequestBuffer.request(() -> {
			channel.sendMessage(message, embed, inline);
		});
	}

	public static void reply (String str, IMessage message) {
		RequestBuffer.request(() -> {
			message.reply(Localisation.getMessage(message.getGuild().getID(), str));
		});
	}
	
	public static void replyRaw (String str, IMessage message) {
		RequestBuffer.request(() -> {
			message.reply(str);
		});
	}

	public static void sendMessageInChannel(IChannel channel, String message) {
		RequestBuffer.request(() -> {
			channel.sendMessage(Localisation.getMessage(channel.getGuild().getID(), message));
		});
	}
	
	public static void sendRawMessageInChannel(IChannel channel, String message) {
		RequestBuffer.request(() -> {
			channel.sendMessage(message);
		});
	}

	public static void sendPM(String message, IUser user) {
		RequestBuffer.request(() -> {
			user.getOrCreatePMChannel().sendMessage(Localisation.getPMMessage(message));
		});
	}
	
	public static void sendRawPM (String message, IUser user) {
		RequestBuffer.request(() -> {
			user.getOrCreatePMChannel().sendMessage(message);
		});
	}
	
	public static void sendPMPic (String message, MessageReceivedEvent event) {
		String tags[] = { "holo" };
		RequestBuffer.request(() -> {
			event.getMessage().getAuthor().getOrCreatePMChannel().sendMessage(HTMLHandler.requestKona(tags, HTMLHandler.KONA_RATING.SAFE));
		});
	}
}