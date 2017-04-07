package com.fuyusan.horobot.command.commands.music;

import com.fuyusan.horobot.command.proccessing.Command;
import com.fuyusan.horobot.util.HTMLHandler;
import com.fuyusan.horobot.util.Localisation;
import com.fuyusan.horobot.util.Message;
import com.fuyusan.horobot.util.Utility;
import com.fuyusan.horobot.util.music.MusicUtils;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

import java.net.URL;

public class CommandPlay implements Command {

	public boolean called(String[] args, MessageReceivedEvent event) {
		return true;
	}

	public void action(String[] args, String raw, MessageReceivedEvent event) {
		if(args.length >= 1) {
			if (event.getGuild().getConnectedVoiceChannel() != null) {
				try {
					URL url = new URL(raw);
					MusicUtils.loadAndPlay(event, event.getGuild().getConnectedVoiceChannel(), args[0]);
				} catch(Exception e) {
					String link = HTMLHandler.searchYouTube(raw);
					if(link != null) {
						MusicUtils.loadAndPlay(event, event.getGuild().getConnectedVoiceChannel(), link);
					} else {
						Message.sendRawMessageInChannel(event.getChannel(), Localisation.getMessage(event.getChannel().getGuild().getID(), "html-no-results"));
					}
				}
			} else {
				Message.sendRawMessageInChannel(event.getChannel(), Localisation.getMessage(event.getChannel().getGuild().getID(), "not-in-channel"));
			}
		} else {
			Message.reply(help(), event.getMessage());
		}
	}

	public String help() {
		return "play-help";
	}

	public void executed(boolean success, MessageReceivedEvent event) {
		if(success)
			Utility.commandsExecuted++;
	}
}