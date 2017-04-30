package com.winter.horobot.animals.fox;

import com.winter.horobot.database.DataBase;
import com.winter.horobot.util.Utility;
import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.EmbedBuilder;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;

public class FoxProfileBuilder {

	private static final int width = 300;
	private static final int height = 150;
	private static final double multiplier = 1.0;

	public static EmbedObject generateEmbed(IUser user) {
		final FoxTemplate template = DataBase.foxQuery(user);

		EmbedBuilder builder = new EmbedBuilder();
		builder.withAuthorIcon(Utility.getAvatar(user));
		builder.withAuthorName(user.getName() + "'s Fox");
		builder.withColor(Color.CYAN);
		builder.appendField("Level", "" + template.getLevel(), true);
		builder.appendField("State", template.getState().getName(), true);
		builder.appendField("Feed Stats", "This fox has been fed a total of " + template.getFedTimes() + " times", false);
		builder.withImage("attachment://fox.gif");
		return builder.build();
	}

	public static byte[] generateProfile(IUser user) {
		FoxTemplate template = DataBase.foxQuery(user);

		/*BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics = bufferedImage.createGraphics();
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// Draw background
		graphics.drawImage(template.getBackground(), 0, 0, width, height, null);

		// Draw the template
		graphics.drawImage(template.getTemplate(), 0, 0, width, height, null);

		// Draw the fox
		//graphics.drawImage(template.getFox(), 165, 23, 110, 110, null);

		// Draw the name
		graphics.setFont(new Font("Roboto Regular", Font.PLAIN, 18));
		graphics.setColor(new Color(186,187,189));
		final int nameX = (width / 4 + 15) - (int) (graphics.getFontMetrics().getStringBounds(template.getName(), graphics).getWidth() / 2);
		final int nameY = 60;
		graphics.drawString(template.getName(), nameX, nameY);

		// Draw the level
		graphics.setFont(new Font("Roboto Medium", Font.PLAIN, 16));
		final int levelX = (width / 4 + width / 12) - (int) (graphics.getFontMetrics().getStringBounds("Level " + template.getLevel(), graphics).getWidth() / 2);
		final int levelY = 105;
		graphics.drawString("Level " + template.getLevel(), levelX, levelY);

		// Fill progress bar
		final int barX = 17;
		final int barY = 67;
		final int barHeight = 20;
		final int size = (width / 2 - 2);
		final double percent = ((double) template.getXp() / (double) template.getMaxXP()) * (double) size;
		//final double percent = 1 * (double) size;
		final int barWidth = (int) percent;
		graphics.setColor(new Color(150,117,96));
		graphics.setClip(new RoundRectangle2D.Float(barX, barY, barWidth, barHeight, 10, 10));
		graphics.fillRect(barX, barY, barWidth, barHeight);
		graphics.setClip(null);

		// Draw the actual progress string
		graphics.setFont(new Font("Roboto Bold", Font.BOLD, 14));
		graphics.setColor(new Color(216,216,216));
		final int textX = barX + (size / 2) - (int) (graphics.getFontMetrics().getStringBounds(template.getXp() + " / " + template.getMaxXP(), graphics).getWidth() / 2);
		final int textY = 82;
		graphics.drawString(template.getXp() + " / " + template.getMaxXP(), textX, textY);

		graphics.dispose();

		ByteArrayOutputStream bytes = new ByteArrayOutputStream();

		try {
			ImageIO.write(bufferedImage, "gif", bytes);
		} catch (IOException e) {
			e.printStackTrace();
		}*/

		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		try {
			Utility.saveGifToStream(Utility.getGifFramesFromStream(new FileInputStream(template.getFox())), bytes, 40, true);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return bytes.toByteArray();
	}
}
