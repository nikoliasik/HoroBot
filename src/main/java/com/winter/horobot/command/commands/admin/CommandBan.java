package com.winter.horobot.command.commands.admin;

import com.winter.horobot.util.Localisation;
import com.winter.horobot.util.Message;
import com.winter.horobot.util.Utility;
import com.winter.horobot.command.proccessing.Command;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.Permissions;

public class CommandBan implements Command {

	public boolean called(String[] args, MessageReceivedEvent event) {
		if(event.getAuthor().getPermissionsForGuild(event.getGuild()).contains(Permissions.ADMINISTRATOR)) {
			return true;
		} else {
			return false;
		}
	}

	public void action(String[] args, String raw, MessageReceivedEvent event) {
		if(args.length >= 1) {
			if (Utility.checkUserPermission(event.getGuild(), event.getClient().getOurUser(), Permissions.BAN)) {
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
				if (user != null) {
					if (args.length == 1) {
						String name = user.getName();
						Message.sendMessageInChannel(event.getChannel(), "**" + name + "** " + Localisation.getMessage(event.getGuild().getID(), "user-banned"));
						event.getGuild().banUser(user);
					} else {
						String name = user.getName();
						Message.sendMessageInChannel(event.getChannel(), "**" + name + "** " + Localisation.getMessage(event.getGuild().getID(), "user-banned-reason") + " " + raw.replaceFirst(args[0], ""));
						event.getGuild().banUser(user);
					}
				} else {
					Message.reply("user-not-found", event.getMessage());
				}
			} else {
				Message.sendMessageInChannel(event.getChannel(), "missing-ban-perm");
			}
		} else {
			Message.reply(help(), event.getMessage());
		}
	}

	public String help() {
		return "ban-help";
	}

	public void executed(boolean success, MessageReceivedEvent event) {
		if(success)
			Utility.commandsExecuted++;
	}
}
