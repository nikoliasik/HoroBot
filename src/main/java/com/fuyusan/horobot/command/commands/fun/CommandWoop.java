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

package com.fuyusan.horobot.command.commands.fun;

import com.fuyusan.horobot.util.Message;
import com.fuyusan.horobot.util.Utility;
import com.fuyusan.horobot.command.proccessing.Command;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.util.EmbedBuilder;

import java.awt.*;
import java.util.Random;

public class CommandWoop implements Command {

	public boolean called(String[] args, MessageReceivedEvent event) {
		return true;
	}

	public void action(String[] args, String raw, MessageReceivedEvent event) {
		if(args.length == 0) {
			Random rand = new Random();
			String message = "Error";
			int result = rand.nextInt(4);
			switch (result) {
				case 1:
					message = "https://media.giphy.com/media/4SGFq4W8Zx36U/giphy.gif";
					break;
				case 2:
					message = "https://cdn.discordapp.com/attachments/290211327337562112/290538154551803904/jEcxTcM.gif";
					break;
				case 3:
					message = "https://cdn.discordapp.com/attachments/290211327337562112/290539177395879937/2CeU2VK.gif";
					break;
				case 4:
					message = "https://cdn.discordapp.com/attachments/290211327337562112/290540017988927498/25c.gif";
					break;
			}
			EmbedBuilder builder = new EmbedBuilder();
			builder.withImage(message);
			builder.withColor(Color.CYAN);

			event.getChannel().sendMessage("", builder.build(), false);
		} else {
			Message.reply("woop-help", event.getMessage());
		}
	}

	public String help() {
		return "woop-help";
	}

	public void executed(boolean success, MessageReceivedEvent event) {
		if(success)
			Utility.commandsExecuted++;
	}
}
