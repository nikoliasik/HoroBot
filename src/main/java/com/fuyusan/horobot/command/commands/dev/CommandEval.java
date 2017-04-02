package com.fuyusan.horobot.command.commands.dev;

import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * I sexually Identify as an Attack Helicopter.
 * Ever since I was a boy I dreamed of soaring over the oilfields dropping hot sticky loads on disgusting foreigners.
 * People say to me that a person being a helicopter is Impossible and I'm fucking retarded but I don't care, I'm beautiful.
 * I'm having a plastic surgeon install rotary blades, 30 mm cannons and AMG-114 Hellfire missiles on my body.
 * From now on I want you guys to call me "Apache" and respect my right to kill from above and kill needlessly.
 * If you can't accept me you're a heliphobe and need to check your vehicle privilege.
 * Thank you for being so understanding.
 *
 * @author Apache the Attack Helicopter
 */
public class CommandEval implements com.fuyusan.horobot.command.proccessing.Command {
	@Override
	public boolean called(String[] args, MessageReceivedEvent event) {
		return event.getAuthor().getID().equals("288996157202497536")
				|| event.getAuthor().getID().equals("155954930191040513");
	}

	@Override
	public void action(String[] args, String raw, MessageReceivedEvent event) {
		ScriptEngine engine = new ScriptEngineManager().getEngineByName("groovy");
		engine.put("event", event);
		engine.put("client", event.getClient());
		try {
			event.getChannel().sendMessage(String.format("```groovy\n%s```", engine.eval(raw)));
		} catch (ScriptException e) {
			event.getChannel().sendMessage(e.getMessage());
		}
	}

	@Override
	public String help() {
		return "eval-help";
	}
}
