package com.fuyusan.horobot.command.commands.Wolf;

import com.fuyusan.horobot.command.proccessing.Command;
import com.fuyusan.horobot.database.DataBase;
import com.fuyusan.horobot.util.Message;
import com.fuyusan.horobot.util.Utility;
import com.fuyusan.horobot.wolf.WolfProfileBuilder;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class CommandWolf implements Command {

	@Override
	public boolean called(String[] args, MessageReceivedEvent event) {
		return true;
	}

	@Override
	public void action(String[] args, String raw, MessageReceivedEvent event) {
		if(args.length == 0) {
			DataBase.insertWolf(event.getAuthor());
			Message.sendFile(event.getChannel(), "", WolfProfileBuilder.generateImage(event.getAuthor()));
		} else if(args.length > 1) {
			if(args[1].equals("feed")) {

			} else if(args[1].equals("rename")) {
				// TODO: Arsen you do this bb <3
			} else if(args[1].equals("background")) {

			} else {
				Message.sendMessageInChannel(event.getChannel(), "no-sub-command");
			}
		} else {
			Message.sendMessageInChannel(event.getChannel(), help());
		}
	}

	@Override
	public String help() {
		return "wolf-help";
	}

	@Override
	public void executed(boolean success, MessageReceivedEvent event) {
		if(success)
			Utility.commandsExecuted++;
	}
}