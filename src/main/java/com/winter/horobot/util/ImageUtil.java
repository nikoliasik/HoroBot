package com.winter.horobot.util;

import sx.blah.discord.handle.obj.IUser;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

public class ImageUtil {

	private static String[] defaults = {
			"6debd47ed13483642cf09e832ed0bc1b",
			"322c936a8c8be1b803cd94861bdfa868",
			"dd4dbc0016779df1378e7812eabaa04d",
			"0e291f67c9274a1abdddeb3fd919cbaa",
			"1cbd08c76f8af6dddce02c5138971129"
	};

	public static BufferedImage imageFromURL(URL url) throws IOException {
		if (url == null)
			return null;
		URLConnection con = url.openConnection();
		con.addRequestProperty("User-Agent", "Mozilla/5.0 HoroBot Discord Bot");
		con.connect();
		return ImageIO.read(con.getInputStream());
		// TODO: I don't think this has to be closed, right? If it does then this might've been the source of the leak in default Horo
	}

	public static String getAvatar(IUser user) {
		return user.getAvatar() != null ? user.getAvatarURL() : getDefaultAvatar(user);
	}

	public static String getDefaultAvatar(IUser user) {
		int discrim = Integer.parseInt(user.getDiscriminator());
		discrim %= defaults.length;
		return "https://discordapp.com/assets/" + defaults[discrim] + ".png";
	}
}