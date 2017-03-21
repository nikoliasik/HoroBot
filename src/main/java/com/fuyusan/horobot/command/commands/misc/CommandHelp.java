/*
	HoroBot - An open-source Discord bot
	Copyright (C) 2017	WiNteR

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as
    published by the Free Software Foundation, either version 3 of the
    License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.fuyusan.horobot.command.commands.misc;

import com.fuyusan.horobot.core.*;
import com.fuyusan.horobot.command.proccessing.Command;
import com.fuyusan.horobot.util.HTMLHandler;
import com.fuyusan.horobot.util.HTMLHandler.KONA_RATING;
import com.fuyusan.horobot.util.Localisation;
import com.fuyusan.horobot.util.Utility;
import com.fuyusan.horobot.util.Message;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class CommandHelp implements Command {

	public boolean called(String[] args, MessageReceivedEvent event) {
		return true;
	}

	public void action(String[] args, String raw, MessageReceivedEvent event) {
		try {
			if (args.length == 0) {
				StringBuilder builder = new StringBuilder();
				builder.append("```\n");
				builder.append(Localisation.getPMMessage("help-list") + "\n\n");
				for (String key : Main.commands.keySet()) {
					builder.append(".horo" + key + "\n" + "    * " + Localisation.getPMMessage(key + "-help") + "\n\n");
				}
				builder.append("```\n");

				String commands = builder.toString();

				String tags[] = { "holo" };
				Message.sendPMPic(HTMLHandler.requestKona(tags, KONA_RATING.SAFE), event);
				event.getAuthor().getOrCreatePMChannel().sendMessage(commands);
				if(!event.getMessage().getChannel().isPrivate()) event.getMessage().delete();
			} else if(args.length == 1) {
				if(!event.getMessage().getChannel().isPrivate()) {
					String guildID = event.getMessage().getGuild().getID();
					if (Main.commands.containsKey(args[0])) {
						Message.replyRaw(Localisation.getMessage(guildID, Main.commands.get(args[0]).help()), event.getMessage());
					} else {
						Message.replyRaw("`" + args[0] + "` " + Localisation.getMessage(guildID, "no-command") + "\n", event.getMessage());
					}
				} else {
					Message.reply("private-channel", event.getMessage());
				}
			} else {
				Message.reply(help(), event.getMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String help() {
		return "help-help";
	}

	public void executed(boolean success, MessageReceivedEvent event) {
		if(success)
			Utility.commandsExecuted++;
	}
}