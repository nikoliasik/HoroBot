package com.fuyusan.horobot.profile;

import com.fuyusan.horobot.database.DataBase;
import com.fuyusan.horobot.util.Utility;
import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.EmbedBuilder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

public class ProfileBuilder {

	public static final int width = 300;
	public static final int height = 300;

	public static EmbedObject generateEmbed(IGuild guild, IUser user) {
		final ProfileTemplate template = DataBase.queryUser(user);

		EmbedBuilder builder = new EmbedBuilder();
		builder.withAuthorIcon(user.getAvatarURL());
		builder.withAuthorName(user.getDisplayName(guild) + "'s Profile");
		builder.withColor(Color.CYAN);
		builder.withImage("attachment://" + user.getID() + "profile.png");
		return builder.build();
	}

	public static byte[] generateProfileImage(IUser user) {
		final ProfileTemplate template = DataBase.queryUser(user);

		BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics = bufferedImage.createGraphics();

		final Font font = new Font("Roboto", Font.TRUETYPE_FONT, 16);
		graphics.setFont(font);

		BufferedImage avatar = null;
		try {
			avatar = Utility.imageFor(user.getAvatarURL().replace(".webp", ".png"));
		} catch(Exception e) {
			e.printStackTrace();
		}

		// Calculate some stuff
		final int avatarX = ((width / 2) - (avatar.getWidth() / 2));
		final int avatarY = ((height / 4) - (avatar.getHeight() / 2));

		// Draw background
		graphics.drawImage(template.getBackground(), 0, 0, width, height, null);

		// Draw profile picture border
		graphics.fillRect(avatarX, avatarY, avatar.getWidth(), avatar.getHeight());
		graphics.setColor(Color.GRAY);
		float thickness = 5;
		Stroke old = graphics.getStroke();
		graphics.setStroke(new BasicStroke(thickness));
		graphics.drawRect(avatarX, avatarY, avatar.getWidth(), avatar.getHeight());
		graphics.setStroke(old);

		// Draw profile picture
		graphics.drawImage(avatar, avatarX, avatarY, 128, 128, null);

		// Draw the stats background
		graphics.setColor(new Color(128, 128, 128, 175));
		graphics.fillRect(10, (height / 2 + 10), width - 20, (height / 2 - 20));

		graphics.dispose();
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		try {
			ImageIO.write(bufferedImage, "png", bytes);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return bytes.toByteArray();
	}
}