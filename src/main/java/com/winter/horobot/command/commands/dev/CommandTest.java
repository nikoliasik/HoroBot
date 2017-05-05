package com.winter.horobot.command.commands.dev;

import com.winter.horobot.command.proccessing.Command;
import com.winter.horobot.command.proccessing.CommandType;
import com.winter.horobot.database.DataBase;
import com.winter.horobot.profile.ProfileBuilder;
import com.winter.horobot.profile.ProfileTemplate;
import com.winter.horobot.util.Message;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.MessageTokenizer;
import sx.blah.discord.util.RequestBuffer;

import java.io.ByteArrayInputStream;

public class CommandTest implements Command {

	@Override
	public boolean called(String[] args, MessageReceivedEvent event) {
		return event.getAuthor().getStringID().equals("288996157202497536")
				|| event.getAuthor().getStringID().equals("155954930191040513");
	}

	@Override
	public void action(String[] args, String raw, MessageReceivedEvent event) {

	}

	@Override
	public String help() {
		return null;
	}

	@Override
	public CommandType getType() {
		return CommandType.DEVELOPER;
	}

	@Override
	public void executed(boolean success, MessageReceivedEvent event) {

	}
}
