package com.fuyusan.horobot.wolf;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class WolfTemplate {

	private final String name;
	private final int level;
	private final int hunger;
	private final int maxHunger;
	private BufferedImage base;
	private BufferedImage background;
	private BufferedImage hat;
	private BufferedImage body;
	private BufferedImage paws;
	private BufferedImage tail;

	public WolfTemplate(String name, int level, int hunger, int maxHunger, String background, String hat, String body, String paws, String tail) {
		this.name = name;
		this.level = level;
		this.hunger = hunger;
		this.maxHunger = maxHunger;
		try {
			this.base = ImageIO.read(new File(getClass().getResource("/wolf/wolf-base.png").toURI()));
			this.background = ImageIO.read(new File(getClass().getResource(WolfCosmetics.backgrounds.get(background)).toURI()));
			this.hat = ImageIO.read(new File(getClass().getResource(WolfCosmetics.hats.get(hat)).toURI()));
			this.body = ImageIO.read(new File(getClass().getResource(WolfCosmetics.bodies.get(body)).toURI()));
			this.paws = ImageIO.read(new File(getClass().getResource(WolfCosmetics.paws.get(paws)).toURI()));
			this.tail = ImageIO.read(new File(getClass().getResource(WolfCosmetics.tails.get(tail)).toURI()));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public String getName() {
		return name;
	}

	public int getLevel() { return level; }

	public int getHunger() {
		return hunger;
	}

	public int getMaxHunger() {
		return maxHunger;
	}

	public BufferedImage getBase() { return base; }

	public BufferedImage getBackground() { return background; }

	public BufferedImage getHat() {
		return hat;
	}

	public BufferedImage getBody() {
		return body;
	}

	public BufferedImage getPaws() {
		return paws;
	}

	public BufferedImage getTail() {
		return tail;
	}
}