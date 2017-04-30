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

package com.winter.horobot.command.commands.utility;

import com.winter.horobot.command.proccessing.Command;
import com.winter.horobot.command.proccessing.CommandType;
import com.winter.horobot.command.proccessing.CommandType;
import com.winter.horobot.core.Main;
import com.winter.horobot.util.Localisation;
import com.winter.horobot.util.Message;
import com.winter.horobot.util.Utility;
import org.apache.commons.lang3.text.WordUtils;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.util.EmbedBuilder;

import java.awt.*;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class CommandHelp implements Command {

	public boolean called(String[] args, MessageReceivedEvent event) {
		return true;
	}

	public void action(String[] args, String raw, MessageReceivedEvent event) {
		try {
			if (args.length == 0) {
//				Message.sendRawPM(
//								"```\n" +
//								"Here's a list of my commands:\n" +
//								"\n" +
//								".horops\n" +
//								"    * Usage: .horops - This is your pornstar name\n" +
//								"\n" +
//								".horoping\n" +
//								"    * Usage: .horoping - Returns pong and the ping in ms\n" +
//								"\n" +
//								".horochannel\n" +
//								"    * Usage: .horochannel [channel name/id] [modifier] - Adds a modifier to a channel. Valid modifiers are: ecchi, nsfw\n" +
//								"\n" +
//								".horolanguage\n" +
//								"    * Usage: .horolanguage [en/nl] - Changes the language HoroBot speaks\n" +
//								"\n" +
//								".horopurge\n" +
//								"    * Usage: .horopurge [count] - Deletes x messages from the channel\n" +
//								"\n" +
//								".hororemove\n" +
//								"    * Usage: .hororemove [user] [role] - Remove someones role\n" +
//								"\n" +
//								".horowoop\n" +
//								"    * Usage: .horowoop - WOOP~! WOOP~!\n" +
//								"\n" +
//								".horoban\n" +
//								"    * Usage: .horoban [user] [reason] - Bans a user with an optional reason\n" +
//								"\n" +
//								".horostats\n" +
//								"    * Usage: .horostats - See what my current stats are\n" +
//								"\n" +
//								".hororate\n" +
//								"    * Usage: .hororate - Horo will rate whatever you ask her to!\n" +
//								"\n" +
//								".horocat\n" +
//								"    * Usage: .horocat - A random image of a cute kitty~! Nyaa~!\n" +
//								"\n" +
//								".horoshoot\n" +
//								"    * Usage: .horoshoot [person] - Shoot at someone and see how you fare\n" +
//								"\n" +
//								".hororeboot\n" +
//								"    * This is a dev only command, it is not for the public\n" +
//								"\n" +
//								".horogithub\n" +
//								"    * Usage: .horogithub - Returns a link to the GitHub repository\n" +
//								"\n" +
//								".horokawaii\n" +
//								"    * Usage: .horokawaii [person] - See how kawaii someone is\n" +
//								"\n" +
//								".horokona\n" +
//								"    * Usage: .horokona [tags] - Search for a cute picture with given tags\n" +
//								"\n" +
//								".horonsfw\n" +
//								"    * Usage: .horonsfw [tags] - Search for an NSFW(18+) picture with given tags\n" +
//								"\n" +
//								".horosay\n" +
//								"    * Usage: .horosay [sentence] - Make me say what you want\n" +
//								"\n" +
//								".horocoinflip\n" +
//								"    * Usage: .horocoinflip - Flip a coin~! What will it be? Heads or tails?\n" +
//								"\n" +
//								".horohelp\n" +
//								"    * Usage: .horohelp or .horohelp [command] - Lists all commands or describes the command\n" +
//								"```",
//						event.getAuthor());
//				Message.sendRawPM(
//								"```\n" +
//								".horo8ball\n" +
//								"    * Usage: .horo8ball [question] - Ask away~!\n" +
//								"\n" +
//								".horokick\n" +
//								"    * Usage: .horokick [user] [reason] - Kicks a user with an optional reason\n" +
//								"\n" +
//								".horoecchi\n" +
//								"    * Usage: .horoecchi [tags] - Search for an ecchi picture with given tags\n" +
//								"\n" +
//								".horoinvite\n" +
//								"    * Usage: .horoinvite - PMs an invite link for the bot\n" +
//								"\n" +
//								".horohug\n" +
//								"    * Usage: .horohug [@mention] - Give someone a big hug\n" +
//								"\n" +
//								".horoassign\n" +
//								"    * Usage: .horoassign [user] [role] - Assign a role to someone\n" +
//								"\n" +
//								".horoprefix\n" +
//								"	* Usage: .horoprefix [prefix] - Change the prefix Horo uses, you will always be able to use .horo\n" +
//								"\n" +
//								".horopatreon\n" +
//								"	* Usage: .horopatreon = Get a link to the HoroBot Patreon page\n" +
//								"\n" +
//								".horoanime\n" +
//								"	* Usage: .horoanime [anime name] - Fetches information about the anime given\n" +
//								"\n" +
//								".horomanga\n" +
//								"	* Usage: .horomanga [manga name] - Search for a manga on MAL\n" +
//								"\n" +
//								".horourban\n" +
//								"	* Usage: .horourban [term] - Search for a term on UrbanDictionary\n" +
//								"\n" +
//								".horojoin\n" +
//								"	* Usage: .horojoin [channel name] - Make Horo join a voice channel\n" +
//								"\n" +
//								".horoleave\n" +
//								"	* Usage: .horoleave - Make Horo leave a voice channel\n" +
//								"\n" +
//								".horoplay\n" +
//								"	* Usage: .horoplay [link to the song or stream] - Play a song, it something is already playing it will be queued\n" +
//								"\n" +
//								".horopause\n" +
//								"	* Usage: .horopause - The currently playing song will be paused\n" +
//								"\n" +
//								".horounpause\n" +
//								"	* Usage: .horounpause - Unpause the song\n" +
//								"\n" +
//								".horoskip\n" +
//								"	* Usage: .horoskip - Skip the song\n" +
//								"\n" +
//								".hororepeat\n" +
//								"	* Usage: .hororepeat - Toggle song looping\n" +
//								"\n" +
//								".horosong\n" +
//								"	* Usage: .horosong - Get information about the current song\n" +
//								"\n" +
//								".horotranslate\n" +
//								"	* Usage: .horotranslate [from] [to] [text] - Translate something from one language to another\n" +
//								"\n" +
//								".horowolf\n" +
//								"	* Usage: .horowolf <feed/rename/background/strip/inventory/capsule> - Display your wolf or execute one of the sub-commands\n" +
//								"\n" +
//								".horoprofile\n" +
//								"	* Usage: .horoprofile <background/info/capsule> - Display your profile or execute one of the sub-commands\n" +
//								"```",
//						event.getAuthor());
				/*StringBuilder builder = new StringBuilder("```bf\n");
				for(Map.Entry<String, Command> c : Main.commands.entrySet()) {
					String message;
					if(event.getChannel().isPrivate()) {
						message = Localisation.getPMMessage(c.getValue().help());
					} else {
						message = Localisation.getMessage(event.getGuild().getStringID(), c.getValue().help());
					}
					String formatted = String.format(".horo%s:\n    * %s\n\n", c.getKey(),
							message);
					if(builder.length() + formatted.length() > 1995) {
						Message.sendRawPM(builder + "\n```", event.getAuthor());
						builder = new StringBuilder("```bf\n" + formatted);
					} else builder.append(formatted);
				}
				Message.sendRawPM(builder + "\n```", event.getAuthor());*/

				for(CommandType c : CommandType.getTypes()) {
					EmbedBuilder builder = new EmbedBuilder();
					builder.withColor(Color.CYAN);
					String help;
					if(!event.getChannel().isPrivate()) {
						help = c.getCommands(c)
								.stream()
								.map(command -> "**.horo" + command + "** - " + Localisation.getMessage(event.getGuild().getStringID(), Main.commands.get(command).help()) + "\n")
								.collect(Collectors.joining(""));
					} else {
						help = c.getCommands(c)
								.stream()
								.map(command -> "**.horo" + command + "** - " + Localisation.getPMMessage(Main.commands.get(command).help()) + "\n")
								.collect(Collectors.joining(""));
					}
					/*String[] result = new String[(int) Math.ceil(((help.length() / (double) 1024)))];
					int j = 0;
					for (int i = 0; i < result.length - 1; i++) {
						result[i] = help.substring(j, j + 1024);
						j += 1024;
					}
					result[result.length - 1] = help.substring(j);
					for (String actual : result) {
						builder.appendField(WordUtils.capitalizeFully(c.toString()), actual, false);
					}*/
					builder.withTitle(WordUtils.capitalizeFully(c.toString()));
					builder.withDescription(help);
					Message.sendPMEmbed(event.getAuthor(), "", builder.build(), false);
				}

				Message.sendRawPM("Hey there!\n\n" +
						"I know everyone hates these kinds of messages and so do I so I'll keep it short,\n" +
						"HoroBot is a project which I've been working on for the past month for fun but I am in desperate need of funds to keep her running.\n" +
						"So should you even have 1 dollar to spare, please head over to https://www.patreon.com/HoroBot and help me out!\n\n" +
						"Thanks a lot for reading and helping me out!", event.getAuthor());
				if(!event.getMessage().getChannel().isPrivate()) event.getMessage().delete();
			} else if(args.length == 1) {
				if(!event.getMessage().getChannel().isPrivate()) {
					String guildID = event.getMessage().getGuild().getStringID();
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

	@Override
	public CommandType getType() {
		return CommandType.UTILITY;
	}

	public void executed(boolean success, MessageReceivedEvent event) {
		if(success)
			Utility.commandsExecuted++;
	}
}