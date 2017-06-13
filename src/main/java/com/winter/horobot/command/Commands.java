package com.winter.horobot.command;

import com.winter.horobot.data.locale.Localisation;
import com.winter.horobot.data.Node;
import com.winter.horobot.permission.PermissionChecks;
import com.winter.horobot.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.api.events.IListener;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.Permissions;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.EmbedBuilder;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Commands implements IListener<MessageReceivedEvent> {

	public static final Logger LOGGER = LoggerFactory.getLogger(Commands.class);

	enum Category {
		ADMIN("admin"),
		FUN("fun"),
		STATUS("status");

		private final String name;

		Category(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

	}

	public static final Map<Category, List<Node<Command>>> COMMAND_MAP = new EnumMap<>(Category.class);
	public static final List<Node<Command>> COMMANDS = new ArrayList<>();

	static {
		COMMAND_MAP.put(Category.STATUS, new ArrayList<>(Arrays.asList(
				new Node<>(
						new Command(
								"help",
								PermissionChecks.hasPermision(Permissions.SEND_MESSAGES),
								Commands::sendHelp
						), Collections.emptyList()),
				new Node<>(new Command(
						"ping",
						PermissionChecks.hasPermision(Permissions.SEND_MESSAGES),
						StatusUtil::ping,
						new HashSet<>(Collections.singleton("pong"))
				), Collections.emptyList()),
				new Node<>(new Command(
						"hi",
						PermissionChecks.hasPermision(Permissions.SEND_MESSAGES),
						e -> {
							e.getChannel().sendMessage(Localisation.of(e.getGuild(), "hello"));
							return true;
						}
				), Collections.emptyList())
		)));
		COMMAND_MAP.put(Category.FUN, new ArrayList<>(Arrays.asList(
				new Node<>(new Command(
						"hue",
						PermissionChecks.hasPermision(Permissions.SEND_MESSAGES),
						e -> {
							float lower = Float.parseFloat(MessageUtil.argsArray(e.getMessage())[1]);
							float upper = Float.parseFloat(MessageUtil.argsArray(e.getMessage())[2]);
							Color c = ColorUtil.withinTwoHues(lower, upper);
							EmbedBuilder eb = new EmbedBuilder();
							eb.appendDescription(String.format(Localisation.of(e.getGuild(), "get"), "#" + Integer.toHexString(c.getRGB()).substring(2, 8)));
							eb.withColor(c);
							e.getChannel().sendMessage(eb.build());
							return true;
						}
				), Collections.emptyList())
		)));
		COMMAND_MAP.put(Category.ADMIN, new ArrayList<>(Arrays.asList(
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

		COMMAND_MAP.values().forEach(COMMANDS::addAll);
	}

	private static boolean sendHelp(MessageReceivedEvent e) {
		try {
			IChannel dmChannel = e.getAuthor().getOrCreatePMChannel();
			for (Category c : COMMAND_MAP.keySet()) {
				EmbedBuilder eb = new EmbedBuilder();
				eb.withColor(Color.MAGENTA.darker());
				eb.withTitle(Localisation.of(e.getGuild(), c.getName()) + " " + Localisation.of(e.getGuild(), "command"));
				StringBuilder desc = new StringBuilder();
				for (Node<Command> n : COMMAND_MAP.get(c)) {
					desc.append("`").append(n.getData().getName()).append("`\n");
				}
				eb.appendDescription(desc.toString());
				dmChannel.sendMessage(eb.build());
			}
			return true;
		} catch (DiscordException de) {
			return false;
		}
	}

	@Override
	public void handle(MessageReceivedEvent e) {
		if (e.getMessage().getContent().startsWith(GuildUtil.getPrefix(e.getGuild()))) {
			String lookingFor = MessageUtil.args(e.getMessage());
			LOGGER.debug(String.format("Looking for `%s` in the tree...", lookingFor));
			COMMANDS.forEach(n -> {
				Node<Command> gotten = n.traverseThis(node -> node.getData().getAliases(), lookingFor, (t, m) -> m.startsWith(t), false);
				if (gotten != null) {
					gotten.getData().call(e);
				}
			});
		}
	}
}