package com.fuyusan.horobot.profile;

import com.fuyusan.horobot.database.DataBase;
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

		final BufferedImage avatar;

		// Calculate some stuff
		//final int avatarX = ((width / 2) - (avatar.getWidth() / 2));
		//final int avatarY = ((height / 4) + (height / 2));

		// Draw background
		graphics.drawImage(template.getBackground(), 0, 0, width, height, null);

		// Draw profile picture
		//graphics.drawImage(avatar, 0, 0, width, height, null);

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