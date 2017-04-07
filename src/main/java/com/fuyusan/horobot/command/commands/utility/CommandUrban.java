package com.fuyusan.horobot.command.commands.utility;

import com.fuyusan.horobot.command.proccessing.Command;
import com.fuyusan.horobot.util.HTMLHandler;
import com.fuyusan.horobot.util.Message;
import com.fuyusan.horobot.util.Utility;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.Permissions;

public class CommandUrban implements Command {

	public boolean called(String[] args, MessageReceivedEvent event) {
		return true;
	}

	public void action(String[] args, String raw, MessageReceivedEvent event) {
		if(args.length == 1) {
			if (Utility.checkUserPermission(event.getGuild(), event.getClient().getOurUser(), Permissions.EMBED_LINKS)) {
				Message.sendEmbed(event.getChannel(), "", HTMLHandler.requestUrban(args[0], event.getAuthor().getDisplayName(event.getGuild()) + "#" + event.getAuthor().getDiscriminator(), event.getAuthor().getAvatarURL()), false);
			} else {
				Message.sendMessageInChannel(event.getChannel(), "missing-embed-perm");
			}
		} else {
			Message.reply(help(), event.getMessage());
		}
	}

	public String help() {
		return "urban-help";
	}

	public void executed(boolean success, MessageReceivedEvent event) {
		if(success)
			Utility.commandsExecuted++;
	}
}