package com.winter.horobot.command;

import com.winter.horobot.data.Node;
import com.winter.horobot.permission.PermissionChecks;
import com.winter.horobot.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.api.events.IListener;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.Permissions;
import sx.blah.discord.util.EmbedBuilder;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Commands implements IListener<MessageReceivedEvent> {

	public static final Logger LOGGER = LoggerFactory.getLogger(Commands.class);

	enum Category {
		ADMIN("Admin"),
		FUN("Fun"),
		STATUS("Status");

		private final String name;

		Category(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

	}

	public Map<Category, List<Node<Command>>> commandMap = new EnumMap<>(Category.class);
	public List<Node<Command>> commands = new ArrayList<>();

	public Commands() {
		super();

		commandMap.put(Category.STATUS, new ArrayList<>(Arrays.asList(
				new Node<>(new Command(
						"ping",
						PermissionChecks.hasPermision(Permissions.SEND_MESSAGES),
						StatusUtil::ping
				), Collections.emptyList()),
				new Node<>(new Command(
						"hi",
						PermissionChecks.hasPermision(Permissions.SEND_MESSAGES),
						e -> {
							e.getChannel().sendMessage("Hi!");
							return true;
						}
				), Collections.emptyList())
		)));
		commandMap.put(Category.FUN, new ArrayList<>(Arrays.asList(
				new Node<>(new Command(
						"hue",
						PermissionChecks.hasPermision(Permissions.SEND_MESSAGES),
						e -> {
							float lower = Float.parseFloat(MessageUtil.argsArray(e.getMessage())[1]);
							float upper = Float.parseFloat(MessageUtil.argsArray(e.getMessage())[2]);
							Color c = ColorUtil.withinTwoHues(lower, upper);
							EmbedBuilder eb = new EmbedBuilder();
							eb.appendDescription("You got **#" + Integer.toHexString(c.getRGB()).substring(2, 8) + "**");
							eb.withColor(c);
							e.getChannel().sendMessage(eb.build());
							return true;
						}
				), Collections.emptyList())
		)));
		commandMap.put(Category.ADMIN, new ArrayList<>(Arrays.asList(
				new Node<>(new Command(
						"kick",
						PermissionChecks.hasPermision(Permissions.KICK),
						AdminUtil::kick
				), Collections.emptyList()),
				new Node<>(new Command(
						"ban",
						PermissionChecks.hasPermision(Permissions.BAN),
						AdminUtil::ban
				), Collections.emptyList())
		)));

		commandMap.values().forEach(commands::addAll);
	}

	@Override
	public void handle(MessageReceivedEvent e) {
		if (e.getMessage().getContent().startsWith(GuildUtil.getPrefix(e.getGuild()))) {
			String lookingFor = MessageUtil.args(e.getMessage());
			LOGGER.debug(String.format("Looking for `%s` in the tree...", lookingFor));
			commands.forEach(n -> {
				Node<Command> gotten = n.traverseThis(commandNode -> commandNode.compileTopDown(Command::getName, (s1, s2) -> s1 + " " + s2), lookingFor, (command, match) -> match.startsWith(command));
				if (gotten != null) {
					gotten.getData().call(e);
				}
			});
		}
	}

}
