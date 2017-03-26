package com.fuyusan.horobot.command.commands.music;

import com.fuyusan.horobot.command.proccessing.Command;
import com.fuyusan.horobot.util.Localisation;
import com.fuyusan.horobot.util.Message;
import com.fuyusan.horobot.util.Utility;
import com.fuyusan.horobot.util.music.MusicUtils;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class CommandRepeat implements Command {

	public boolean called(String[] args, MessageReceivedEvent event) {
		return true;
	}

	public void action(String[] args, String raw, MessageReceivedEvent event) {
		if(args.length == 0) {
			if (event.getGuild().getConnectedVoiceChannel() != null) {
				if(MusicUtils.getGuildAudioPlayer(event.getGuild()).scheduler.toggleRepeat()) {
					event.getChannel().sendMessage(Localisation.getMessage(event.getGuild().getID(), "repeat-on"));
				} else {
					event.getChannel().sendMessage(Localisation.getMessage(event.getGuild().getID(), "repeat-off"));
				}
			} else {
				event.getChannel().sendMessage(Localisation.getMessage(event.getChannel().getGuild().getID(), "not-in-channel"));
			}
		} else {
			Message.reply(help(), event.getMessage());
		}
	}

	public String help() {
		return "repeat-help";
	}

	public void executed(boolean success, MessageReceivedEvent event) {
		if(success)
			Utility.commandsExecuted++;
	}
}