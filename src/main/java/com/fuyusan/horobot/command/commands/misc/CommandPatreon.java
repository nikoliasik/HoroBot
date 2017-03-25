package com.fuyusan.horobot.command.commands.misc;

import com.fuyusan.horobot.command.proccessing.Command;
import com.fuyusan.horobot.util.Message;
import com.fuyusan.horobot.util.Utility;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.util.EmbedBuilder;

import java.awt.*;

public class CommandPatreon implements Command {

	public boolean called(String[] args, MessageReceivedEvent event) {
		return true;
	}

	public void action(String[] args, String raw, MessageReceivedEvent event) {
		if(args.length == 0) {
			EmbedBuilder builder = new EmbedBuilder();
			builder.withFooterIcon("https://cdn.discordapp.com/avatars/288996157202497536/292e2ad51032535171b8e852444fb958.webp?size=256");
			builder.withFooterText("Winter#9815 288996157202497536");
			builder.withColor(Color.CYAN);
			builder.appendField("HoroBot Patreon", "https://www.patreon.com/HoroBot", false);

			event.getChannel().sendMessage("", builder.build(), false);
		} else {
			Message.reply(help(), event.getMessage());
		}
	}

	public String help() {
		return "patreon-help";
	}

	public void executed(boolean success, MessageReceivedEvent event) {
		if(success)
			Utility.commandsExecuted++;
	}
}