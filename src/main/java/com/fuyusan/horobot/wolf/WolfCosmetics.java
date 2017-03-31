package com.fuyusan.horobot.wolf;

import java.util.HashMap;

public class WolfCosmetics {

	public static HashMap<String, Integer> foods = new HashMap<>();
	static {
		foods.put("cracker", 2);
	}

	public static HashMap<String, String> backgrounds = new HashMap<>();
	static {
		backgrounds.put("Default", "/wolf/bg/default-bg.png");
		backgrounds.put("Aurora", "/wolf/bg/aurora.png");
		backgrounds.put("Bar", "/wolf/bg/bar.png");
		backgrounds.put("Bon Odori", "/wolf/bg/bon-odori.png");
		backgrounds.put("Built To Scale Fever", "/wolf/bg/built-to-scale-fever.png");
		backgrounds.put("Bunny Hop", "/wolf/bg/bunny-hop.png");
		backgrounds.put("Cafe", "/wolf/bg/cafe.png");
		backgrounds.put("Cheer Readers", "/wolf/bg/cheer-readers.png");
		backgrounds.put("Clappy Trio", "/wolf/bg/clappy-trio.png");
		backgrounds.put("Cosmic Dance", "/wolf/bg/cosmic-dance.png");
		backgrounds.put("Crop Stomp", "/wolf/bg/crop-stomp.png");
		backgrounds.put("DJ School", "/wolf/bg/DJ School.png");
	}

	public static HashMap<String, String> hats = new HashMap<>();
	static {
		hats.put("None", "/wolf/hats/none.png");
	}

	public static HashMap<String, String> bodies = new HashMap<>();
	static {
		bodies.put("None", "/wolf/bodies/none.png");
	}

	public static HashMap<String, String> paws = new HashMap<>();
	static {
		paws.put("None", "/wolf/paws/none.png");
	}

	public static HashMap<String, String> tails = new HashMap<>();
	static {
		tails.put("None", "/wolf/tails/none.png");
	}
}