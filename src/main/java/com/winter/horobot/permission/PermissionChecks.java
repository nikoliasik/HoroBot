package com.winter.horobot.permission;

import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.Permissions;

import java.util.function.Predicate;

public class PermissionChecks {

	public static Predicate<MessageReceivedEvent> hasPermision(Permissions p) {
		return e -> e.getChannel().getModifiedPermissions(e.getAuthor()).contains(p) && e.getChannel().getModifiedPermissions(e.getClient().getOurUser()).contains(p);
	}

}
