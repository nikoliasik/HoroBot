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

package com.winter.horobot.command.commands.image;

import com.winter.horobot.util.HTMLHandler;
import com.winter.horobot.util.Message;
import com.winter.horobot.util.Utility;
import com.winter.horobot.command.proccessing.Command;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.Permissions;
import sx.blah.discord.util.EmbedBuilder;

import java.awt.*;

public class CommandCat implements Command {

	public boolean called(String[] args, MessageReceivedEvent event) {
		return true;
	}

	public void action(String[] args, String raw, MessageReceivedEvent event) {
		if(args.length == 0) {
			if (Utility.checkUserPermission(event.getGuild(), event.getClient().getOurUser(), Permissions.EMBED_LINKS)) {
				EmbedBuilder builder = new EmbedBuilder();
				builder.withImage(HTMLHandler.requestCat());
				builder.withColor(Color.CYAN);
				builder.withAuthorName("Requested by @" + event.getAuthor().getDisplayName(event.getGuild()) + "#" + event.getAuthor().getDiscriminator());
				builder.withAuthorIcon(event.getAuthor().getAvatarURL());

				Message.sendEmbed(event.getChannel(), "", builder.build(), false);
			} else {
				Message.sendMessageInChannel(event.getChannel(), "missing-embed-perm");
			}
		} else {
			Message.reply(help(), event.getMessage());
		}
	}

	public String help() {
		return "cat-help";
	}

	public void executed(boolean success, MessageReceivedEvent event) {
		if(success)
			Utility.commandsExecuted++;
	}
}