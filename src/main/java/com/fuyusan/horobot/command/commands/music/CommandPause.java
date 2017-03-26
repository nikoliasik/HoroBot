package com.fuyusan.horobot.command.commands.music;

import com.fuyusan.horobot.command.proccessing.Command;
import com.fuyusan.horobot.core.Main;
import com.fuyusan.horobot.util.Localisation;
import com.fuyusan.horobot.util.Message;
import com.fuyusan.horobot.util.Utility;
import com.fuyusan.horobot.util.music.MusicUtils;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class CommandPause implements Command {

	public boolean called(String[] args, MessageReceivedEvent event) {
		return true;
	}

	public void action(String[] args, String raw, MessageReceivedEvent event) {
		if(args.length == 0) {
			if (event.getGuild().getConnectedVoiceChannel() != null) {
				MusicUtils.pause(event.getGuild(), MusicUtils.getGuildAudioPlayer(event.getGuild()));
				event.getChannel().sendMessage(Localisation.getMessage(event.getChannel().getGuild().getID(), "paused"));
			} else {
				event.getChannel().sendMessage(Localisation.getMessage(event.getChannel().getGuild().getID(), "not-in-channel"));
			}
		} else {
			Message.reply(help(), event.getMessage());
		}
	}

	public String help() {
		return "pause-help";
	}

	public void executed(boolean success, MessageReceivedEvent event) {
		if(success)
			Utility.commandsExecuted++;
	}
}