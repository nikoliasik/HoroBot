package com.winter.horobot.command.impl.image;

import com.madgag.gif.fmsware.AnimatedGifEncoder;
import com.winter.horobot.command.proccessing.Command;
import com.winter.horobot.command.proccessing.CommandType;
import com.winter.horobot.util.Message;
import com.winter.horobot.util.Utility;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IUser;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CommandTriggered implements Command {

	@Override
	public boolean called(String[] args, MessageReceivedEvent event) {
		return true;
	}

	@Override
	public void action(String[] args, String raw, MessageReceivedEvent event) {
		AnimatedGifEncoder age = new AnimatedGifEncoder();
		age.setFrameRate(15);
		age.setRepeat(0);
		if(args.length == 1) {
			IUser u = event.getMessage().getMentions().get(0);
			ByteArrayOutputStream o = new ByteArrayOutputStream();
			age.start(o);
			try {
				List<BufferedImage> avatarFrames = null;
				try {
					URLConnection conn = new URI(u.getAvatarURL().substring(0, u.getAvatarURL().lastIndexOf('.')) + ".gif").toURL().openConnection();
					conn.addRequestProperty("User-Agent", "Mozzila/5.0");
					avatarFrames = Utility.getGifFramesFromStream(conn.getInputStream());
				} catch(Exception e) {
					URLConnection conn = new URI(u.getAvatarURL().substring(0, u.getAvatarURL().lastIndexOf('.')) + ".png").toURL().openConnection();
					conn.addRequestProperty("User-Agent", "Mozzila/5.0");
					avatarFrames = Collections.singletonList(ImageIO.read(conn.getInputStream()));
				}
				List<BufferedImage> triggeredFrames = Utility.getGifFramesFromStream(new URI("http://i.imgur.com/9WSux7S.gif").toURL().openStream());
				for(int i = 0; i < 50; i++) {
					int[] avatarFramePixels = new int[256 * 256];
					BufferedImage r = avatarFrames.get(i % avatarFrames.size());
					r.getRaster().getPixels(0, 0, 128, 128, avatarFramePixels);
					List<Integer> redPixels = Arrays.stream(avatarFramePixels).mapToObj(p -> new Color(new Color(p).getRed(), new Color(p).getGreen() / 2, new Color(p).getBlue() / 2).getRGB()).collect(Collectors.toList());
					for(int c = 0; c < redPixels.size(); c++) {
						avatarFramePixels[c] = redPixels.get(c);
					}
					r.getRaster().setPixels(0, 0, 128, 128, avatarFramePixels);
					Graphics g = r.getGraphics();
					g.drawImage(triggeredFrames.get(i % triggeredFrames.size()), 0, 0, null);
					age.addFrame(r);
				}
				age.finish();
				Message.sendFile(event.getChannel(), "", "triggered.gif", new ByteArrayInputStream(o.toByteArray()));
			} catch (IOException e) {
				e.printStackTrace(); // TODO correctly handle exceptions
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
		} else if(args.length == 0) {
			IUser u = event.getAuthor();
			for(int i = 0; i < 50; i++) {

			}
		} else {
			Message.sendMessageInChannel(event.getChannel(), help());
		}
	}

	@Override
	public String help() {
		return "triggered-help";
	}

	@Override
	public CommandType getType() {
		return CommandType.IMAGE;
	}

}
