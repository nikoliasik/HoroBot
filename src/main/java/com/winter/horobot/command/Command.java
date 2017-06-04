package com.winter.horobot.command;

import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

public class Command {

	private final String name;
	private final Predicate<MessageReceivedEvent> check;
	private final Predicate<MessageReceivedEvent> call;
	private final Set<String> aliases;

	/**
	 * Creates a new command class
	 *
	 * @param name  Name of the command or subcommand.
	 * @param check Whether to run the call or not.
	 * @param call  Run the call, false means show help message. Help is determined by <code>[name]-help</code>.
	 */
	public Command(String name, Predicate<MessageReceivedEvent> check, Predicate<MessageReceivedEvent> call) {
		this(name, check, call, new HashSet<>());
	}

	public Command(String name, Predicate<MessageReceivedEvent> check, Predicate<MessageReceivedEvent> call, Set<String> aliases) {
		this.name = name;
		this.check = check;
		this.call = call;
		this.aliases = aliases;
		this.aliases.add(name);
	}

	public void call(MessageReceivedEvent e) {
		if (check.test(e)) {
			if (!call.test(e)) {
				// TODO show help
			}
		} else {
			e.getMessage().addReaction("\uD83D\uDEAB");
		}
	}

	public String getName() {
		return name;
	}

	public Set<String> getAliases() {
		return aliases;
	}

}
