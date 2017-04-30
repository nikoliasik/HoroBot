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

package com.winter.horobot.util;

import at.dhyan.open_imaging.GifDecoder;
import com.winter.horobot.core.Config;
import com.winter.horobot.core.Main;
import com.winter.horobot.database.DataBase;
import sx.blah.discord.Discord4J;
import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.Permissions;
import sx.blah.discord.util.EmbedBuilder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.URL;
import java.net.URLConnection;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Utility {

	private static String[] defaults = {
			"6debd47ed13483642cf09e832ed0bc1b",
			"322c936a8c8be1b803cd94861bdfa868",
			"dd4dbc0016779df1378e7812eabaa04d",
			"0e291f67c9274a1abdddeb3fd919cbaa",
			"1cbd08c76f8af6dddce02c5138971129"
	};

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

	public static EmbedObject getStats(int shard, int shardCount, int serverCount) {
		updateStats();
		if (!Config.debug) postStats(shard, shardCount, serverCount);

		EmbedBuilder builder = new EmbedBuilder();
		builder.withColor(Color.CYAN);
		builder.withTimestamp(LocalDateTime.now());
		builder.withAuthorName("HoroBot");
		builder.withAuthorIcon(Utility.getAvatar(Main.INSTANCE.client.getOurUser()));
		builder.withThumbnail(Utility.getAvatar(Main.INSTANCE.client.getOurUser()));

		builder.appendField("Discord4J Version", Discord4JVersion, false);
		builder.appendField("Shards", String.valueOf(shardCount), true);
		builder.appendField("Guilds", String.valueOf(serverCount), true);
		builder.appendField("Commands Executed", String.valueOf(commandsExecuted), true);
		builder.appendField("Commands Available", String.valueOf(totalCommands), true);
		builder.appendField("App Start", runStart, true);
		builder.appendField("App Up", runTime, true);
		builder.appendField("Reconnected Times", String.valueOf(reconnectedTimes), true);
		builder.appendField("Memory Usage", memory, true);
		builder.appendField("Messages Received", String.valueOf(messagesReceived), true);
		builder.appendField("Messages Sent", String.valueOf(messagesSent), true);

		return builder.build();
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
		return String.format("%d days, %d hours, %d minutes, %d seconds",
				days,
				hours,
				minutes,
				seconds
		);
	}

	public static String formatTime(long milliseconds) {
		StringBuilder builder = new StringBuilder();
		long convert = (milliseconds / 1000);
		int days = (int) (convert / (3600 * 24));
		int hours = (int) (convert / 3600 % 24);
		int minutes = (int) (convert / 60 % 60);
		int seconds = (int) (convert % 60);

		if (days > 0) {
			builder.append(days);
			builder.append(":");
		}

		if (hours > 0) {
			if (hours < 10)
				builder.append("0");
			builder.append(hours);
			builder.append(":");
		} else if (days > 0) {
			builder.append("00:");
		}

		if (minutes > 0) {
			if (minutes < 10 && (hours > 0 || days > 0))
				builder.append("0");
			builder.append(minutes);
		} else {
			builder.append("0");
			if (hours > 0 || days > 0)
				builder.append("0");
		}

		builder.append(":");
		if (seconds < 10)
			builder.append("0");
		builder.append(seconds);

		if (convert < 1f && convert > 0f) {
			return "0:0" + String.format("%.3f", Math.max(convert, 0.001f));
		}

		return builder.toString();
	}

	public static String getStartTime() {
		RuntimeMXBean rb = ManagementFactory.getRuntimeMXBean();
		long startTime = rb.getStartTime();

		Date date = new Date(startTime);
		SimpleDateFormat format = new SimpleDateFormat();
		return format.format(date);
	}

	public static String getMemoryUsage() {
		Runtime runtime = Runtime.getRuntime();
		NumberFormat format = NumberFormat.getInstance();
		StringBuilder builder = new StringBuilder();

		int mb = 1024 * 1024;
		builder.append(format.format((int) (runtime.totalMemory() - runtime.freeMemory()) / mb)).append("Mb / ");
		builder.append(format.format((int) (runtime.totalMemory()) / mb)).append("Mb");
		return builder.toString();
	}

	public static String getAvatar(IUser user) {
		return user.getAvatar() != null ? user.getAvatarURL() : getDefaultAvatar(user);
	}

	public static String getDefaultAvatar(IUser user) {
		int discrim = Integer.parseInt(user.getDiscriminator());
		discrim %= defaults.length;
		return "https://discordapp.com/assets/" + defaults[discrim] + ".png";
	}

	public static BufferedImage imageFor(String url) throws IOException {
		if (url == null)
			return null;
		URL urll = new URL(url);
		URLConnection connection = urll.openConnection();
		connection.addRequestProperty("User-Agent", "Mozilla/5.0 Apache the Attack Helicopter");
		connection.connect();
		return ImageIO.read(connection.getInputStream());
	}

	public static String listAsString(List<String> list) {
		StringBuilder builder = new StringBuilder();
		for(String string : list) {
			builder.append(string);
			builder.append(", ");
		}
		return builder.length() > 0 ? builder.substring(0, builder.length() - 2) : "";
	}

	public static String permissionsAsString(EnumSet<Permissions> permissions) {
		StringBuilder builder = new StringBuilder();
		for(Permissions perm : permissions) {
			builder.append(perm.name());
			builder.append(", ");
		}
		return builder.length() > 0 ? builder.substring(0, builder.length() - 2) : "";
	}

	public static void saveGifToStream(List<BufferedImage> images, OutputStream stream, int timeBetweenFrames, boolean loop) throws IOException {
		GifSequenceWriter writer = new GifSequenceWriter(ImageIO.createImageOutputStream(stream), images.get(0).getType(), timeBetweenFrames, loop);
		for(BufferedImage image : images) {
			writer.writeToSequence(image);
		}
		writer.close();
	}

	public static List<BufferedImage> getGifFramesFromStream(InputStream stream) throws IOException {
		GifDecoder.GifImage image = GifDecoder.read(stream);
		List<BufferedImage> frames = new ArrayList<>();
		for(int i = 0; i < image.getFrameCount(); i++) {
			frames.add(image.getFrame(i));
		}
		return frames;
	}
}