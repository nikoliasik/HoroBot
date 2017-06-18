package com.winter.horobot.command.impl.image;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.winter.horobot.command.proccessing.Command;
import com.winter.horobot.command.proccessing.CommandType;
import com.winter.horobot.util.HTTPHandler;
import com.winter.horobot.util.Message;
import com.winter.horobot.util.Utility;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.util.EmbedBuilder;

import java.awt.*;
import java.net.URL;

public class CommandDanbooru implements Command {

	@Override
	public boolean called(String[] args, MessageReceivedEvent event) {
		return event.getChannel().isNSFW();
	}

	@Override
	public CommandType getType() {
		return CommandType.IMAGE;
	}

	@Override
	public void action(String[] args, String raw, MessageReceivedEvent event) {
		/*if(args.length > 0) {
			EmbedBuilder builder = new EmbedBuilder();
			builder.withAuthorIcon(Utility.getAvatar(event.getAuthor()));
			builder.withAuthorName(event.getAuthor().getName());
			builder.withColor(Color.CYAN);
			try {
				String image = HTTPHandler.requestDanbooru(args);
				URL url = new URL(image);
				builder.withImage(image);
				Message.sendEmbed(event.getChannel(), "", builder.build(), false);
			} catch (Exception e) {
				if (e instanceof UnirestException) {
					Message.sendMessageInChannel(event.getChannel(), "html-error");
				} else {
					Message.sendMessageInChannel(event.getChannel(), "html-no-results");
				}
			}
		} else {
			Message.sendMessageInChannel(event.getChannel(), help());
		}*/
		Message.sendRawMessageInChannel(event.getChannel(), "This command is currently locked from public use because of a very nasty and persistent issue, check back later! Also spam Fox with encouraging words to help speed up the process of fixing this. He also loves to be petted... Oh! And belly rubs, give him those too!");
	}

	@Override
	public String help() {
		return "help-danbooru";
	}
}