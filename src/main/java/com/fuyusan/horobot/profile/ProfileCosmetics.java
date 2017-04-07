package com.fuyusan.horobot.profile;

import com.fuyusan.horobot.database.DataBase;
import sx.blah.discord.handle.obj.IUser;

import java.util.*;

public class ProfileCosmetics {

	public static LinkedHashMap<String, String> backgrounds = new LinkedHashMap<>();
	static {
		backgrounds.put("None", "/wolf/bg/default-bg.png");
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
		backgrounds.put("DJ School", "/wolf/bg/dj-school.png");
		backgrounds.put("Dog Ninja", "/wolf/bg/dog-ninja.png");
		backgrounds.put("Drummer Dual", "/wolf/bg/drummer-dual.png");
		backgrounds.put("Fan Club 2", "/wolf/bg/fan-club-2.png");
		backgrounds.put("Fillbots Bee", "/wolf/bg/fillbots-bee.png");
		backgrounds.put("Final Destination", "/wolf/bg/final-destination.png");
		backgrounds.put("Fireworks", "/wolf/bg/fireworks.png");
		backgrounds.put("Fireworks Smile", "/wolf/bg/fireworks-smile.png");
		backgrounds.put("Flipper Flop", "/wolf/bg/flipper-flop.png");
		backgrounds.put("Fruit Basket 2", "/wolf/bg/fruit-basket-2.png");
		backgrounds.put("Glee Club 1", "/wolf/bg/glee-club-1.png");
		backgrounds.put("Glee Club 2", "/wolf/bg/glee-club-2.png");
		backgrounds.put("Hey Baby", "/wolf/bg/hey-baby.png");
		backgrounds.put("Hey Baby 2", "/wolf/bg/hey-baby-2.png");
		backgrounds.put("Living Room", "/wolf/bg/living-room.png");
		backgrounds.put("Marching Orders", "/wolf/bg/marching-orders.png");
		backgrounds.put("Night Walk Fever", "/wolf/bg/night-walk-fever.png");
		backgrounds.put("Ninja Bodyguard", "/wolf/bg/ninja-bodyguard.png");
		backgrounds.put("Ninja Reincarnate", "/wolf/bg/ninja-reincarnate.png");
		backgrounds.put("Quiz Audience", "/wolf/bg/quiz-audience.png");
		backgrounds.put("Rap Men", "/wolf/bg/rap-men.png");
		backgrounds.put("Rap Women", "/wolf/bg/rap-women.png");
		backgrounds.put("Rhythm", "/wolf/bg/rhythm.png");
		backgrounds.put("Ringside", "/wolf/bg/ringside.png");
		backgrounds.put("Samurai Slice", "/wolf/bg/samurai-slice.png");
		backgrounds.put("Samurai Slice Foggy", "/wolf/bg/samurai-slice-foggy.png");
		backgrounds.put("Showtime", "/wolf/bg/showtime.png");
		backgrounds.put("Snappy Trio", "/wolf/bg/snappy-trio.png");
		backgrounds.put("Sneaky Spirits", "/wolf/bg/sneaky-spirits.png");
		backgrounds.put("Sneaky Spirits 2", "/wolf/bg/sneaky-spirits-2.png");
		backgrounds.put("Spaceball", "/wolf/bg/spaceball.png");
		backgrounds.put("Space Dance", "/wolf/bg/space-dance.png");
		backgrounds.put("Space Dance R3", "/wolf/bg/space-dance-r3.png");
		backgrounds.put("Space Soccer", "/wolf/bg/space-soccer.png");
		backgrounds.put("Sunset", "/wolf/bg/sunset.png");
		backgrounds.put("Tap Trial", "/wolf/bg/tap-trial.png");
		backgrounds.put("Tengami", "/wolf/bg/tengami.png");
		backgrounds.put("Toss Boys", "/wolf/bg/toss-boys.png");
		backgrounds.put("Tram And Pauline", "/wolf/bg/tram-and-pauline.png");
		backgrounds.put("Tram and Pauline R3", "/wolf/bg/tram-and-pauline-r3.png");
		backgrounds.put("Winter", "/wolf/bg/winter.png");
		backgrounds.put("Wizards Waltz", "/wolf/bg/wizards-waltz.png");
	}

	public static int totalItems = (backgrounds.size());

	public static String drop(IUser user) {
		Random rand = new Random();
		int drop;
		drop = rand.nextInt(backgrounds.size());
		String value = (new ArrayList<>(backgrounds.values())).get(drop);
		String item = getKeyName(backgrounds, value);
		if(item.equals("None")) return drop(user);
		if(!hasItem(user, item)) return item;
		if(!hasAllItems(user)) return null;
		return drop(user);
	}

	public static String getKeyName(LinkedHashMap<String, String> hashMap, String value) {
		Iterator<Map.Entry<String, String>> iterator = hashMap.entrySet().iterator();
		while(iterator.hasNext()) {
			Map.Entry<String, String> entry = iterator.next();
			if(entry.getValue().equals(value)) return entry.getKey();
		}
		return "Unknown";
	}

	public static boolean hasItem(IUser user, String item) {
		return (DataBase.queryItem(user, item) != null);
	}

	public static boolean hasAllItems(IUser user) {
		return (DataBase.queryItems(user).size() >= totalItems);
	}
}