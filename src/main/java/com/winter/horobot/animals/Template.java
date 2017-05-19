package com.winter.horobot.animals;

<<<<<<< HEAD
import java.awt.image.BufferedImage;

public class Template {

=======
public class Template {
	private final String path;
>>>>>>> ff1001a677fbe8abf9b939c28ddf585b0ef0581f
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
	private BufferedImage template;

<<<<<<< HEAD
	public Template(String name, int level, int hunger, int maxHunger, int fedTimes, BufferedImage base, BufferedImage background, BufferedImage hat, BufferedImage body, BufferedImage paws, BufferedImage tail, BufferedImage shirt, BufferedImage nose, BufferedImage eye, BufferedImage neck, BufferedImage template) {
=======
	Template(String path, String name, int baseHunger) {
		this.path = path;
>>>>>>> ff1001a677fbe8abf9b939c28ddf585b0ef0581f
		this.name = name;
		this.level = level;
		this.hunger = hunger;
		this.maxHunger = maxHunger;
		this.fedTimes = fedTimes;
		this.base = base;
		this.background = background;
		this.hat = hat;
		this.body = body;
		this.paws = paws;
		this.tail = tail;
		this.shirt = shirt;
		this.nose = nose;
		this.eye = eye;
		this.neck = neck;
		this.template = template;
	}

	public String getName() {
		return name;
	}

	public int getLevel() {
		return level;
	}

	public int getHunger() {
		return hunger;
	}

	public int getMaxHunger() {
		return maxHunger;
	}

	public int getFedTimes() {
		return fedTimes;
	}

	public BufferedImage getBase() {
		return base;
	}

	public BufferedImage getBackground() {
		return background;
	}

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

	public BufferedImage getTemplate() {
		return template;
	}
}