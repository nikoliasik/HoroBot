package com.winter.horobot.command;

import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

import java.util.function.Predicate;

public class Command {

	private final String name;
	private final Predicate<MessageReceivedEvent> check;
	private final Predicate<MessageReceivedEvent> call;

	/**
	 * Creates a new command class
	 *
	 * @param name  Name of the command or subcommand.
	 * @param check Whether to run the call or not.
	 * @param call  Run the call, false means show help message. Help is determined by <code>[name]-help</code>.
	 */
	public Command(String name, Predicate<MessageReceivedEvent> check, Predicate<MessageReceivedEvent> call) {
		this.name = name;
		this.check = check;
		this.call = call;
	}

	public void call(MessageReceivedEvent e) {
		if (check.test(e) && !call.test(e)) { // && prevents call from running if check is false
			// TODO show help
		}
	}

	public String getName() {
		return name;
	}

}
