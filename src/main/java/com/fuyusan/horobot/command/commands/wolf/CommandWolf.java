package com.fuyusan.horobot.command.commands.wolf;

import com.fuyusan.horobot.command.proccessing.Command;
import com.fuyusan.horobot.database.DataBase;
import com.fuyusan.horobot.util.Localisation;
import com.fuyusan.horobot.util.Message;
import com.fuyusan.horobot.util.Utility;
import com.fuyusan.horobot.wolf.WolfCosmetics;
import com.fuyusan.horobot.wolf.WolfProfileBuilder;
import com.fuyusan.horobot.wolf.WolfTemplate;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.stream.Collectors;

public class CommandWolf implements Command {

	@Override
	public boolean called(String[] args, MessageReceivedEvent event) {
		return true;
	}

	@Override
	public void action(String[] args, String raw, MessageReceivedEvent event) {
		if (args.length == 0) {
			DataBase.insertWolf(event.getAuthor());
			Message.sendFile(event.getChannel(), "",
					event.getAuthor().getID() + "-wolf.png",
					new ByteArrayInputStream(WolfProfileBuilder.generateImage(event.getAuthor())));
		} else if (args.length > 1) {
			if (args[0].equals("feed")) {
				if(args.length == 2) {
					if(WolfCosmetics.foods.containsKey(args[1])) {
						WolfTemplate template = DataBase.wolfQuery(event.getAuthor());
						int hunger = template.getHunger();
						int maxHunger = template.getMaxHunger();
						int foodValue = WolfCosmetics.foods.get(args[1]);
						hunger += foodValue;
						DataBase.updateWolf(event.getAuthor(), "hunger", hunger);

						StringBuilder message = new StringBuilder(String.format(Localisation.getMessage(event.getGuild().getID(), "feed-wolf") + "\n", args[1]));
						if (hunger >= maxHunger) {
							for(int i = 0; i <= Math.floor(maxHunger / hunger); i++) {
								int nextHunger = (7 + template.getLevel());
								DataBase.updateWolf(event.getAuthor(), "hunger", maxHunger - hunger);
								DataBase.updateWolf(event.getAuthor(), "maxHunger", nextHunger);
								DataBase.updateWolf(event.getAuthor(), "level", template.getLevel() + 1);
								message.append(String.format("**LEVEL UP** Your wolf leveled up and is now level %s\n", template.getLevel() + i + 1));
							}
						}
						Message.sendFile(event.getChannel(), message.toString(),
								event.getAuthor().getID() + "-wolf.png",
								new ByteArrayInputStream(WolfProfileBuilder.generateImage(event.getAuthor())));
					}
				}
			} else if (args[0].equals("rename")) {
				String temp = Arrays.stream(args).skip(1).collect(Collectors.joining(" "));
				if(temp.length() <= 30) {
					DataBase.updateWolf(event.getAuthor(), "name", temp);
					Message.sendMessageInChannel(event.getChannel(), "name-success", temp);
				} else {
					Message.sendMessageInChannel(event.getChannel(), "name-too-long");
				}
			} else if (args[0].equals("background")) {
				String temp = Arrays.stream(args).skip(1).collect(Collectors.joining(" "));
				System.out.println(temp);
				if(!WolfCosmetics.backgrounds.containsKey(temp)) {
					Message.sendMessageInChannel(event.getChannel(), "wrong-background");
					return;
				}
				DataBase.updateWolf(event.getAuthor(), "background", WolfCosmetics.backgrounds.get(temp));
				Message.sendMessageInChannel(event.getChannel(), "background-success", temp);
			} else {
				Message.sendMessageInChannel(event.getChannel(), "no-sub-command");
			}
		} else {
			Message.sendMessageInChannel(event.getChannel(), help());
		}
	}

	@Override
	public String help() {
		return "wolf-help";
	}

	@Override
	public void executed(boolean success, MessageReceivedEvent event) {
		if (success)
			Utility.commandsExecuted++;
	}
}