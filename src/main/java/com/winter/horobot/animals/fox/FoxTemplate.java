package com.winter.horobot.animals.fox;

import com.winter.horobot.animals.wolf.WolfCosmetics;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class FoxTemplate {

	private final String name;
	private final int level;
	private final int xp;
	private final int maxXP;
	private final int fedTimes;
	private final FoxState.FOX_STATE state;
	private BufferedImage fox;
	private BufferedImage background;
	private BufferedImage template;

	public FoxTemplate(String name, int level, int xp, int maxXP, int fedTimes, FoxState.FOX_STATE state, String background) {
		this.name = name;
		this.level = level;
		this.xp = xp;
		this.maxXP = maxXP;
		this.fedTimes = fedTimes;
		this.state = state;
		try {
			this.fox = ImageIO.read(getClass().getResourceAsStream("/fox/" + state.getName().toLowerCase() + ".gif"));
			this.background = ImageIO.read(getClass().getResourceAsStream(WolfCosmetics.backgrounds.get(background)));
			this.template = ImageIO.read(getClass().getResourceAsStream("/wolf/template.png"));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public String getName() {
		return name;
	}

	public int getLevel() {
		return level;
	}

	public int getXp() {
		return xp;
	}

	public int getMaxXP() {
		return maxXP;
	}

	public int getFedTimes() {
		return fedTimes;
	}

	public FoxState.FOX_STATE getState() {
		return state;
	}

	public BufferedImage getFox() { return fox; }

	public BufferedImage getBackground() { return background; }

	public BufferedImage getTemplate() { return template; }
}