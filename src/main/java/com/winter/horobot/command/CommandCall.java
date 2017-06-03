package com.winter.horobot.command;

import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public interface CommandCall {

	boolean call(MessageReceivedEvent e);

}
