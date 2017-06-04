package com.winter.horobot.util;

import com.winter.horobot.Main;
import com.winter.horobot.data.Localisation;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.EmbedBuilder;
import sx.blah.discord.util.MessageBuilder;

public class AdminUtil {

	public static boolean kick(MessageReceivedEvent event) {
		IUser user = ParsingUtil.getUser(MessageUtil.argsArray(event.getMessage())[1]);
		event.getGuild().kickUser(user);
		MessageBuilder mb = new MessageBuilder(Main.getClient());
		mb.withContent(Localisation.of("kicked", event.getGuild()) + " " + user.getName() + "#" + user.getDiscriminator());
		EmbedBuilder eb = new EmbedBuilder();
		eb.withColor(ColorUtil.withinTwoHues(1.0f / 15.0f, 1.0f / 6.0f));
		eb.appendField("Kick stats:", "Kicked on " + event.getMessage().getTimestamp().toString() + " by " + event.getAuthor().getName() + "#" + event.getAuthor().getDiscriminator(), false);
		mb.withEmbed(eb.build());
		mb.withChannel(event.getChannel());
		mb.send();
		return true;
	}

	public static boolean ban(MessageReceivedEvent event) {
		IUser user = ParsingUtil.getUser(MessageUtil.argsArray(event.getMessage())[1]);
		event.getGuild().banUser(user);
		MessageBuilder mb = new MessageBuilder(Main.getClient());
		mb.withContent(Localisation.of("banned", event.getGuild()) + " " + user.getName() + "#" + user.getDiscriminator());
		EmbedBuilder eb = new EmbedBuilder();
		eb.withColor(ColorUtil.withinTwoHues(0.0f, 1.5f / 15.0f));
		eb.appendField("Ban stats:", "Banned on " + event.getMessage().getTimestamp().toString() + " by " + event.getAuthor().getName() + "#" + event.getAuthor().getDiscriminator(), false);
		mb.withEmbed(eb.build());
		mb.withChannel(event.getChannel());
		mb.send();
		return true;
	}
}
