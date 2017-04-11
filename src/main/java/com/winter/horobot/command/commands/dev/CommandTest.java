package com.winter.horobot.command.commands.dev;

import com.winter.horobot.command.proccessing.Command;
import com.winter.horobot.database.DataBase;
import com.winter.horobot.profile.ProfileBuilder;
import com.winter.horobot.profile.ProfileTemplate;
import com.winter.horobot.util.Message;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

import java.io.ByteArrayInputStream;

public class CommandTest implements Command {

	@Override
	public boolean called(String[] args, MessageReceivedEvent event) {
		return false;
	}

	@Override
	public void action(String[] args, String raw, MessageReceivedEvent event) {
		ProfileTemplate template = DataBase.queryUser(event.getAuthor());
		Message.sendFile(
				event.getChannel(),
				"**LEVEL UP!**",
				"level-up.png",
				new ByteArrayInputStream(ProfileBuilder.generateLevelUp(event.getAuthor(), (template.getLevel() + 1))));
	}

	@Override
	public String help() {
		return null;
	}

	@Override
	public void executed(boolean success, MessageReceivedEvent event) {

	}
}
