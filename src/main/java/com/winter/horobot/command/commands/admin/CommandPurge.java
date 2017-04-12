package com.winter.horobot.command.commands.admin;

import com.winter.horobot.command.proccessing.Command;
import com.winter.horobot.util.Localisation;
import com.winter.horobot.util.Message;
import com.winter.horobot.util.Utility;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.Permissions;

public class CommandPurge implements Command {

	public boolean called(String[] args, MessageReceivedEvent event) {
		if(event.getAuthor().getRolesForGuild(event.getGuild()).contains(Permissions.ADMINISTRATOR))
			return true;
		return false;
	}

	public void action(String[] args, String raw, MessageReceivedEvent event) {
		if(args.length == 1) {
			if (Utility.checkUserPermission(event.getGuild(), event.getClient().getOurUser(), Permissions.MANAGE_MESSAGES) &&
					event.getChannel().getModifiedPermissions(event.getClient().getOurUser()).contains(Permissions.MANAGE_MESSAGES)) {
				try {
					int messages = Integer.parseInt(args[0]);
					event.getChannel().getMessageHistory(messages).bulkDelete();
					Message.sendMessageInChannel(event.getChannel(),"purged-messages", messages);
				} catch (Exception e) {
					e.printStackTrace();
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
