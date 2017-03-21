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

package com.fuyusan.horobot.command.commands.admin;

import com.fuyusan.horobot.util.Message;
import com.fuyusan.horobot.util.Utility;
import com.fuyusan.horobot.command.proccessing.Command;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.Permissions;

public class CommandChannel implements Command {

	public boolean called(String[] args, MessageReceivedEvent event) {
		if(event.getAuthor().getPermissionsForGuild(event.getGuild()).contains(Permissions.ADMINISTRATOR)) {
			return true;
		} else {
			return false;
		}
	}

	public void action(String[] args, String raw, MessageReceivedEvent event) {
		if(args.length == 2) {
			if (args[1].equals("ecchi") || args[1].equals("nsfw")) {
				IChannel channel = event.getGuild().getChannelByID(args[0]);
				if (channel != null) {
					Utility.storeChannelMod(channel.getID(), args[1]);
					Message.reply("mod-added", event.getMessage());
				} else {
					if (event.getGuild().getChannelsByName(args[0]).size() == 1) {
						channel = event.getGuild().getChannelsByName(args[0]).get(0);
						Utility.storeChannelMod(channel.getID(), args[1]);
						Message.reply("mod-added", event.getMessage());
					} else {
						Message.reply("no-channels", event.getMessage());
					}
				}
			} else {
				Message.reply("no-mods", event.getMessage());
			}
		} else {
			Message.reply(help(), event.getMessage());
		}
	}

	public String help() {
		return "channel-help";
	}

	public void executed(boolean success, MessageReceivedEvent event) {
		if(success)
			Utility.commandsExecuted++;
	}
}
