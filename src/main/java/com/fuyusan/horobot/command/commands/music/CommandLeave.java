package com.fuyusan.horobot.command.commands.music;

import com.fuyusan.horobot.command.proccessing.Command;
import com.fuyusan.horobot.util.Localisation;
import com.fuyusan.horobot.util.Message;
import com.fuyusan.horobot.util.Utility;
import com.fuyusan.horobot.util.music.MusicUtils;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.Permissions;

public class CommandLeave implements Command {

	public boolean called(String[] args, MessageReceivedEvent event) {
		if(event.getAuthor().getPermissionsForGuild(event.getGuild()).contains(Permissions.ADMINISTRATOR)) {
			return true;
		}
		return false;
	}

	public void action(String[] args, String raw, MessageReceivedEvent event) {
		if(args.length == 0) {
			if(event.getGuild().getConnectedVoiceChannel() != null) {
				MusicUtils.getGuildAudioPlayer(event.getGuild()).player.stopTrack();
				event.getGuild().getConnectedVoiceChannel().leave();
				Message.sendRawMessageInChannel(event.getChannel(), Localisation.getMessage(event.getGuild().getID(), "left-voice"));
			} else {
				Message.reply("not-in-channel", event.getMessage());
			}
		} else {
			Message.reply(help(), event.getMessage());
		}
	}

	public String help() {
		return "leave-help";
	}

	public void executed(boolean success, MessageReceivedEvent event) {
		if(success)
			Utility.commandsExecuted++;
	}
}