package com.fuyusan.horobot.command.commands.utility;

import com.fuyusan.horobot.command.proccessing.Command;
import com.fuyusan.horobot.util.HTMLHandler;
import com.fuyusan.horobot.util.Message;
import com.fuyusan.horobot.util.Utility;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class CommandTranslate implements Command {

	public boolean called(String[] args, MessageReceivedEvent event) {
		return true;
	}

	public void action(String[] args, String raw, MessageReceivedEvent event) {
		if(args.length > 2) {
			String text = raw.replaceFirst(args[0] + " " + args[1], "");
			event.getChannel().sendMessage(HTMLHandler.requestTranslation(args[0], args[1], text,event.getAuthor().getDisplayName(event.getGuild()) + "#" + event.getAuthor().getDiscriminator(), event.getAuthor().getAvatarURL()));
		} else {
			Message.reply(help(), event.getMessage());
		}
	}

	public String help() {
		return "translate-help";
	}

	public void executed(boolean success, MessageReceivedEvent event) {
		if(success)
			Utility.commandsExecuted++;
	}
}