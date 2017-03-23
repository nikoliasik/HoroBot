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
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.Permissions;

public class CommandRemoveRole implements Command {

	public boolean called(String[] args, MessageReceivedEvent event) {
		if(event.getAuthor().getPermissionsForGuild(event.getGuild()).contains(Permissions.ADMINISTRATOR)) {
			return true;
		} else {
			return false;
		}
	}

	public void action(String[] args, String raw, MessageReceivedEvent event) {
		if(args.length == 2) {
			IUser user = null;
			if (event.getMessage().getMentions().size() == 1) {
				user = event.getMessage().getMentions().get(0);
			}
			if (user == null)
				user = event.getGuild().getUserByID(args[0]);
			if (user == null) {
				if (event.getGuild().getUsersByName(args[0]).size() == 1) {
					user = event.getGuild().getUsersByName(args[0]).get(0);
				}
			}

			IRole role = null;
			if (event.getMessage().getRoleMentions().size() == 1) {
				role = event.getMessage().getRoleMentions().get(0);
			}
			if (role == null)
				role = event.getGuild().getRoleByID(args[1]);
			if (role == null) {
				if (event.getGuild().getRolesByName(args[1]).size() == 1) {
					role = event.getGuild().getRolesByName(args[1]).get(0);
				}
			}

			if(user != null) {
				if(role != null) {
					if (user.getRolesForGuild(event.getMessage().getGuild()).contains(role)) {
						try {
							user.removeRole(role);
							Message.reply("user-role-removed", event.getMessage());
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else {
						Message.reply("has-no-role", event.getMessage());
					}
				} else {
					Message.reply("user-not-found", event.getMessage());
				}
			} else {
				Message.reply("no-role", event.getMessage());
			}
		} else {
			Message.reply(help(), event.getMessage());
		}
	}

	public String help() {
		return "remove-help";
	}

	public void executed(boolean success, MessageReceivedEvent event) {
		if(success)
			Utility.commandsExecuted++;
	}
}