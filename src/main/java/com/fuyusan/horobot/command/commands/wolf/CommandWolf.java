package com.fuyusan.horobot.command.commands.wolf;

import com.fuyusan.horobot.command.proccessing.Command;
import com.fuyusan.horobot.database.DataBase;
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

						String message = String.format("You decide to feed your wolf a %s\n", args[1]);
						if (hunger >= maxHunger) {
							for(int i = 0; i <= Math.floor(maxHunger / hunger); i++) {
								int nextHunger = (7 + template.getLevel());
								DataBase.updateWolf(event.getAuthor(), "hunger", maxHunger - hunger);
								DataBase.updateWolf(event.getAuthor(), "maxHunger", nextHunger);
								DataBase.updateWolf(event.getAuthor(), "level", template.getLevel() + 1);
								message += String.format("**LEVEL UP** Your wolf leveled up and is now level %s\n", template.getLevel() + i + 1);
							}
						}
						Message.sendFile(event.getChannel(), "",
								event.getAuthor().getID() + "-wolf.png",
								new ByteArrayInputStream(WolfProfileBuilder.generateImage(event.getAuthor())));
					}
				}
			} else if (args[0].equals("rename")) {
				DataBase.updateWolf(event.getAuthor(), "name", Arrays.stream(args).skip(1).collect(Collectors.joining(" ")));
				Message.reply("success", event.getMessage());
			} else if (args[0].equals("background")) {
				String background = Arrays.stream(args).skip(1).collect(Collectors.joining(" "));
				if(!WolfCosmetics.backgrounds.containsKey(background)) {
					Message.reply("wrong-background", event.getMessage());
					return;
				}
				DataBase.updateWolf(event.getAuthor(), "background", background);
				Message.reply("success", event.getMessage());
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

	/*
	 * I sexually Identify as an Attack Helicopter.
	 * Ever since I was a boy I dreamed of soaring over the oilfields dropping hot sticky loads on disgusting foreigners.
	 * People say to me that a person being a helicopter is Impossible and I’m fucking retarded but I don’t care, I’m beautiful.
	 * I’m having a plastic surgeon install rotary blades, 30 mm cannons and AMG-114 Hellfire missiles on my body.
	 * From now on I want you guys to call me “Apache” and respect my right to kill from above and kill needlessly.
	 * If you can’t accept me you’re a heliphobe and need to check your vehicle privilege.
	 * Thank you for being so understanding.
	 */
}