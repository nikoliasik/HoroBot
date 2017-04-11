package com.winter.horobot.command.commands.fun;

import com.winter.horobot.command.proccessing.Command;
import com.winter.horobot.util.HTMLHandler;
import com.winter.horobot.util.Message;
import com.winter.horobot.util.Utility;
import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.Permissions;

public class CommandManga implements Command {

	public boolean called(String[] args, MessageReceivedEvent event) {
		return true;
	}

	public void action(String[] args, String raw, MessageReceivedEvent event) {
		if(args.length > 0) {
			if (Utility.checkUserPermission(event.getGuild(), event.getClient().getOurUser(), Permissions.EMBED_LINKS)) {
				EmbedObject manga = HTMLHandler.requestAnime(args, event.getAuthor().getDisplayName(event.getGuild()) + "#" + event.getAuthor().getDiscriminator(), event.getAuthor().getAvatarURL(), 1);
				if (manga != null) {
					Message.sendEmbed(event.getChannel(),"", manga, false);
				} else {
					Message.reply("html-no-results", event.getMessage());
				}
			} else {
				Message.sendMessageInChannel(event.getChannel(), "missing-embed-perm");
			}
		} else {
			Message.reply(help(), event.getMessage());
		}
	}

	public String help() {
		return "manga-help";
	}

	public void executed(boolean success, MessageReceivedEvent event) {
		if(success)
			Utility.commandsExecuted++;
	}
}