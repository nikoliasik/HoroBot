package com.winter.horobot.command.commands.animals.wolf;

import com.winter.horobot.animals.Item;
import com.winter.horobot.command.proccessing.Command;
import com.winter.horobot.command.proccessing.CommandType;
import com.winter.horobot.database.DataBase;
import com.winter.horobot.scheduler.HoroTask;
import com.winter.horobot.util.Cooldowns;
import com.winter.horobot.util.Localisation;
import com.winter.horobot.util.Message;
import com.winter.horobot.util.Utility;
import com.winter.horobot.animals.wolf.WolfCosmetics;
import com.winter.horobot.animals.wolf.WolfProfileBuilder;
import com.winter.horobot.animals.wolf.WolfTemplate;
import org.apache.commons.lang3.text.WordUtils;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

import javax.imageio.ImageIO;
import java.io.*;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;

public class CommandWolf implements Command {

	@Override
	public boolean called(String[] args, MessageReceivedEvent event) {
		return true;
	}

	@Override
	public CommandType getType() {
		return CommandType.ANIMAL;
	}

	@Override
	public void action(String[] args, String raw, MessageReceivedEvent event) {
		if (args.length == 0) {
			if(!Cooldowns.onCooldown("wolf-stats-" + event.getAuthor().getStringID(), 10000, event.getAuthor())) {
				Cooldowns.putOnCooldown("wolf-stats-" + event.getAuthor().getStringID(), event.getAuthor());
				DataBase.insertWolf(event.getAuthor());
				Message.sendFile(
						event.getChannel(),
						WolfProfileBuilder.generateEmbed(event.getAuthor()),
						"Here's your wolf",
						"wolf.png",
						new ByteArrayInputStream(
								WolfProfileBuilder.generateProfile(
										event.getAuthor())));
			} else {
				Message.sendMessageInChannel(event.getChannel(), "on-cooldown", Utility.formatTime(Cooldowns.getRemaining("wolf-stats-" + event.getAuthor().getStringID(), 10000, event.getAuthor())));
			}
		} else if (args.length >= 1) {
			if (event.getMessage().getMentions().size() == 1) {
				if(!event.getMessage().getMentions().get(0).isBot()) {
					if (!Cooldowns.onCooldown("wolf-stats-" + event.getAuthor().getStringID(), 10000, event.getAuthor())) {
						Cooldowns.putOnCooldown("wolf-stats-" + event.getAuthor().getStringID(), event.getAuthor());
						DataBase.insertWolf(event.getMessage().getMentions().get(0));
						Message.sendFile(
								event.getChannel(),
								WolfProfileBuilder.generateEmbed(event.getMessage().getMentions().get(0)),
								"Here's " + event.getMessage().getMentions().get(0).getName() + "'s wolf",
								"wolf.png",
								new ByteArrayInputStream(
										WolfProfileBuilder.generateProfile(
												event.getMessage().getMentions().get(0))));
					} else {
						Message.sendMessageInChannel(event.getChannel(), "on-cooldown", Utility.formatTime(Cooldowns.getRemaining("wolf-stats-" + event.getAuthor().getStringID(), 10000, event.getAuthor())));
					}
				} else {
					Message.sendMessageInChannel(event.getChannel(), "bot-user");
				}
			} else {
				switch (args[0]) {
					case "feed":
						if (args.length == 2) {
							WolfCosmetics.foods food = null;
							try {
								food = WolfCosmetics.foods.valueOf(args[1].toLowerCase());
							} catch (Exception e) {
								e.printStackTrace();
							}
							if (food != null) {
								if (!Cooldowns.onCooldown("wolf-feed-" + event.getAuthor().getStringID(), food.getCooldown(), event.getAuthor())) {
									Cooldowns.putOnCooldown("wolf-feed-" + event.getAuthor().getStringID(), event.getAuthor());
									if (DataBase.queryUser(event.getAuthor()).getNotifications()) {
										new HoroTask(event.getAuthor().getStringID() + "-note") {
											@Override
											public void run() {
												Message.sendPM(event.getAuthor(), "wolf-ready");
											}
										}.delay(7200000);
									}
									WolfTemplate template = DataBase.wolfQuery(event.getAuthor());
									if (template == null) {
										DataBase.insertWolf(event.getAuthor());
										template = DataBase.wolfQuery(event.getAuthor());
									}
									int hunger = template.getHunger();
									int maxHunger = template.getMaxHunger();
									int foodValue = food.getValue();
									hunger += foodValue;
									DataBase.updateWolf(event.getAuthor(), "hunger", hunger);
									DataBase.updateWolf(event.getAuthor(), "fedTimes", template.getFedTimes() + 1);

									StringBuilder message = new StringBuilder(String.format(Localisation.getMessage(event.getGuild().getStringID(), "feed-wolf") + "\n", food.getName()));
									if (hunger >= maxHunger) {
										int nextHunger = 1 + template.getLevel();
										DataBase.updateWolf(event.getAuthor(), "hunger", 0);
										DataBase.updateWolf(event.getAuthor(), "maxHunger", nextHunger);
										DataBase.updateWolf(event.getAuthor(), "level", template.getLevel() + 1);
										message.append(String.format("**LEVEL UP!** Your wolf is now level **%s**!\n", template.getLevel() + 1));
									}

									DataBase.updateUser(event.getAuthor(), "foxCoins", (DataBase.queryUser(event.getAuthor()).getFoxCoins() + 100));
									message.append("**+100** Coins for feeding your wolf!\n");

									Message.sendFile(
											event.getChannel(),
											WolfProfileBuilder.generateEmbed(event.getAuthor()),
											message.toString(),
											"wolf.png",
											new ByteArrayInputStream(
													WolfProfileBuilder.generateProfile(
															event.getAuthor())));
								} else {
									Message.sendMessageInChannel(event.getChannel(), "wolf-full", Utility.formatTime(Cooldowns.getRemaining("wolf-feed-" + event.getAuthor().getStringID(), 7200000, event.getAuthor())));
								}
							} else {
								Message.sendMessageInChannel(event.getChannel(), "no-food");
							}
						} else {
							Message.sendMessageInChannel(event.getChannel(), "feed-help");
						}
						break;
					case "rename": {
						String temp = Arrays.stream(args).skip(1).collect(Collectors.joining(" "));
						if (temp.length() <= 18) {
							DataBase.updateWolf(event.getAuthor(), "name", temp);
							Message.sendMessageInChannel(event.getChannel(), "name-success", temp);
						} else {
							Message.sendMessageInChannel(event.getChannel(), "name-too-long");
						}
						break;
					}
					case "equip": {
						String temp = Arrays.stream(args).skip(1).collect(Collectors.joining(" "));
						String item = DataBase.queryItem(event.getAuthor(), WordUtils.capitalizeFully(temp));
						if (item != null) {
							DataBase.updateWolf(event.getAuthor(), Utility.getType(item), item);
							Message.sendMessageInChannel(event.getChannel(), "background-success", WordUtils.capitalizeFully(temp));
						} else {
							Message.sendMessageInChannel(event.getChannel(), "no-item");
						}
						break;
					}
					case "strip": {
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
						break;
					}
					case "inventory": {
						ArrayList<String> items = (ArrayList<String>) DataBase.queryItems(event.getAuthor());
						Message.sendRawMessageInChannel(
								event.getChannel(),
								"Here's your inventory!\n" +
										"```\n" +
										Utility.listAsString(items) + "\n" +
										"```");
						break;
					}
					case "capsule": {
						if (DataBase.queryUser(event.getAuthor()).getFoxCoins() >= 100) {
							Item drop = Utility.dropItem();
							if (drop != null) {
								boolean ownsItem;
								ownsItem = DataBase.queryItem(event.getAuthor(), drop.getFile()) != null;
								DataBase.updateUser(event.getAuthor(), "foxCoins", (DataBase.queryUser(event.getAuthor()).getFoxCoins() - 100));
								if(!ownsItem) DataBase.insertItem(event.getAuthor(), WordUtils.capitalizeFully(drop.getFile()));
								InputStream stream = getClass().getResourceAsStream(drop.getFile());
								if (stream == null) return;
								try {
									File temp = File.createTempFile(String.valueOf(stream.hashCode()), ".png");
									temp.deleteOnExit();

									try (FileOutputStream out = new FileOutputStream(temp)) {
										byte[] buffer = new byte[1024];
										int bytesRead;
										while((bytesRead = stream.read(buffer)) != -1) {
											out.write(buffer, 0, bytesRead);
										}
									}
									if (!ownsItem) {
										Message.sendFile(
												event.getChannel(),
												String.format(Localisation.getMessage(event.getGuild().getStringID(), "capsule-opened"), drop),
												temp);
									} else {
										Message.sendFile(
												event.getChannel(),
												String.format(Localisation.getMessage(event.getGuild().getStringID(), "capsule-opened-got"), drop),
												temp);
									}
								} catch (IOException e) {
									e.printStackTrace();
								}
							} else {
								Message.sendMessageInChannel(event.getChannel(), "got-everything");
							}
						} else {
							Message.sendMessageInChannel(event.getChannel(), "insufficient-funds");
						}
						//Message.sendRawMessageInChannel(event.getChannel(), "This command is currently broken and under construction");
						break;
					}
					case "help": {
						Message.sendMessageInChannel(event.getChannel(), help());
						break;
					}
					default: {
						Message.sendMessageInChannel(event.getChannel(), "no-sub-command");
						break;
					}
				}
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