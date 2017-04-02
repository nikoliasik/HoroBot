package com.fuyusan.horobot.profile;

import com.fuyusan.horobot.database.DataBase;
import sx.blah.discord.handle.obj.IUser;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ProfileBuilder {

	public static final int width = 300;
	public static final int height = 300;

	public static void generateProfileImage(IUser user) {
		final ProfileTemplate template = DataBase.queryUser(user);

		BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics = bufferedImage.createGraphics();

		final Font font = new Font("Roboto", Font.TRUETYPE_FONT, 16);
		graphics.setFont(font);

		// Draw background
		graphics.drawImage(template.getBackground(), 0, 0, width, height, null);

		//
	}
}