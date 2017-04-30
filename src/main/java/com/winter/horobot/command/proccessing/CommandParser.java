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

package com.winter.horobot.command.proccessing;

import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

import java.util.ArrayList;

public class CommandParser {
	public CommandContainer parse(String rw, String prefix, MessageReceivedEvent event) {
		ArrayList<String> split = new ArrayList<String>();
		String raw = rw;
		String beheaded = rw.substring(prefix.length(), rw.length());
		String[] splitBeheaded = beheaded.split(" ");
		
		for(String s : splitBeheaded)
			split.add(s);
		
		String invoke = split.get(0);
		beheaded = raw.substring(prefix.length() + invoke.length(), raw.length());
		if(beheaded.length() > 0) beheaded = beheaded.substring(1, beheaded.length());
		String[] args = new String[split.size() - 1];
		split.subList(1, split.size()).toArray(args);
		
		return new CommandContainer(raw, beheaded, splitBeheaded, invoke, args, event);
	}
}