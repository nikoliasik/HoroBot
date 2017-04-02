package com.fuyusan.horobot.profile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class ProfileTemplate {

	private final String name;
	private final String description;
	private final int level;
	private final int xp;
	private final int maxXp;
	private final int foxCoins;
	private BufferedImage background;

	public ProfileTemplate(String name, String description, int level, int xp, int maxXp, int foxCoins, String background) {
		this.name = name;
		this.description = description;
		this.level = level;
		this.xp = xp;
		this.maxXp = maxXp;
		this.foxCoins = foxCoins;
		try {
			this.background = ImageIO.read(getClass().getResourceAsStream(ProfileCosmetics.backgrounds.get(background)));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public int getLevel() {
		return level;
	}

	public int getXp() {
		return xp;
	}

	public int getMaxXp() {
		return maxXp;
	}

	public int getFoxCoins() {
		return foxCoins;
	}

	public BufferedImage getBackground() {
		return background;
	}
}