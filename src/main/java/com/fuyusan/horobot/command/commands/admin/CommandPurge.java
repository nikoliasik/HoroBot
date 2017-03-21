package com.fuyusan.horobot.command.commands.admin;

import com.fuyusan.horobot.command.proccessing.Command;
import com.fuyusan.horobot.util.Localisation;
import com.fuyusan.horobot.util.Message;
import com.fuyusan.horobot.util.Utility;
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
			try {
				int messages = Integer.parseInt(args[0]);
				event.getChannel().getMessageHistory(messages).bulkDelete();
				event.getChannel().sendMessage(String.format(Localisation.getMessage(event.getGuild().getID(), "purged-messages"), messages));
			} catch(Exception e) { e.printStackTrace(); }
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
