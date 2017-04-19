package com.winter.horobot.animals.wolf;

import com.winter.horobot.database.DataBase;
import sx.blah.discord.handle.obj.IUser;

import java.util.*;

public class WolfCosmetics {

	public static HashMap<String, Integer> foods = new HashMap<>();
	static {
		foods.put("cracker", 2);
		foods.put("meat", 2);
		foods.put("turnip", 2);
		foods.put("bread", 2);
		foods.put("fries", 2);
		foods.put("beer", 2);
	}

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
		backgrounds.put("Drummer Duel", "/wolf/bg/drummer-duel.png");
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

	public static LinkedHashMap<String, String> hats = new LinkedHashMap<>();
	static {
		hats.put("None", "/wolf/hats/none.png");
	}

	public static LinkedHashMap<String, String> bodies = new LinkedHashMap<>();
	static {
		bodies.put("None", "/wolf/bodies/none.png");
	}

	public static LinkedHashMap<String, String> paws = new LinkedHashMap<>();
	static {
		paws.put("None", "/wolf/paws/none.png");
	}

	public static LinkedHashMap<String, String> tails = new LinkedHashMap<>();
	static {
		tails.put("None", "/wolf/tails/none.png");
		tails.put("Christmas Lights", "/wolf/tails/christmas-lights.png");
	}

	public static LinkedHashMap<String, String> shirts = new LinkedHashMap<>();
	static {
		shirts.put("None", "/wolf/shirts/none.png");
		shirts.put("Anime Shirt", "/wolf/shirts/anime.png");
	}

	public static LinkedHashMap<String, String> noses = new LinkedHashMap<>();
	static {
		noses.put("None", "/wolf/noses/none.png");
		noses.put("Clowns Nose", "/wolf/noses/clown-nose.png");
	}

	public static LinkedHashMap<String, String> eyes = new LinkedHashMap<>();
	static {
		eyes.put("None", "/wolf/eyes/none.png");
		eyes.put("Eye Patch", "/wolf/eyes/eye-patch.png");
		eyes.put("Thug Glasses", "/wolf/eyes/thug-life.png");
	}

	public static LinkedHashMap<String, String> neck = new LinkedHashMap<>();
	static {
		neck.put("None", "/wolf/neck/none.png");
		neck.put("Scarf", "/wolf/neck/scarf-1.png");
		neck.put("Scarf 2", "/wolf/neck/scarf-2.png");
	}

	private static int totalItems = (backgrounds.size() + hats.size() + bodies.size() + paws.size() + tails.size() + noses.size() + neck.size() + shirts.size() + eyes.size());

	public static String drop(IUser user) {
		Random rand = new Random();
		int type = rand.nextInt(8);
		int drop;
		switch(type) {
			case 0: {
				drop = rand.nextInt(backgrounds.size());
				String value = (new ArrayList<>(backgrounds.values())).get(drop);
				String item = getKeyName(backgrounds, value);
				if(item.equals("None")) return drop(user);
				if(!hasItem(user, item)) return item;
				if(!hasAllItems(user)) return drop(user);
				if(hasItem(user, item)) return drop(user);
			}
			case 1: {
				drop = rand.nextInt(hats.size());
				String value = (new ArrayList<>(hats.values())).get(drop);
				String item = getKeyName(hats, value);
				if(item.equals("None")) return drop(user);
				if(!hasItem(user, item)) return item;
				if(!hasAllItems(user)) return drop(user);
				if(hasItem(user, item)) return drop(user);
			}
			case 2: {
				drop = rand.nextInt(bodies.size());
				String value = (new ArrayList<>(bodies.values())).get(drop);
				String item = getKeyName(bodies, value);
				if(item.equals("None")) return drop(user);
				if(!hasItem(user, item)) return item;
				if(!hasAllItems(user)) return drop(user);
				if(hasItem(user, item)) return drop(user);
			}
			case 3: {
				drop = rand.nextInt(paws.size());
				String value = (new ArrayList<>(paws.values())).get(drop);
				String item = getKeyName(paws, value);
				if(item.equals("None")) return drop(user);
				if(!hasItem(user, item)) return item;
				if(!hasAllItems(user)) return drop(user);
				if(hasItem(user, item)) return drop(user);
			}
			case 4: {
				drop = rand.nextInt(tails.size());
				String value = (new ArrayList<>(tails.values())).get(drop);
				String item = getKeyName(tails, value);
				if(item.equals("None")) return drop(user);
				if(!hasItem(user, item)) return item;
				if(!hasAllItems(user)) return drop(user);
				if(hasItem(user, item)) return drop(user);
			}
			case 5: {
				drop = rand.nextInt(shirts.size());
				String value = (new ArrayList<>(shirts.values())).get(drop);
				String item = getKeyName(shirts, value);
				if(item.equals("None")) return drop(user);
				if(!hasItem(user, item)) return item;
				if(!hasAllItems(user)) return drop(user);
				if(hasItem(user, item)) return drop(user);
			}
			case 6: {
				drop = rand.nextInt(noses.size());
				String value = (new ArrayList<>(noses.values())).get(drop);
				String item = getKeyName(noses, value);
				if(item.equals("None")) return drop(user);
				if(!hasItem(user, item)) return item;
				if(!hasAllItems(user)) return drop(user);
				if(hasItem(user, item)) return drop(user);
			}
			case 7: {
				drop = rand.nextInt(neck.size());
				String value = (new ArrayList<>(neck.values())).get(drop);
				String item = getKeyName(neck, value);
				if(item.equals("None")) return drop(user);
				if(!hasItem(user, item)) return item;
				if(!hasAllItems(user)) return drop(user);
				if(hasItem(user, item)) return drop(user);
			}
			case 8: {
				drop = rand.nextInt(eyes.size());
				String value = (new ArrayList<>(eyes.values())).get(drop);
				String item = getKeyName(eyes, value);
				if(item.equals("None")) return drop(user);
				if(!hasItem(user, item)) return item;
				if(!hasAllItems(user)) return drop(user);
				if(hasItem(user, item)) return drop(user);
			}
		}
		return null;
	}

	private static String getKeyName(LinkedHashMap<String, String> hashMap, String value) {
		for(Map.Entry<String, String> entry : hashMap.entrySet()) {
			if(entry.getValue().equals(value)) return entry.getKey();
		}
		return "None";
	}

	private static boolean hasItem(IUser user, String item) {
		return DataBase.queryItem(user, item) != null;
	}

	private static boolean hasAllItems(IUser user) {
		List<String> items = DataBase.queryItems(user);
		if (items != null) {
			int count = items.size();
			if(count >= totalItems - 1) return true;
		}
		return true;
	}

	public static String getType(String item) {
		return backgrounds.containsKey(item) ? "background" :
				hats.containsKey(item) ? "hat" :
				paws.containsKey(item) ? "paws" :
				bodies.containsKey(item) ? "body" :
				tails.containsKey(item) ? "tail" :
				shirts.containsKey(item) ? "shirt" :
				noses.containsKey(item) ? "nose" :
				neck.containsKey(item) ? "neck" :
				eyes.containsKey(item) ? "eye" :
				null;
	}
}