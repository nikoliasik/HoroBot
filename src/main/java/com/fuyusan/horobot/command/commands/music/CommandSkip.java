package com.fuyusan.horobot.command.commands.music;

import com.fuyusan.horobot.command.proccessing.Command;
import com.fuyusan.horobot.util.Localisation;
import com.fuyusan.horobot.util.Message;
import com.fuyusan.horobot.util.Utility;
import com.fuyusan.horobot.util.music.MusicUtils;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class CommandSkip implements Command {

	public boolean called(String[] args, MessageReceivedEvent event) {
		return true;
	}

	public void action(String[] args, String raw, MessageReceivedEvent event) {
		if(args.length == 0) {
			MusicUtils.skipTrack(event.getChannel());
			event.getChannel().sendMessage(Localisation.getMessage(event.getGuild().getID(), "track-skipped"));
		} else {
			Message.reply(help(), event.getMessage());
		}
	}

	public String help() {
		return "skip-help";
	}

	public void executed(boolean success, MessageReceivedEvent event) {
		if(success)
			Utility.commandsExecuted++;
	}
}