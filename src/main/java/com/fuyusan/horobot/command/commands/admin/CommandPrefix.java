package com.fuyusan.horobot.command.commands.admin;

import com.fuyusan.horobot.command.proccessing.Command;
import com.fuyusan.horobot.database.DataBase;
import com.fuyusan.horobot.util.Localisation;
import com.fuyusan.horobot.util.Message;
import com.fuyusan.horobot.util.Utility;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.Permissions;

public class CommandPrefix implements Command {

	public boolean called(String[] args, MessageReceivedEvent event) {
		if(event.getAuthor().getPermissionsForGuild(event.getGuild()).contains(Permissions.ADMINISTRATOR)) {
			return true;
		} else {
			return false;
		}
	}

	public void action(String[] args, String raw, MessageReceivedEvent event) {
		if(args.length == 0) {
			event.getChannel().sendMessage(String.format(Localisation.getMessage(event.getGuild().getID(), "current-prefix"), DataBase.guildQuery(event.getGuild().getID(), "prefix")));
		} else if(args.length == 1) {
			DataBase.insertGuild(event.getGuild().getID());
			DataBase.updateGuild(event.getGuild().getID(), "prefix", args[0]);
			event.getChannel().sendMessage(String.format(Localisation.getMessage(event.getGuild().getID(), "prefix-updated"), args[0]));
		} else {
			Message.reply(help(), event.getMessage());
		}
	}

	public String help() {
		return "prefix-help";
	}

	public void executed(boolean success, MessageReceivedEvent event) {
		if(success)
			Utility.commandsExecuted++;
	}
}