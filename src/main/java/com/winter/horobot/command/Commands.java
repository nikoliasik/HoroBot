package com.winter.horobot.command;

import com.winter.horobot.data.Node;
import com.winter.horobot.permission.PermissionChecks;
import com.winter.horobot.util.MessageUtil;
import com.winter.horobot.util.StatusUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.api.events.IListener;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.Permissions;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class Commands implements IListener<MessageReceivedEvent> {

	public static Logger LOGGER = LoggerFactory.getLogger(Commands.class);

	enum Category {
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

		List<Node<Command>> statusCommands = new ArrayList<>();
		statusCommands.add(new Node<>(
				new Command(
						"ping",
						PermissionChecks.hasPermision(Permissions.SEND_MESSAGES),
						StatusUtil::ping
				),
				new ArrayList<>()
		));

		commandMap.put(Category.STATUS, statusCommands);

		commandMap.values().forEach(commands::addAll);
	}

	@Override
	public void handle(MessageReceivedEvent e) {
		if (e.getMessage().getContent().startsWith(".horo")) {
			String lookingFor = MessageUtil.args(e.getMessage());
			LOGGER.debug(String.format("Looking for `%s` in the tree...", lookingFor));
			commands.forEach(n -> {
				Node<Command> gotten = n.traverseThis(commandNode -> commandNode.compileTopDown(Command::getName, (s1, s2) -> s1 + " " + s2), lookingFor);
				if (gotten != null) {
					gotten.getData().call(e);
				}
			});
		}
	}

}
