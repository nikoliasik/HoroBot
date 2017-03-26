package com.fuyusan.horobot.command.commands.music;

import com.fuyusan.horobot.command.proccessing.Command;
import com.fuyusan.horobot.util.Localisation;
import com.fuyusan.horobot.util.Message;
import com.fuyusan.horobot.util.Utility;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IVoiceChannel;
import sx.blah.discord.handle.obj.Permissions;

public class CommandJoin implements Command {

	public boolean called(String[] args, MessageReceivedEvent event) {
		if(event.getAuthor().getPermissionsForGuild(event.getGuild()).contains(Permissions.ADMINISTRATOR)) {
			return true;
		}
		return false;
	}

	public void action(String[] args, String raw, MessageReceivedEvent event) {
		if(args.length == 1) {
			IVoiceChannel channel = null;
			if(channel == null)
				channel = event.getGuild().getVoiceChannelByID(args[0]);
			if(channel == null) {
				if (event.getGuild().getVoiceChannelsByName(args[0]).size() == 1) {
					channel = event.getGuild().getVoiceChannelsByName(args[0]).get(0);
				}
			}

			if(channel != null) {
				channel.join();
				Message.replyInChannel(Localisation.getMessage(event.getGuild().getID(), "joined-voice"), event.getMessage());
			} else {
				Message.reply("no-voice-channel", event.getMessage());
			}
		} else {
			if(event.getAuthor().getVoiceStateForGuild(event.getGuild()).getChannel() != null) {
				event.getGuild().getVoiceChannelByID(event.getAuthor().getVoiceStateForGuild(event.getGuild()).getChannel().getID()).join();
				Message.replyInChannel(Localisation.getMessage(event.getGuild().getID(), "joined-voice"), event.getMessage());
			} else {
				Message.reply("no-voice-channel", event.getMessage());
			}
		}
	}

	public String help() {
		return "join-help";
	}

	public void executed(boolean success, MessageReceivedEvent event) {
		if(success)
			Utility.commandsExecuted++;
	}
}