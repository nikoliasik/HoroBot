package com.winter.horobot.wolf;

import com.winter.horobot.database.DataBase;
import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.EmbedBuilder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class WolfProfileBuilder {

	private static final int width = 300;
	private static final int height = 150;

	private static final double multiplier = 2.5;

	public static EmbedObject generateEmbed(IUser user) {
		final WolfTemplate template = DataBase.wolfQuery(user);

		EmbedBuilder builder = new EmbedBuilder();
		builder.withAuthorIcon(user.getAvatarURL());
		builder.withAuthorName(user.getName() + "'s Wolf");
		builder.withColor(Color.CYAN);
		builder.appendField("Level", "" + template.getLevel(), true);
		builder.appendField("Progress", template.getHunger() + " / " + template.getMaxHunger() + " food to level " + (template.getLevel() + 1), true);
		builder.appendField("Feed Stats", "This wolf has been fed a total of " + template.getFedTimes() + " times", false);
		builder.withImage("attachment://wolf.png");
		return builder.build();
	}

	public static byte[] generateImage(IUser user) {
		final WolfTemplate wolf = DataBase.wolfQuery(user);
		BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics = bufferedImage.createGraphics();
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// Draw background
		graphics.drawImage(wolf.getBackground(), 0, 0, width, height, null);

		// Draw the template
		graphics.drawImage(wolf.getTemplate(), 0, 0, width, height, null);

		// Draw the wolf
		final double calc = wolf.getBase().getWidth() * multiplier;
		final int sizeWolf = (int) calc;
		graphics.drawImage(generateWolf(user), 175, 20, sizeWolf, sizeWolf, null);

		// Draw the name
		graphics.setFont(new Font("Roboto Regular", Font.PLAIN, 18));
		graphics.setColor(new Color(186,187,189));
		final int nameX = (width / 4 + 15) - (int) (graphics.getFontMetrics().getStringBounds(wolf.getName(), graphics).getWidth() / 2);
		final int nameY = 60;
		graphics.drawString(wolf.getName(), nameX, nameY);

		// Draw the level
		graphics.setFont(new Font("Roboto Medium", Font.PLAIN, 16));
		final int levelX = (width / 4 + width / 12) - (int) (graphics.getFontMetrics().getStringBounds("Level " + wolf.getLevel(), graphics).getWidth() / 2);
		final int levelY = 105;
		graphics.drawString("Level " + wolf.getLevel(), levelX, levelY);

		// Fill progress bar
		final int barX = 17;
		final int barY = 67;
		final int barHeight = 20;
		final int size = (width / 2 - 2);
		final double percent = ((double) wolf.getHunger() / (double) wolf.getMaxHunger()) * (double) size;
		//final double percent = 1 * (double) size;
		final int barWidth = (int) percent;
		graphics.setColor(new Color(150,117,96));
		graphics.setClip(new RoundRectangle2D.Float(barX, barY, barWidth, barHeight, 10, 10));
		graphics.fillRect(barX, barY, barWidth, barHeight);
		graphics.setClip(null);

		// Draw the actual progress string
		graphics.setFont(new Font("Roboto Bold", Font.BOLD, 14));
		graphics.setColor(new Color(216,216,216));
		final int textX = barX + (size / 2) - (int) (graphics.getFontMetrics().getStringBounds(wolf.getHunger() + " / " + wolf.getMaxHunger(), graphics).getWidth() / 2);
		final int textY = 82;
		graphics.drawString(wolf.getHunger() + " / " + wolf.getMaxHunger(), textX, textY);

		/* LEGACY PROFILE */
		/*// Calculate some stuff
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

		// Draw the wolf cosmetics
		graphics.drawImage(wolf.getBody(),
				(width - wolf.getBase().getWidth() * 2),
				(height / 2 - wolf.getBase().getHeight()),
				wolf.getBase().getWidth() * 2,
				wolf.getBase().getHeight() * 2,
				null);

		graphics.drawImage(wolf.getEye(),
				(width - wolf.getBase().getWidth() * 2),
				(height / 2 - wolf.getBase().getHeight()),
				wolf.getBase().getWidth() * 2,
				wolf.getBase().getHeight() * 2,
				null);

		graphics.drawImage(wolf.getNose(),
				(width - wolf.getBase().getWidth() * 2),
				(height / 2 - wolf.getBase().getHeight()),
				wolf.getBase().getWidth() * 2,
				wolf.getBase().getHeight() * 2,
				null);

		graphics.drawImage(wolf.getShirt(),
				(width - wolf.getBase().getWidth() * 2),
				(height / 2 - wolf.getBase().getHeight()),
				wolf.getBase().getWidth() * 2,
				wolf.getBase().getHeight() * 2,
				null);

		graphics.drawImage(wolf.getNeck(),
				(width - wolf.getBase().getWidth() * 2),
				(height / 2 - wolf.getBase().getHeight()),
				wolf.getBase().getWidth() * 2,
				wolf.getBase().getHeight() * 2,
				null);

		graphics.drawImage(wolf.getTail(),
				(width - wolf.getBase().getWidth() * 2),
				(height / 2 - wolf.getBase().getHeight()),
				wolf.getBase().getWidth() * 2,
				wolf.getBase().getHeight() * 2,
				null);

		graphics.drawImage(wolf.getPaws(),
				(width - wolf.getBase().getWidth() * 2),
				(height / 2 - wolf.getBase().getHeight()),
				wolf.getBase().getWidth() * 2,
				wolf.getBase().getHeight() * 2,
				null);

		graphics.drawImage(wolf.getHat(),
				(width - wolf.getBase().getWidth() * 2),
				(height / 2 - wolf.getBase().getHeight()),
				wolf.getBase().getWidth() * 2,
				wolf.getBase().getHeight() * 2,
				null);*/

		graphics.dispose();

		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		try {
			ImageIO.write(bufferedImage, "png", bytes);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bytes.toByteArray();
	}

	public static BufferedImage generateWolf(IUser user) {
		final WolfTemplate wolf = DataBase.wolfQuery(user);
		BufferedImage bufferedImage = new BufferedImage(wolf.getBase().getWidth() * 4, wolf.getBase().getHeight() * 4, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics = bufferedImage.createGraphics();

		final double calc = wolf.getBase().getWidth() * multiplier;
		final int size = (int) calc;

		// Draw the wolf base
		graphics.drawImage(wolf.getBase(),
				0,
				0,
				size,
				size,
				null);

		// Draw the wolf cosmetics
		graphics.drawImage(wolf.getBody(),
				0,
				0,
				size,
				size,
				null);

		graphics.drawImage(wolf.getEye(),
				0,
				0,
				size,
				size,
				null);

		graphics.drawImage(wolf.getNose(),
				0,
				0,
				size,
				size,
				null);

		graphics.drawImage(wolf.getShirt(),
				0,
				0,
				size,
				size,
				null);

		graphics.drawImage(wolf.getNeck(),
				0,
				0,
				size,
				size,
				null);

		graphics.drawImage(wolf.getTail(),
				0,
				0,
				size,
				size,
				null);

		graphics.drawImage(wolf.getPaws(),
				0,
				0,
				size,
				size,
				null);

		graphics.drawImage(wolf.getHat(),
				0,
				0,
				size,
				size,
				null);

		graphics.dispose();
		return bufferedImage;
	}
}