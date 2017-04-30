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

package com.winter.horobot.command.commands.admin;

import com.winter.horobot.command.proccessing.CommandType;
import com.winter.horobot.util.Localisation;
import com.winter.horobot.util.Message;
import com.winter.horobot.util.Utility;
import com.winter.horobot.command.proccessing.Command;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.Permissions;
import sx.blah.discord.util.EmbedBuilder;
import sx.blah.discord.util.MissingPermissionsException;

import java.awt.*;
import java.time.LocalDateTime;

public class CommandKick implements Command {

	public boolean called(String[] args, MessageReceivedEvent event) {
		if(event.getAuthor().getPermissionsForGuild(event.getGuild()).contains(Permissions.ADMINISTRATOR)) {
			return true;
		} else {
			return false;
		}
	}

	public void action(String[] args, String raw, MessageReceivedEvent event) {
		if(args.length >= 1) {
			IUser user = null;
			if (event.getMessage().getMentions().size() == 1) {
				user = event.getMessage().getMentions().get(0);
			}
			if (user == null) {
				try {
					user = event.getGuild().getUserByID(Long.parseUnsignedLong(args[0]));
				} catch (NumberFormatException e) { }
			}
			if (user == null) {
				if (event.getGuild().getUsersByName(args[0]).size() == 1) {
					user = event.getGuild().getUsersByName(args[0]).get(0);
				}
			}
			if (user != null) {
				String reason;
				if (args.length == 1) {
					try {
						event.getGuild().kickUser(user);
					} catch (MissingPermissionsException e) {
						if(e.getMissingPermissions().size() == 0) {
							Message.sendMessageInChannel(event.getChannel(), "failed-kick", user.getName(), "Role hierarchy is too high");
						} else {
							Message.sendMessageInChannel(event.getChannel(), "failed-kick", user.getName(), Utility.permissionsAsString(e.getMissingPermissions()));
						}
						return;
					}
					reason = "No reason specified";
				} else {
					try {
						event.getGuild().kickUser(user);
					} catch (MissingPermissionsException e) {
						if(e.getMissingPermissions().size() == 0) {
							Message.sendMessageInChannel(event.getChannel(), "failed-kick", user.getName(), "Role hierarchy is too high");
						} else {
							Message.sendMessageInChannel(event.getChannel(), "failed-kick", user.getName(), Utility.permissionsAsString(e.getMissingPermissions()));
						}
						return;
					}
					reason = raw.substring(args[0].length() + 1, raw.length());
				}
				Message.sendPM(user, "kicked-from", event.getGuild().getName(), reason);

				EmbedBuilder builder = new EmbedBuilder();
				builder.withAuthorIcon(Utility.getAvatar(user));
				builder.withAuthorName("User " + user.getName() + " was kicked");
				builder.withColor(Color.RED);
				builder.withTimestamp(LocalDateTime.now());
				builder.withThumbnail(Utility.getAvatar(user));

				builder.appendField("User kicked", user.getName(), true);
				builder.appendField("ID", user.getStringID(), true);
				builder.appendField("Reason", reason, false);
				builder.appendField("Kicked by", event.getAuthor().getName(), true);
				builder.appendField("ID", event.getAuthor().getStringID(), true);

				Message.sendEmbed(event.getChannel(), "", builder.build(), false);
			} else {
				Message.reply("user-not-found", event.getMessage());
			}
		} else {
			Message.sendMessageInChannel(event.getChannel(), help());
		}
	}

	public String help() {
		return "kick-help";
	}

	@Override
	public CommandType getType() {
		return CommandType.ADMIN;
	}

	public void executed(boolean success, MessageReceivedEvent event) {
		if(success)
			Utility.commandsExecuted++;
	}
}
