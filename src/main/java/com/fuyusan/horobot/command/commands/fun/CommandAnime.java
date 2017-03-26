package com.fuyusan.horobot.command.commands.fun;

import com.fuyusan.horobot.command.proccessing.Command;
import com.fuyusan.horobot.util.HTMLHandler;
import com.fuyusan.horobot.util.Message;
import com.fuyusan.horobot.util.Utility;
import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;


public class CommandAnime implements Command {

	public boolean called(String[] args, MessageReceivedEvent event) {
		return true;
	}

	public void action(String[] args, String raw, MessageReceivedEvent event) {
		if(args.length > 0) {
			EmbedObject anime = HTMLHandler.requestAnime(args, event.getAuthor().getDisplayName(event.getGuild()) + "#" + event.getAuthor().getDiscriminator(), event.getAuthor().getAvatarURL(), 0);
			if(anime != null) {
				event.getChannel().sendMessage(anime);
			} else {
				Message.reply("html-no-results", event.getMessage());
			}
		} else {
			Message.reply(help(), event.getMessage());
		}
	}

	public String help() {
		return "anime-help";
	}

	public void executed(boolean success, MessageReceivedEvent event) {
		if(success)
			Utility.commandsExecuted++;
	}
}