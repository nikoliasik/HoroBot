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

package com.fuyusan.horobot.command.commands.dev;

import com.fuyusan.horobot.core.Main;
import com.fuyusan.horobot.util.Message;
import com.fuyusan.horobot.command.proccessing.Command;
import com.fuyusan.horobot.util.music.GuildMusicManager;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IVoiceState;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class CommandReboot implements Command {

	public boolean called(String[] args, MessageReceivedEvent event) {
		return false;
	}

	public void action(String[] args, String raw, MessageReceivedEvent event) {
		if(event.getMessage().getAuthor().getID().equals("288996157202497536")) {
			try {
				//Message.reply("shut-down", event.getMessage());

				Iterator iterator = Main.INSTANCE.client.getOurUser().getVoiceStates().entrySet().iterator();
				while(iterator.hasNext()) {
					Map.Entry<String, IVoiceState> pair = (Map.Entry) iterator.next();
					pair.getValue().getChannel().leave();
				}

				/*String javaBin = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
				File currentJar = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI());

				if(!currentJar.getName().endsWith(".jar"))
					return;

				ArrayList<String> command = new ArrayList<String>();
				command.add(javaBin);
				command.add("-jar");
				command.add(currentJar.getPath());
				command.add("-Xmx1024m");

				ProcessBuilder builder = new ProcessBuilder(command);
				builder.start();
				System.exit(0);*/
			} catch(Exception e) {
				e.printStackTrace();
			}
		} else {
			Message.reply("no-dev", event.getMessage());
		}
	}

	public String help() {
		return "reboot-help";
	}

	public void executed(boolean success, MessageReceivedEvent event) {
		return;
	}
}