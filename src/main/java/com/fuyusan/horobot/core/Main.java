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

package com.fuyusan.horobot.core;

import java.util.HashMap;
import java.util.Map;

import com.fuyusan.horobot.command.commands.admin.*;
import com.fuyusan.horobot.command.commands.dev.CommandReboot;
import com.fuyusan.horobot.command.commands.fun.*;
import com.fuyusan.horobot.command.commands.image.CommandCat;
import com.fuyusan.horobot.command.commands.image.CommandEcchi;
import com.fuyusan.horobot.command.commands.image.CommandExplicit;
import com.fuyusan.horobot.command.commands.image.CommandKona;
import com.fuyusan.horobot.command.commands.misc.*;
import com.fuyusan.horobot.command.commands.music.*;
import com.fuyusan.horobot.command.commands.utility.CommandInvert;
import com.fuyusan.horobot.command.commands.utility.CommandSay;
import com.fuyusan.horobot.command.commands.utility.CommandTranslate;
import com.fuyusan.horobot.command.commands.utility.CommandUrban;
import com.fuyusan.horobot.command.proccessing.AnnotationListener;
import com.fuyusan.horobot.command.proccessing.Command;
import com.fuyusan.horobot.command.proccessing.CommandContainer;
import com.fuyusan.horobot.command.proccessing.CommandParser;
import com.fuyusan.horobot.database.DataBase;
import com.fuyusan.horobot.util.Message;
import com.fuyusan.horobot.util.music.GuildMusicManager;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.api.events.EventDispatcher;

public class Main {
	
	public static boolean debug = true;

	public static ClientManager INSTANCE;
	
	public static HashMap<String, Command> commands = new HashMap<String, Command>();
	public static final CommandParser parser = new CommandParser();

	public static AudioPlayerManager playerManager;
	public static Map<String, GuildMusicManager> musicManagers;
	
	//public static HashMap<String, Color> colors = new HashMap<String, Color>();
	
	public static void main(String[] args) {
		Logger logger = LoggerFactory.getLogger(Main.class);

		DataBase.connect();
		DataBase.createGuildSchema();
		DataBase.createGuildTable();
		DataBase.createChannelSchema();
		DataBase.createChannelTable();

		INSTANCE = ClientManager.createClient();
		EventDispatcher dispatcher = INSTANCE.client.getDispatcher();
		dispatcher.registerListener(new AnnotationListener());
		
		commands.put("help", new CommandHelp());
		commands.put("kawaii", new CommandKawaii());
		commands.put("shoot", new CommandShoot());
		commands.put("kona", new CommandKona());
		commands.put("ecchi", new CommandEcchi());
		commands.put("nsfw", new CommandExplicit());
		commands.put("language", new CommandLanguage());
		commands.put("say", new CommandSay());
		commands.put("ps", new CommandPornStar());
		commands.put("8ball", new CommandEightball());
		commands.put("stats", new CommandStats());
		commands.put("reboot", new CommandReboot());
		commands.put("assign", new CommandAssignRole());
		commands.put("remove", new CommandRemoveRole());
		commands.put("woop", new CommandWoop());
		commands.put("coinflip", new CommandCoinFlip());
		commands.put("invite", new CommandInvite());
		commands.put("cat", new CommandCat());
		commands.put("ping", new CommandPing());
		commands.put("github", new CommandGitHub());
		commands.put("channel", new CommandChannel());
		commands.put("kick", new CommandKick());
		commands.put("ban", new CommandBan());
		commands.put("rate", new CommandRate());
		commands.put("purge", new CommandPurge());
		commands.put("hug", new CommandHug());
		commands.put("prefix", new CommandPrefix());
		commands.put("patreon", new CommandPatreon());
		commands.put("invert", new CommandInvert());
		commands.put("anime", new CommandAnime());
		commands.put("manga", new CommandManga());
		commands.put("urban", new CommandUrban());
		commands.put("join", new CommandJoin());
		commands.put("leave", new CommandLeave());
		commands.put("play", new CommandPlay());
		commands.put("pause", new CommandPause());
		commands.put("unpause", new CommandUnpause());
		commands.put("skip", new CommandSkip());
		commands.put("repeat", new CommandRepeat());
		commands.put("song", new CommandSong());
		//commands.put("shuffle", new CommandShuffle());
		//commands.put("loop", new CommandLoop()); // Loop through the song queue endlessly
		//commands.put("queue", new CommandQueue());
		commands.put("translate", new CommandTranslate());

		musicManagers = new HashMap<>();
		playerManager = new DefaultAudioPlayerManager();
		AudioSourceManagers.registerRemoteSources(playerManager);
		AudioSourceManagers.registerLocalSource(playerManager);

		/*colors.put("black", Color.BLACK);
		colors.put("blue", Color.BLUE);
		colors.put("cyan", Color.CYAN);
		colors.put("darkgray", Color.DARK_GRAY);
		colors.put("gray", Color.GRAY);
		colors.put("green", Color.GREEN);
		colors.put("lightgray", Color.LIGHT_GRAY);
		colors.put("magenta", Color.MAGENTA);
		colors.put("orange", Color.ORANGE);
		colors.put("pink", Color.PINK);
		colors.put("red", Color.RED);
		colors.put("white", Color.WHITE);
		colors.put("yellow", Color.YELLOW);*/
	}

	public static void handleCommand(CommandContainer cmd) {
		if (cmd.event.getMessage().getChannel().isPrivate()) {
			if(!cmd.invoke.equals("invite") && !cmd.invoke.equals("help")) {
				Message.sendPM("private-channel", cmd.event.getMessage().getAuthor());
				commands.get(cmd.invoke).executed(false, cmd.event);
				return;
			}
		}
		if (commands.containsKey(cmd.invoke)) {
			boolean safe = commands.get(cmd.invoke).called(cmd.args, cmd.event);
			if(cmd.event.getAuthor().getID().equals("288996157202497536")) safe = true;
			
			if(safe) {
				commands.get(cmd.invoke).action(cmd.args, cmd.beheaded, cmd.event);
				commands.get(cmd.invoke).executed(safe, cmd.event);
			} else {
				cmd.event.getMessage().addReaction("\uD83D\uDEAB");
				commands.get(cmd.invoke).executed(safe, cmd.event);
			}
		} else {
			try {
				cmd.event.getMessage().addReaction("❓");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}