package com.winter.horobot.command.commands.animals.fox;

import com.winter.horobot.animals.fox.FoxProfileBuilder;
import com.winter.horobot.animals.wolf.WolfProfileBuilder;
import com.winter.horobot.command.proccessing.Command;
import com.winter.horobot.command.proccessing.CommandType;
import com.winter.horobot.database.DataBase;
import com.winter.horobot.util.Cooldowns;
import com.winter.horobot.util.Message;
import com.winter.horobot.util.Utility;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.RequestBuffer;

import java.io.ByteArrayInputStream;

public class CommandFox implements Command {

	@Override
	public boolean called(String[] args, MessageReceivedEvent event) {
		return true;
	}

	@Override
	public CommandType getType() {
		return CommandType.ANIMAL;
	}

	@Override
	public void action(String[] args, String raw, MessageReceivedEvent event) {
		if (args.length == 0) {
			if(!Cooldowns.onCooldown("fox-stats-" + event.getAuthor().getStringID(), 10000, event.getAuthor())) {
				Cooldowns.putOnCooldown("fox-stats-" + event.getAuthor().getStringID(), event.getAuthor());
				DataBase.insertFox(event.getAuthor());
				Message.sendFile(
						event.getChannel(),
						FoxProfileBuilder.generateEmbed(event.getAuthor()),
						"Here's your fox",
						"fox.gif",
						new ByteArrayInputStream(
								FoxProfileBuilder.generateProfile(
										event.getAuthor())));
			} else {
				Message.sendMessageInChannel(event.getChannel(), "on-cooldown", Utility.formatTime(Cooldowns.getRemaining("fox-stats-" + event.getAuthor().getStringID(), 10000, event.getAuthor())));
			}
		}
	}

	@Override
	public String help() {
		return "fox-help";
	}
}
