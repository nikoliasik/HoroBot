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

package com.fuyusan.horobot.command.proccessing;

import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class CommandContainer {
	
	public final String raw;
	public final String beheaded;
	public final String[] splitBeheaded;
	public final String invoke;
	public final String[] args;
	public final MessageReceivedEvent event;
	
	public CommandContainer(String raw, String beheaded, String[] splitBeheaded, String invoke, String[] args, MessageReceivedEvent event) {
		this.raw = raw;
		this.beheaded = beheaded;
		this.splitBeheaded = splitBeheaded;
		this.invoke = invoke;
		this.args = args;
		this.event = event;
	}
}