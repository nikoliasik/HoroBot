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

import com.fuyusan.horobot.util.Localisation;
import com.fuyusan.horobot.util.Message;
import com.fuyusan.horobot.util.Utility;
import com.fuyusan.horobot.command.proccessing.Command;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

import java.util.Random;

public class CommandKawaii implements Command {

	public boolean called(String[] args, MessageReceivedEvent event) {
		return true;
	}

	public void action(String[] args, String raw, MessageReceivedEvent event) {
		try {
			if (args.length == 1) {
				String guildID = event.getMessage().getGuild().getID();
				Random rand = new Random();
				int factor = rand.nextInt(100);
				Message.replyRaw("**" + args[0] + "** " + Localisation.getMessage(guildID, "kawaii-is") + " " + factor + Localisation.getMessage(guildID, "kawaii-percent"), event.getMessage());
			} else {
				Message.reply(help(), event.getMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String help() {
		return "kawaii-help";
	}

	public void executed(boolean success, MessageReceivedEvent event) {
		if(success)
			Utility.commandsExecuted++;
	}
}