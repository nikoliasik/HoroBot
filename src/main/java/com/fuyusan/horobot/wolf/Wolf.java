package com.fuyusan.horobot.wolf;

import com.fuyusan.horobot.database.DataBase;
import sx.blah.discord.handle.obj.IUser;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Wolf {

	public static File generateImage(IUser user) {
		BufferedImage bufferedImage = new BufferedImage(256, 128, BufferedImage.TYPE_INT_ARGB);
		File file = null;

		// TODO: Draw image components here
		final String name = DataBase.wolfQuery();

		try {
			file = new File("\\resources\\wolf\\temp\\" + user.getID() + "-wolf.png");
			ImageIO.write(bufferedImage, "png", file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return file;
	}
}