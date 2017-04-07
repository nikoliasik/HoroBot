package com.fuyusan.horobot.command.commands.dev;

import com.fuyusan.horobot.command.proccessing.Command;
import com.fuyusan.horobot.database.DataBase;
import com.fuyusan.horobot.profile.ProfileBuilder;
import com.fuyusan.horobot.profile.ProfileTemplate;
import com.fuyusan.horobot.util.Message;
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
