package com.winter.horobot.command.commands.admin;

import com.winter.horobot.command.proccessing.Command;
import com.winter.horobot.command.proccessing.CommandType;
import com.winter.horobot.util.Localisation;
import com.winter.horobot.util.Message;
import com.winter.horobot.util.Utility;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.Permissions;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MessageHistory;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RequestBuffer;

public class CommandPurge implements Command {

	public boolean called(String[] args, MessageReceivedEvent event) {
		return event.getAuthor().getPermissionsForGuild(event.getGuild()).contains(Permissions.ADMINISTRATOR);
	}

	@Override
	public CommandType getType() {
		return CommandType.ADMIN;
	}

	public void action(String[] args, String raw, MessageReceivedEvent event) {
		if(args.length == 1) {
			if (Utility.checkUserPermission(event.getGuild(), event.getClient().getOurUser(), Permissions.MANAGE_MESSAGES)) {
				int messages;
				try {
					messages = Integer.parseInt(args[0]);
				} catch (Exception e) {
					Message.sendMessageInChannel(event.getChannel(), "no-number");
					return;
				}
				int i = 0;
				try {
					try {
						event.getChannel().getMessageHistory(messages).bulkDelete();
					} catch (DiscordException e) {
						Message.sendMessageInChannel(event.getChannel(), "limit-exceeded");
						return;
					}
					Message.sendMessageInChannel(event.getChannel(), "purged-messages", messages);
				} catch (MissingPermissionsException e) {
					Message.sendMessageInChannel(event.getChannel(), "missing-messages-manage-perm");
				} catch (ArrayIndexOutOfBoundsException e) {
					Message.sendMessageInChannel(event.getChannel(), "purged-messages", i);
				}
			} else {
				Message.sendMessageInChannel(event.getChannel(), "missing-messages-manage-perm");
			}
		} else {
			Message.reply(help(), event.getMessage());
		}
	}

	public String help() {
		return "purge-help";
	}

	public void executed(boolean success, MessageReceivedEvent event) {
		if(success)
			Utility.commandsExecuted++;
	}
}
