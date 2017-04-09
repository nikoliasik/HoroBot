package com.fuyusan.horobot.command.commands.wolf;

import com.fuyusan.horobot.command.proccessing.Command;
import com.fuyusan.horobot.database.DataBase;
import com.fuyusan.horobot.scheduler.HoroTask;
import com.fuyusan.horobot.util.Cooldowns;
import com.fuyusan.horobot.util.Localisation;
import com.fuyusan.horobot.util.Message;
import com.fuyusan.horobot.util.Utility;
import com.fuyusan.horobot.wolf.WolfCosmetics;
import com.fuyusan.horobot.wolf.WolfProfileBuilder;
import com.fuyusan.horobot.wolf.WolfTemplate;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class CommandWolf implements Command {

	@Override
	public boolean called(String[] args, MessageReceivedEvent event) {
		return true;
	}

	@Override
	public void action(String[] args, String raw, MessageReceivedEvent event) {
		if (args.length == 0) {
			if(!Cooldowns.onCooldown("wolf-stats-" + event.getAuthor().getID(), 10000, event.getAuthor())) {
				Cooldowns.putOnCooldown("wolf-stats-" + event.getAuthor().getID(), event.getAuthor());
				DataBase.insertWolf(event.getAuthor());
				Message.sendFile(
						event.getChannel(),
						WolfProfileBuilder.generateEmbed(event.getGuild(), event.getAuthor()),
						"Here's your wolf",
						event.getAuthor().getID() + "wolf.png",
						new ByteArrayInputStream(
								WolfProfileBuilder.generateImage(
										event.getAuthor())));
			} else {
				Message.sendMessageInChannel(event.getChannel(), "on-cooldown", Utility.formatTime(Cooldowns.getRemaining("wolf-stats-" + event.getAuthor().getID(), 10000, event.getAuthor())));
			}
		} else if (args.length >= 1) {
			if (args[0].equals("feed")) {
				if (args.length == 2) {
					if (WolfCosmetics.foods.containsKey(args[1])) {
						if (!Cooldowns.onCooldown("wolf-feed-" + event.getAuthor().getID(), 7200000, event.getAuthor())) {
							Cooldowns.putOnCooldown("wolf-feed-" + event.getAuthor().getID(), event.getAuthor());
							new HoroTask(event.getAuthor().getID() + "-note") {
								@Override
								public void run() {
									Message.sendPM("wolf-ready", event.getAuthor());
								}
							}.delay(7200000);
							WolfTemplate template = DataBase.wolfQuery(event.getAuthor());
							if (template == null) {
								DataBase.insertWolf(event.getAuthor());
								template = DataBase.wolfQuery(event.getAuthor());
							}
							int hunger = template.getHunger();
							int maxHunger = template.getMaxHunger();
							int foodValue = WolfCosmetics.foods.get(args[1]);
							hunger += foodValue;
							DataBase.updateWolf(event.getAuthor(), "hunger", hunger);
							DataBase.updateWolf(event.getAuthor(), "fedTimes", template.getFedTimes() + 1);

							StringBuilder message = new StringBuilder(String.format(Localisation.getMessage(event.getGuild().getID(), "feed-wolf") + "\n", args[1]));
							if (hunger >= maxHunger) {
								int nextHunger = (8);
								DataBase.updateWolf(event.getAuthor(), "hunger", 0);
								DataBase.updateWolf(event.getAuthor(), "maxHunger", nextHunger);
								DataBase.updateWolf(event.getAuthor(), "level", template.getLevel() + 1);
								message.append(String.format("**LEVEL UP!** Your wolf is now level **%s**!\n", template.getLevel() + 1));
							}

							Random rand = new Random();
							int drop = rand.nextInt(100);
							if (drop <= 10) {
								String result = WolfCosmetics.drop(event.getAuthor());
								if (result != null) {
									DataBase.insertItem(event.getAuthor(), result);
									message.append("**ITEM DROP!** You got **" + result + "**!\n");
								}
							}

							DataBase.updateUser(event.getAuthor(), "foxCoins", (DataBase.queryUser(event.getAuthor()).getFoxCoins() + 100));
							message.append("**+100** Coins for feeding your wolf!\n");

							Message.sendFile(
									event.getChannel(),
									WolfProfileBuilder.generateEmbed(event.getGuild(), event.getAuthor()),
									message.toString(),
									event.getAuthor().getID() + "wolf.png",
									new ByteArrayInputStream(
											WolfProfileBuilder.generateImage(
													event.getAuthor())));
						} else {
							Message.sendMessageInChannel(event.getChannel(), "wolf-full", Utility.formatTime(Cooldowns.getRemaining("wolf-feed-" + event.getAuthor().getID(), 7200000, event.getAuthor())));
						}
					} else {
						Message.sendMessageInChannel(event.getChannel(), "no-food");
					}
				} else {
					Message.sendMessageInChannel(event.getChannel(), "feed-help");
				}
			} else if (args[0].equals("rename")) {
				String temp = Arrays.stream(args).skip(1).collect(Collectors.joining(" "));
				if (temp.length() <= 30) {
					DataBase.updateWolf(event.getAuthor(), "name", temp);
					Message.sendMessageInChannel(event.getChannel(), "name-success", temp);
				} else {
					Message.sendMessageInChannel(event.getChannel(), "name-too-long");
				}
			} else if (args[0].equals("equip")) {
				String temp = Arrays.stream(args).skip(1).collect(Collectors.joining(" "));
				String item = DataBase.queryItem(event.getAuthor(), temp);
				if (item != null) {
					DataBase.updateWolf(event.getAuthor(), WolfCosmetics.getType(item), temp);
					Message.sendMessageInChannel(event.getChannel(), "background-success", temp);
				} else {
					Message.sendMessageInChannel(event.getChannel(), "no-item");
				}
			} else if (args[0].equals("strip")) {
				DataBase.updateWolf(event.getAuthor(), "background", "None");
				DataBase.updateWolf(event.getAuthor(), "hat", "None");
				DataBase.updateWolf(event.getAuthor(), "body", "None");
				DataBase.updateWolf(event.getAuthor(), "paws", "None");
				DataBase.updateWolf(event.getAuthor(), "tail", "None");
				DataBase.updateWolf(event.getAuthor(), "shirt", "None");
				DataBase.updateWolf(event.getAuthor(), "nose", "None");
				DataBase.updateWolf(event.getAuthor(), "eye", "None");
				DataBase.updateWolf(event.getAuthor(), "neck", "None");
				Message.sendMessageInChannel(event.getChannel(), "stripped-items");
			} else if (args[0].equals("inventory")) {
				ArrayList<String> items = (ArrayList<String>) DataBase.queryItems(event.getAuthor());
				Message.sendRawMessageInChannel(
						event.getChannel(),
						"Here's your inventory!\n" +
								"```\n" +
								Utility.listAsString(items) + "\n" +
								"```");
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