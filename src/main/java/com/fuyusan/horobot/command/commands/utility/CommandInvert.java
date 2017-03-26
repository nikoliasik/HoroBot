package com.fuyusan.horobot.command.commands.utility;

import com.fuyusan.horobot.command.proccessing.Command;
import com.fuyusan.horobot.util.Message;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.util.EmbedBuilder;

import java.awt.*;

public class CommandInvert implements Command {

	public boolean called(String[] args, MessageReceivedEvent event) {
		return true;
	}

	public void action(String[] args, String raw, MessageReceivedEvent event) {
		if(args.length > 0) {
			StringBuilder inverted = new StringBuilder();
			for(char c : raw.toCharArray()) {
				inverted.insert(0, c);
			}

			EmbedBuilder builder = new EmbedBuilder();
			builder.withColor(Color.CYAN);
			builder.withAuthorName("Requested by @" + event.getAuthor().getDisplayName(event.getGuild()) + "#" + event.getAuthor().getDiscriminator());
			builder.withAuthorIcon(event.getAuthor().getAvatarURL());
			builder.appendField("Here's the inverted text", inverted.toString(), false);

			event.getChannel().sendMessage(builder.build());
		} else {
			Message.reply(help(), event.getMessage());
		}
	}

	public String help() {
		return "invert-help";
	}

	public void executed(boolean success, MessageReceivedEvent event) {

	}
}
