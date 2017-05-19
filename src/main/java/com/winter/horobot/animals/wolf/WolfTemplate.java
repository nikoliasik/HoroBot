package com.winter.horobot.animals.wolf;

import com.winter.horobot.animals.Template;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class WolfTemplate extends Template {

	public WolfTemplate(String name, int level, int hunger, int maxHunger, int fedTimes, BufferedImage base, BufferedImage background, BufferedImage hat, BufferedImage body, BufferedImage paws, BufferedImage tail, BufferedImage shirt, BufferedImage nose, BufferedImage eye, BufferedImage neck, BufferedImage template) {
		super(name, level, hunger, maxHunger, fedTimes, base, background, hat, body, paws, tail, shirt, nose, eye, neck, template);
	}
}