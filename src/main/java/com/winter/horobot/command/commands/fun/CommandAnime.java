package com.winter.horobot.command.commands.fun;

import com.winter.horobot.command.proccessing.Command;
import com.winter.horobot.util.HTMLHandler;
import com.winter.horobot.util.Message;
import com.winter.horobot.util.Utility;
import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.Permissions;


public class CommandAnime implements Command {

	public boolean called(String[] args, MessageReceivedEvent event) {
		return true;
	}

	public void action(String[] args, String raw, MessageReceivedEvent event) {
		if(args.length > 0) {
			if (Utility.checkUserPermission(event.getGuild(), event.getClient().getOurUser(), Permissions.EMBED_LINKS)) {
				event.getChannel().setTypingStatus(true);
				EmbedObject anime = null;
				try {
					anime = HTMLHandler.requestAnime(args, event.getAuthor().getDisplayName(event.getGuild()) + "#" + event.getAuthor().getDiscriminator(), event.getAuthor().getAvatarURL(), 0);
				} catch (Exception e) {
					Message.sendMessageInChannel(event.getChannel(), "html-error");
					return;
				}
				if (anime != null) {
					Message.sendEmbed(event.getChannel(), "", anime, false);
				} else {
					Message.reply("html-no-results", event.getMessage());
				}
				event.getChannel().setTypingStatus(false);
			} else {
				Message.sendMessageInChannel(event.getChannel(), "missing-embed-perm");
			}
		} else {
			Message.reply(help(), event.getMessage());
		}
	}

	public String help() {
		return "anime-help";
	}

	public void executed(boolean success, MessageReceivedEvent event) {
		if(success)
			Utility.commandsExecuted++;
	}
}