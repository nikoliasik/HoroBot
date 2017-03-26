package com.fuyusan.horobot.command.commands.music;

import com.fuyusan.horobot.command.proccessing.Command;
import com.fuyusan.horobot.util.HTMLHandler;
import com.fuyusan.horobot.util.Localisation;
import com.fuyusan.horobot.util.Message;
import com.fuyusan.horobot.util.Utility;
import com.fuyusan.horobot.util.music.MusicUtils;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.util.EmbedBuilder;

import java.awt.*;

public class CommandSong implements Command {

	public boolean called(String[] args, MessageReceivedEvent event) {
		return true;
	}

	public void action(String[] args, String raw, MessageReceivedEvent event) {
		if(args.length == 0) {
			if (event.getGuild().getConnectedVoiceChannel() != null) {
				if(MusicUtils.getGuildAudioPlayer(event.getGuild()).player.getPlayingTrack() != null) {
					AudioPlayer player = MusicUtils.getGuildAudioPlayer(event.getGuild()).player;
					String title = player.getPlayingTrack().getInfo().title;
					String author = player.getPlayingTrack().getInfo().author;
					String uri = player.getPlayingTrack().getInfo().uri;
					String length = formatDuration(player.getPlayingTrack());

					StringBuilder string = new StringBuilder();
					string.append("**Title:** " + title + "\n");
					string.append("**Author:** " + author + "\n");
					string.append("**Length:** " + length + "\n");
					string.append("**URI:** " + uri);

					EmbedBuilder builder = new EmbedBuilder();
					builder.withColor(Color.CYAN);
					builder.withAuthorName("Requested by @" + event.getAuthor().getDisplayName(event.getGuild()) + "#" + event.getAuthor().getDiscriminator());
					builder.withAuthorIcon(event.getAuthor().getAvatarURL());
					builder.appendField("Current Song", string.toString(), false);

					event.getChannel().sendMessage(builder.build());
				} else {
					event.getChannel().sendMessage(Localisation.getMessage(event.getGuild().getID(), "no-song-playing"));
				}
			} else {
				event.getChannel().sendMessage(Localisation.getMessage(event.getChannel().getGuild().getID(), "not-in-channel"));
			}
		} else {
			Message.reply(help(), event.getMessage());
		}
	}

	public String help() {
		return "song-help";
	}

	public void executed(boolean success, MessageReceivedEvent event) {
		if(success)
			Utility.commandsExecuted++;
	}

	public static String formatDuration(AudioTrack track) {
		long totalSeconds = track.getDuration() / 1000;
		long seconds = totalSeconds % 60;
		long minutes = (totalSeconds / 60) % 60;
		long hours = (totalSeconds / 3600);
		return (hours > 0 ? (hours < 10 ? "0" + hours : hours) + ":" : "")
				+ (minutes < 10 ? "0" + minutes : minutes) + ":" + (seconds < 10 ? "0" + seconds : seconds);
	}
}