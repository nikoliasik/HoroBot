package com.winter.horobot.util;

import com.winter.horobot.Main;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.reaction.ReactionAddEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IReaction;
import sx.blah.discord.handle.obj.IUser;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class DialogueUtil {

	/**
	 * A choice dialogue with reactions
	 * @param reactions An array containing all reaction choices in a string form
	 * @param message The message to listen for
	 * @param user The user to listen for
	 * @param wait The amount of time to wait before time-out
	 * @param timeUnit The time unit eg. seconds
	 * @return IReaction The reaction the user reacted with or null
	 */
	public static IReaction choiceDialogue(String[] reactions, IMessage message, IUser user, long wait, TimeUnit timeUnit) {
		IReaction reaction = null;
		try {
			reaction = Main.getClient().getDispatcher().waitFor((ReactionAddEvent e) -> e.getMessage().equals(message) && e.getUser().equals(user) && Arrays.stream(reactions).anyMatch(r -> r.equals(e.getReaction().toString())), wait, timeUnit).getReaction();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (NullPointerException ignored) { }
		return reaction;
	}

	/**
	 * A choice dialogue with normal strings
	 * @param choices An array containing all choices
	 * @param channel The channel to listen in
	 * @param user The user to listen for
	 * @param wait The amount of time to wait before time-out
	 * @param timeUnit The time unit eg. seconds
	 * @return One of the given choices or null
	 */
	public static IMessage choiceDialogue(String[] choices, IChannel channel, IUser user, long wait, TimeUnit timeUnit) {
		IMessage message = null;
		try {
			message = Main.getClient().getDispatcher().waitFor((MessageReceivedEvent e) -> e.getChannel().equals(channel) && e.getAuthor().equals(user) && Arrays.stream(choices).anyMatch(m -> m.equalsIgnoreCase(e.getMessage().getContent())), wait, timeUnit).getMessage();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (NullPointerException ignored) { }
		return message;
	}
}