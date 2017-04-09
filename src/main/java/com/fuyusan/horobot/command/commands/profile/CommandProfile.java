package com.fuyusan.horobot.command.commands.profile;

import com.fuyusan.horobot.command.proccessing.Command;
import com.fuyusan.horobot.database.DataBase;
import com.fuyusan.horobot.profile.ProfileBuilder;
import com.fuyusan.horobot.util.Cooldowns;
import com.fuyusan.horobot.util.Message;
import com.fuyusan.horobot.util.Utility;
import com.fuyusan.horobot.wolf.WolfCosmetics;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.stream.Collectors;

public class CommandProfile implements Command {

	@Override
	public boolean called(String[] args, MessageReceivedEvent event) {
		return true;
	}

	@Override
	public void action(String[] args, String raw, MessageReceivedEvent event) {
		if(args.length == 0) {
			if(!Cooldowns.onCooldown("profile-stats-" + event.getAuthor().getID(), 10000, event.getAuthor())) {
				Cooldowns.putOnCooldown("profile-stats-" + event.getAuthor().getID(), event.getAuthor());
				DataBase.insertUser(event.getAuthor());
				Message.sendFile(
						event.getChannel(),
						ProfileBuilder.generateEmbed(
								event.getGuild(),
								event.getAuthor()),
						"Here's your profile",
						event.getAuthor().getID() + "profile.png",
						new ByteArrayInputStream(ProfileBuilder.generateProfileImage(
								event.getAuthor())));
			} else {
				Message.sendMessageInChannel(event.getChannel(), "on-cooldown2", Utility.formatTime(Cooldowns.getRemaining("profile-stats-" + event.getAuthor().getID(), 10000, event.getAuthor())));
			}
		} else if(args.length >= 1) {
			if(args[0].equals("background")) {
				String temp = Arrays.stream(args).skip(1).collect(Collectors.joining(" "));
				if ("background".equals(WolfCosmetics.getType(temp))) {
					String item = DataBase.queryItem(event.getAuthor(), temp);
					if(item != null) {
					DataBase.updateUser(event.getAuthor(), "background", temp);
					Message.sendMessageInChannel(event.getChannel(), "background-updated", temp);
					} else {
						Message.sendMessageInChannel(event.getChannel(), "no-item");
					}
				} else {
					Message.sendMessageInChannel(event.getChannel(), "invalid-item");
				}
			} else if(args[0].equals("info")) {
				String temp = Arrays.stream(args).skip(1).collect(Collectors.joining(" "));
				if(temp.length() <= 88) {
					DataBase.updateUser(event.getAuthor(), "description", temp);
					Message.sendMessageInChannel(event.getChannel(), "desc-changed");
				} else {
					Message.sendMessageInChannel(event.getChannel(), "desc-too-long");
				}
			} else if (args[0].equals("capsule")) {
				if(DataBase.queryUser(event.getAuthor()).getFoxCoins() >= 100) {
					String drop = WolfCosmetics.drop(event.getAuthor());
					if (drop != null) {
						DataBase.updateUser(event.getAuthor(), "foxCoins", (DataBase.queryUser(event.getAuthor()).getFoxCoins() - 100));
						DataBase.insertItem(event.getAuthor(), drop);
						Message.sendMessageInChannel(
								event.getChannel(),
								"capsule-opened",
								drop);
					} else {
						Message.sendMessageInChannel(event.getChannel(), "got-everything");
					}
				} else {
					Message.sendMessageInChannel(event.getChannel(), "insufficient-funds");
				}
			} else {
				Message.sendMessageInChannel(event.getChannel(), "no-sub-command");
			}
		}
	}

	@Override
	public String help() {
		return "profile-help";
	}

	@Override
	public void executed(boolean success, MessageReceivedEvent event) {
		if(success)
			Utility.commandsExecuted++;
	}
}