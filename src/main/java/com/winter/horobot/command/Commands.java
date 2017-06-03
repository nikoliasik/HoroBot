package com.winter.horobot.command;

import com.winter.horobot.data.Node;
import com.winter.horobot.permission.PermissionChecks;
import com.winter.horobot.util.AdminUtil;
import com.winter.horobot.util.GuildUtil;
import com.winter.horobot.util.MessageUtil;
import com.winter.horobot.util.StatusUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.api.events.IListener;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.Permissions;

import java.util.*;

public class Commands implements IListener<MessageReceivedEvent> {

	public static Logger LOGGER = LoggerFactory.getLogger(Commands.class);

	enum Category {
		ADMIN("Admin"),
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
		commandMap.put(Category.ADMIN, new ArrayList<>(Arrays.asList(
				new Node<>(new Command(
						"kick",
						PermissionChecks.hasPermision(Permissions.KICK),
						AdminUtil::kick
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
