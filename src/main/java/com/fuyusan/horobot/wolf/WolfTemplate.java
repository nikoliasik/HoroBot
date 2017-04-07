package com.fuyusan.horobot.wolf;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class WolfTemplate {

	private final String name;
	private final int level;
	private final int hunger;
	private final int maxHunger;
	private final int fedTimes;
	private BufferedImage base;
	private BufferedImage background;
	private BufferedImage hat;
	private BufferedImage body;
	private BufferedImage paws;
	private BufferedImage tail;
	private BufferedImage shirt;
	private BufferedImage nose;
	private BufferedImage eye;
	private BufferedImage neck;

	public WolfTemplate(String name, int level, int hunger, int maxHunger, int fedTimes, String background, String hat, String body, String paws, String tail, String shirt, String nose, String eye, String neck) {
		this.name = name;
		this.level = level;
		this.hunger = hunger;
		this.maxHunger = maxHunger;
		this.fedTimes = fedTimes;
		try {
			this.base = ImageIO.read(getClass().getResourceAsStream("/wolf/wolf-base.png"));
			this.background = ImageIO.read(getClass().getResourceAsStream(WolfCosmetics.backgrounds.get(background)));
			this.hat = ImageIO.read(getClass().getResourceAsStream(WolfCosmetics.hats.get(hat)));
			this.body = ImageIO.read(getClass().getResourceAsStream(WolfCosmetics.bodies.get(body)));
			this.paws = ImageIO.read(getClass().getResourceAsStream(WolfCosmetics.paws.get(paws)));
			this.tail = ImageIO.read(getClass().getResourceAsStream(WolfCosmetics.tails.get(tail)));
			this.shirt = ImageIO.read(getClass().getResourceAsStream(WolfCosmetics.shirts.get(shirt)));
			this.nose = ImageIO.read(getClass().getResourceAsStream(WolfCosmetics.noses.get(nose)));
			this.eye = ImageIO.read(getClass().getResourceAsStream(WolfCosmetics.eyes.get(eye)));
			this.neck = ImageIO.read(getClass().getResourceAsStream(WolfCosmetics.neck.get(neck)));
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

	public int getFedTimes() { return fedTimes; }

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

	public BufferedImage getShirt() {
		return shirt;
	}

	public BufferedImage getNose() {
		return nose;
	}

	public BufferedImage getEye() {
		return eye;
	}

	public BufferedImage getNeck() {
		return neck;
	}
}