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

package com.fuyusan.horobot.util;

import com.fuyusan.horobot.core.Config;
import com.fuyusan.horobot.core.Main;
import com.fuyusan.horobot.database.DataBase;
import sx.blah.discord.Discord4J;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.Permissions;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Utility {

	public static String Discord4JVersion = Discord4J.VERSION;
	public static long commandsExecuted = 0;
	public static long totalCommands = 0;
	public static String runStart;
	public static String runTime;
	public static long reconnectedTimes = 0;
	public static String memory;
	public static long messagesReceived = 0;
	public static long messagesSent = 0;

	public static boolean checkUserPermission(IGuild guild, IUser user, Permissions permission) {
		return (user.getPermissionsForGuild(guild).contains(permission));
	}

	public static String getChannelMod(String channelID) {
		return DataBase.channelQuery(channelID);
	}

	public static void storeChannelMod(String channelID, String mod) {
		DataBase.insertChannel(channelID, mod);
		DataBase.updateChannel(channelID, mod);
	}

	public static void postStats(int shard, int shardCount, int serverCount) {
		HTMLHandler.postStats(Config.discordBotsToken, shard, shardCount, serverCount);
	}

	public static String getStats(int shard, int shardCount, int serverCount) {
		updateStats();
		if(!Main.debug) postStats(shard, shardCount, serverCount);

		StringBuilder builder = new StringBuilder();
		builder.append("```\n");
		builder.append("Discord4J version: " + Discord4JVersion + "\n");
		builder.append("Shards: " + shardCount + "\n");
		builder.append("Guilds: " + serverCount + "\n");
		builder.append("Commands executed: " + commandsExecuted + "\n");
		builder.append("Total commands available: " + totalCommands + "\n");
		builder.append("App start date: " + runStart + "\n");
		builder.append("App up time: " + runTime + "\n");
		builder.append("Reconnected times: " + reconnectedTimes + "\n");
		builder.append("Memory usage: " + memory + "\n");
		builder.append("Messages received: " + messagesReceived + "\n");
		builder.append("Messages sent: " + messagesSent + "\n");
		builder.append("```");

		return builder.toString();
	}

	public static void updateStats() {
		totalCommands = Main.commands.size();
		runStart = getStartTime();
		runTime = getUpTime();
		memory = getMemoryUsage();
	}

	public static String getUpTime() {
		RuntimeMXBean rb = ManagementFactory.getRuntimeMXBean();
		long uptime = rb.getUptime();

		int days = (int) TimeUnit.MILLISECONDS.toDays(uptime);
		int hours = (int) TimeUnit.MILLISECONDS.toHours(uptime) - (days * 24);
		int minutes = (int) (TimeUnit.MILLISECONDS.toMinutes(uptime) - (TimeUnit.MILLISECONDS.toHours(uptime) * 60));
		int seconds = (int) (TimeUnit.MILLISECONDS.toSeconds(uptime) - (TimeUnit.MILLISECONDS.toMinutes(uptime) * 60));
		String string = String.format("%d days, %d hours, %d minutes, %d seconds",
				days,
				hours,
				minutes,
				seconds
		);
		return string;
	}

	public static String getStartTime() {
		RuntimeMXBean rb = ManagementFactory.getRuntimeMXBean();
		long startTime = rb.getStartTime();

		Date date = new Date(startTime);
		SimpleDateFormat format = new SimpleDateFormat();
		String dateString = format.format(date);
		return dateString;
	}

	public static String getMemoryUsage() {
		Runtime runtime = Runtime.getRuntime();
		NumberFormat format = NumberFormat.getInstance();
		StringBuilder builder = new StringBuilder();

		long max = runtime.maxMemory();
		long allocated = runtime.totalMemory();
		builder.append(format.format((int) allocated / 1048576) + "Mb / ");
		builder.append(format.format((int) max / 1048576) + "Mb");
		String memory = builder.toString();
		return memory;
	}
}