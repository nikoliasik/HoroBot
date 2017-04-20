package com.winter.horobot.animals.fox;

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
	private Image fox;
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
			this.fox = new ImageIcon(getClass().getResource("/fox/" + state.getName().toLowerCase() + ".gif")).getImage();
			this.background = ImageIO.read(getClass().getResourceAsStream("/wolf/bg/" + background + ".png"));
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