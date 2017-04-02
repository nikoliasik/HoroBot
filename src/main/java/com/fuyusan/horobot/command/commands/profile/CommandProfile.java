package com.fuyusan.horobot.command.commands.profile;

import com.fuyusan.horobot.command.proccessing.Command;
import com.fuyusan.horobot.database.DataBase;
import com.fuyusan.horobot.util.Cooldowns;
import com.fuyusan.horobot.util.Message;
import com.fuyusan.horobot.util.Utility;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class CommandProfile implements Command {

	@Override
	public boolean called(String[] args, MessageReceivedEvent event) {
		return true;
	}

	@Override
	public void action(String[] args, String raw, MessageReceivedEvent event) {
		if(args.length == 0) {
			if(!Cooldowns.onCooldown("profile-stats", 300000, event.getAuthor())) {
				Cooldowns.putOnCooldown("", event.getAuthor());
				DataBase.insertWolf(event.getAuthor());

			}
		} else {
			Message.sendMessageInChannel(event.getChannel(), help());
		}
	}

	@Override
	public String help() {
		return "profile-help";
	}

	@Override
	public void executed(boolean success, MessageReceivedEvent event) {
		if(success)
			Utility.commandsExecuted++;
	}
}