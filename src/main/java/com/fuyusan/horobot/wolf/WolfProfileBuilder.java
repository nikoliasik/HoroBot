package com.fuyusan.horobot.wolf;

import com.fuyusan.horobot.database.DataBase;
import sx.blah.discord.handle.obj.IUser;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class WolfProfileBuilder {

	public static final int width = 256;
	public static final int height = 128;

	public static byte[] generateImage(IUser user) {
		final WolfTemplate wolf = DataBase.wolfQuery(user);
		BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		File file = null;
		Graphics2D graphics = bufferedImage.createGraphics();

		final Font font = new Font("Roboto", Font.TRUETYPE_FONT, 16);
		graphics.setFont(font);

		// Calculate some stuff
		final int stringX = (width / 2) - (graphics.getFontMetrics().stringWidth(wolf.getName()) / 2);
		final int stringY = (height - 16 + 10);
		final int nameWidth = (int) graphics.getFontMetrics().getStringBounds(wolf.getName(), graphics).getWidth();
		final int nameHeight = (int) graphics.getFontMetrics().getStringBounds(wolf.getName(), graphics).getHeight() + 5;
		graphics.setFont(new Font("Roboto", Font.TRUETYPE_FONT, 16));
		final int foodWidth = (int) graphics.getFontMetrics().getStringBounds("HUNGER " + wolf.getHunger() + "/" + wolf.getMaxHunger(), graphics).getWidth() + 10;
		final int foodHeight = (int) graphics.getFontMetrics().getStringBounds("HUNGER " + wolf.getHunger() + "/" + wolf.getMaxHunger(), graphics).getHeight();

		// Draw the background
		graphics.drawImage(wolf.getBackground(), 0, 0, width, height, null);

		// Draw the box around the wolf's name
		graphics.setColor(new Color(128, 128, 128, 150));
		graphics.fillRect(
				stringX - 5,
				stringY - 16,
				nameWidth + 10,
				nameHeight + 20);

		// Draw the wolf's name
		graphics.setFont(new Font("Roboto", Font.TRUETYPE_FONT, 16));
		graphics.setColor(Color.WHITE);
		graphics.drawString(
				wolf.getName(),
				stringX,
				stringY);

		// Draw the box around the wolf's stats
		graphics.setColor(new Color(128, 128, 128, 150));
		graphics.fillRect(
				0,
				0,
				(Math.max(foodWidth, foodHeight)),
				(32 + 2 + 16 + 2 + 4));

		// Draw the wolf's stats
		graphics.setColor(Color.WHITE);
		graphics.setFont(new Font("Roboto", Font.TRUETYPE_FONT, 32));
		graphics.drawString("LVL " + wolf.getLevel(), 4, 32);
		graphics.setFont(new Font("Roboto", Font.TRUETYPE_FONT, 16));
		graphics.drawString("HUNGER " + wolf.getHunger() + "/" + wolf.getMaxHunger(), 4, 32 + 2 + 16 + 2);

		// Draw the wolf base
		graphics.drawImage(wolf.getBase(),
				(width - wolf.getBase().getWidth() * 2),
				(height / 2 - wolf.getBase().getHeight()),
				wolf.getBase().getWidth() * 2,
				wolf.getBase().getHeight() * 2,
				null);
		graphics.dispose();

		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		try {
			ImageIO.write(bufferedImage, "png", bytes);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bytes.toByteArray();
	}
}