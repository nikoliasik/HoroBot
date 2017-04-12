package com.winter.horobot.wolf;

import com.winter.horobot.database.DataBase;
import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.EmbedBuilder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class WolfProfileBuilder {

	private static final int width = 300;
	private static final int height = 150;

	public static EmbedObject generateEmbed(IGuild guild, IUser user) {
		final WolfTemplate template = DataBase.wolfQuery(user);

		EmbedBuilder builder = new EmbedBuilder();
		builder.withAuthorIcon(user.getAvatarURL());
		builder.withAuthorName(user.getDisplayName(guild) + "'s Wolf");
		builder.withColor(Color.CYAN);
		builder.appendField("Level", "" + template.getLevel(), true);
		builder.appendField("Progress", template.getHunger() + " / " + template.getMaxHunger() + " food to level " + (template.getLevel() + 1), true);
		builder.appendField("Feed Stats", "This wolf has been fed a total of " + template.getFedTimes() + " times", false);
		builder.withImage("attachment://" + user.getID() + "wolf.png");
		return builder.build();
	}

	public static byte[] generateImage(IUser user) {
		final WolfTemplate wolf = DataBase.wolfQuery(user);
		BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics = bufferedImage.createGraphics();

		final Font font = new Font("Roboto", Font.PLAIN, 16);
		graphics.setFont(font);

		// Draw background
		graphics.drawImage(wolf.getBackground(), 0, 0, width, height, null);

		// Draw the template
		graphics.drawImage(wolf.getTemplate(), 0, 0, width, height, null);

		// Draw the wolf
		graphics.drawImage(generateWolf(user), 0, 0, 68, 68, null);


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
		BufferedImage bufferedImage = new BufferedImage(300, 150, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics = bufferedImage.createGraphics();

		final WolfTemplate wolf = DataBase.wolfQuery(user);

		// Draw the wolf base
		graphics.drawImage(wolf.getBase(),
				(width - wolf.getBase().getWidth() * 2),
				(height / 2 - wolf.getBase().getHeight()),
				wolf.getBase().getWidth() * 2,
				wolf.getBase().getHeight() * 2,
				null);

		// Draw the wolf cosmetics
		graphics.drawImage(wolf.getBody(),
				0,
				0,
				wolf.getBase().getWidth() * 2,
				wolf.getBase().getHeight() * 2,
				null);

		graphics.drawImage(wolf.getEye(),
				0,
				0,
				wolf.getBase().getWidth() * 2,
				wolf.getBase().getHeight() * 2,
				null);

		graphics.drawImage(wolf.getNose(),
				0,
				0,
				wolf.getBase().getWidth() * 2,
				wolf.getBase().getHeight() * 2,
				null);

		graphics.drawImage(wolf.getShirt(),
				0,
				0,
				wolf.getBase().getWidth() * 2,
				wolf.getBase().getHeight() * 2,
				null);

		graphics.drawImage(wolf.getNeck(),
				0,
				0,
				wolf.getBase().getWidth() * 2,
				wolf.getBase().getHeight() * 2,
				null);

		graphics.drawImage(wolf.getTail(),
				0,
				0,
				wolf.getBase().getWidth() * 2,
				wolf.getBase().getHeight() * 2,
				null);

		graphics.drawImage(wolf.getPaws(),
				0,
				0,
				wolf.getBase().getWidth() * 2,
				wolf.getBase().getHeight() * 2,
				null);

		graphics.drawImage(wolf.getHat(),
				0,
				0,
				wolf.getBase().getWidth() * 2,
				wolf.getBase().getHeight() * 2,
				null);

		graphics.dispose();
		return bufferedImage;
	}
}