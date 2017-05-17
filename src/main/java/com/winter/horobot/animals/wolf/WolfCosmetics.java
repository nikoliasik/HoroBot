package com.winter.horobot.animals.wolf;

public class WolfCosmetics {
	public enum foods {
		CRACKER("cracker", 2, 14400000),
		MEAT("meat", 6, 43200000),
		TURNIP("turnip", 1, 7200000),
		BREAD("bread", 5, 36000000),
		FRIES("fries", 3, 21600000),
		BEER("beer", 4, 28800000);

		private final String name;
		private final int value;
		private final int cooldown;

		foods(String name, int value, int cooldown) {
			this.name = name;
			this.value = value;
			this.cooldown = cooldown;
		}

		public String getName() {
			return this.name;
		}

		public int getValue() {
			return this.value;
		}

		public int getCooldown() {
			return this.cooldown;
		}
	}

	public enum backgrounds {
		NONE("/wolf/bg/default-bg.png", "None"),
		AURORA("/wolf/bg/aurora.png", "Aurora"),
		SUNSET("/wolf/bg/sunset.png", "Sunset"),
		COSMIC_DANCE("/wolf/bg/cosmic dance.png", "Cosmic Dance"),
		WINTER("/wolf/bg/winter.png", "Winter"),
		FINAL_DESTINATION("/wolf/bg/final destination.png", "Final Destination"),
		BUNNY_HOP("/wolf/bg/bunny hop.png", "Bunny Hop"),
		HEY_BABY("/wolf/bg/hey baby.png", "Hey Baby"),
		SPACE_DANCE("/wolf/bg/space dance.png", "Space Dance"),
		SPACE_DANCE_R3("/wolf/bg/space dance r3.png", "Space Dance (Remix 3)"),
		TRAM_AND_PAULINE_R3("/wolf/bg/tram and pauline r3.png", "Tram and Pauline (Remix 3)"),
		WIZARD_WALTZ("/wolf/bg/wizard's waltz.png", "Wizard's Waltz"),
		MARCHING_ORDERS("/wolf/bg/marching orders.png", "Marching Orders"),
		CROP_STOMP("/wolf/bg/crop stomp.png", "Crop Stomp"),
		SPACEBALL("/wolf/bg/spaceball.png", "Spaceball"),
		BON_ODORI("/wolf/bg/bon-odori.png", "Bon-Odori"),
		BUILT_TO_SCALE_FEVER("/wolf/bg/built to scale fever.png", "Built to Scale Fever"),
		CAFE("/wolf/bg/cafe.png", "Cafe"),
		CHEER_READERS("/wolf/bg/cheer readers.png", "Cheer Readers"),
		CLAPPY_TRIO("/wolf/bg/clappy trio.png", "Clappy Trio"),
		DJ_SCHOOL("/wolf/bg/dj school.png", "DJ School"),
		LIVING_ROOM("/wolf/bg/living room.png", "Living Room"),
		NIGHT_WALK_FEVER("/wolf/bg/night walk fever.png", "Night Walk Fever"),
		NINJA_BODYGUARD("/wolf/bg/ninja bodyguard.png", "Ninja Bodyguard"),
		QUIZ_AUDIENCE("/wolf/bg/quiz audience.png", "Quiz Audience"),
		RAP_MEN("/wolf/bg/Rap Men.png", "Rap Men"),
		RINGSIDE_BG("/wolf/bg/ringside.png", "Ringside"),
		SAMURAI_SLICE_FOGGY("/wolf/bg/samurai slice foggy.png", "Samurai Slice Foggy"),
		SAMURAI_SLICE("/wolf/bg/samurai slice.png", "Samurai Slice"),
		SNEAKY_SPIRITS("/wolf/bg/sneaky spirits.png", "Sneaky Spirits"),
		TAP_TRIAL("/wolf/bg/tap trial.png", "Tap Trial"),
		TENGAMI("/wolf/bg/tengami.png", "Tengami"),
		TOSS_BOYS("/wolf/bg/toss boys.png", "Toss Boys"),
		TRAM_AND_PAULINE("/wolf/bg/tram and pauline.png", "Tram and Pauline"),
		BLUE("/wolf/bg/blue.png", "Blue"),
		BAR("/wolf/bg/bar.png", "Bar"),
		HEY_BABY_2("/wolf/bg/hey baby 2.png", "Hey Baby 2"),
		NINJA_REINCARNATE("/wolf/bg/ninja reincarnate.png", "Ninja Reincarnate"),
		SNAPPY_TRIO("/wolf/bg/snappy trio.png", "Snappy Trio"),
		SNEAKY_SPIRITS_2("/wolf/bg/sneaky spirits 2.png", "Sneaky Spirits 2"),
		RAP_WOMEN("/wolf/bg/rap women.png", "Rap Women"),
		FLIPPER_FLOP("/wolf/bg/flipper-flop.png", "Flipper-Flop"),
		DOG_NINJA("/wolf/bg/dog ninja.png", "Dog Ninja"),
		DRUMMER_DUEL("/wolf/bg/drummer duel.png", "Drummer Duel"),
		FAN_CLUB_2("/wolf/bg/fan club 2.png", "Fan Club 2"),
		FRUIT_BASKET_2("/wolf/bg/fruit basket 2.png", "Fruit Basket 2"),
		GLEE_CLUB_2("/wolf/bg/glee club 2.png", "Glee Club 2"),
		SNEAKY_SPIRITS_STORY("/wolf/bg/sneaky spirits story.png", "Sneaky Spirits (Story)"),
		SPACE_SOCCER("/wolf/bg/space soccer.png", "Space Soccer"),
		FILLBOTS_BEE_REMIX("/wolf/bg/fillbots bee.png", "Fillbots (Bee Remix)"),
		GLEE_CLUB_STORY("/wolf/bg/glee club 1.png", "Glee Club 1"),
		SHOWTIME("/wolf/bg/showtime.png", "Showtime"),
		FIREWORKS("/wolf/bg/fireworks.png", "Fireworks"),
		FIREWORKS_SMILE("/wolf/bg/fireworks-smile.png", "Fireworks Smile");

		private final String file;
		private final String name;

		backgrounds(String file, String name) {
			this.file = file;
			this.name = name;
		}
	}

	public enum hats {
		// TODO: Hat customizables
		NONE("/wolf/hats/none.png", "None");

		private final String file;
		private final String name;

		hats(String file, String name) {
			this.file = file;
			this.name = name;
		}
	}

	public enum bodies {
		NONE("/wolf/bodies/none.png", "None");

		private final String file;
		private final String name;

		bodies(String file, String name) {
			this.file = file;
			this.name = name;
		}
	}

	public enum paws {
		NONE("/wolf/paws/none.png", "None");

		private final String file;
		private final String name;

		paws(String file, String name) {
			this.file = file;
			this.name = name;
		}
	}

	public enum tails {
		NONE("/wolf/tails/none.png", "None"),
		CHRISTMAS_LIGHTS("/wolf/tails/christmas-lights.png", "Christmas Lights");

		private final String file;
		private final String name;

		tails(String file, String name) {
			this.file = file;
			this.name = name;
		}
	}

	public enum shirts {
		NONE("/wolf/shirts/none.png", "None"),
		ANIME_SHIRT("/wolf/shirts/anime.png", "Anime Shirt");

		private final String file;
		private final String name;

		shirts(String file, String name) {
			this.file = file;
			this.name = name;
		}
	}

	public enum noses {
		NONE("/wolf/noses/none.png", "None"),
		CLOWNS_NOSE("/wolf/noses/clown-nose.png", "Clowns Nose");

		private final String file;
		private final String name;

		noses(String file, String name) {
			this.file = file;
			this.name = name;
		}
	}

	public enum eyes {
		NONE("/wolf/eyes/none.png", "None"),
		EYE_PATCH("/wolf/eyes/eye-patch.png", "Eye Patch"),
		THUG_GLASSES("/wolf/eyes/thug-life.png", "Thug Glasses");

		private final String file;
		private final String name;

		eyes(String file, String name) {
			this.file = file;
			this.name = name;
		}
	}

	public enum neck {
		NONE("/wolf/neck/none.png", "None"),
		SCARF("/wolf/neck/scarf-1.png", "Scarf"),
		SCARF_2("/wolf/neck/scarf-2.png", "Scarf 2");


		private final String file;
		private final String name;

		neck(String file, String name) {
			this.file = file;
			this.name = name;
		}
	}
}

/*package com.winter.horobot.animals.wolf;

import com.winter.horobot.database.DataBase;
import sx.blah.discord.handle.obj.IUser;

import java.util.*;

public class WolfCosmetics {

	public enum FOODS {
		CRACKER("cracker", 2, 14400000),
		MEAT("meat", 6, 43200000),
		TURNIP("turnip", 1, 7200000),
		BREAD("bread", 5, 36000000),
		FRIES("fries", 3, 21600000),
		BEER("beer", 4, 28800000);

		private String name;
		private int value;
		private int cooldown;

		FOODS(String name, int value, int cooldown) {
			this.name = name;
			this.value = value;
			this.cooldown = cooldown;
		}

		public String getName() {
			return name;
		}

		public int getValue() {
			return value;
		}

		public int getCooldown() {
			return cooldown;
		}
	}

	enum class BACKGROUNDS(String resource, String name) {

	}

	public static LinkedHashMap<String, String> backgrounds = new LinkedHashMap<>();
	static {
		("None", "/wolf/bg/default-bg.png");
		("Aurora", "/wolf/bg/aurora.png");
		("Bar", "/wolf/bg/bar.png");
		("Bon Odori", "/wolf/bg/bon-odori.png");
		("Built To Scale Fever", "/wolf/bg/built-to-scale-fever.png");
		("Bunny Hop", "/wolf/bg/bunny-hop.png");
		("Cafe", "/wolf/bg/cafe.png");
		("Cheer Readers", "/wolf/bg/cheer-readers.png");
		("Clappy Trio", "/wolf/bg/clappy-trio.png");
		("Cosmic Dance", "/wolf/bg/cosmic-dance.png");
		("Crop Stomp", "/wolf/bg/crop-stomp.png");
		("DJ School", "/wolf/bg/dj-school.png");
		("Dog Ninja", "/wolf/bg/dog-ninja.png");
		("Drummer Duel", "/wolf/bg/drummer-duel.png");
		("Fan Club 2", "/wolf/bg/fan-club-2.png");
		("Fillbots Bee", "/wolf/bg/fillbots-bee.png");
		("Final Destination", "/wolf/bg/final-destination.png");
		("Fireworks", "/wolf/bg/fireworks.png");
		("Fireworks Smile", "/wolf/bg/fireworks-smile.png");
		("Flipper Flop", "/wolf/bg/flipper-flop.png");
		("Fruit Basket 2", "/wolf/bg/fruit-basket-2.png");
		("Glee Club 1", "/wolf/bg/glee-club-1.png");
		("Glee Club 2", "/wolf/bg/glee-club-2.png");
		("Hey Baby", "/wolf/bg/hey-baby.png");
		("Hey Baby 2", "/wolf/bg/hey-baby-2.png");
		("Living Room", "/wolf/bg/living-room.png");
		("Marching Orders", "/wolf/bg/marching-orders.png");
		("Night Walk Fever", "/wolf/bg/night-walk-fever.png");
		("Ninja Bodyguard", "/wolf/bg/ninja-bodyguard.png");
		("Ninja Reincarnate", "/wolf/bg/ninja-reincarnate.png");
		("Quiz Audience", "/wolf/bg/quiz-audience.png");
		("Rap Men", "/wolf/bg/rap-men.png");
		("Rap Women", "/wolf/bg/rap-women.png");
		("Rhythm", "/wolf/bg/rhythm.png");
		("Ringside", "/wolf/bg/ringside.png");
		("Samurai Slice", "/wolf/bg/samurai-slice.png");
		("Samurai Slice Foggy", "/wolf/bg/samurai-slice-foggy.png");
		("Showtime", "/wolf/bg/showtime.png");
		("Snappy Trio", "/wolf/bg/snappy-trio.png");
		("Sneaky Spirits", "/wolf/bg/sneaky-spirits.png");
		("Sneaky Spirits 2", "/wolf/bg/sneaky-spirits-2.png");
		("Spaceball", "/wolf/bg/spaceball.png");
		("Space Dance", "/wolf/bg/space-dance.png");
		("Space Dance R3", "/wolf/bg/space-dance-r3.png");
		("Space Soccer", "/wolf/bg/space-soccer.png");
		("Sunset", "/wolf/bg/sunset.png");
		("Tap Trial", "/wolf/bg/tap-trial.png");
		("Tengami", "/wolf/bg/tengami.png");
		("Toss Boys", "/wolf/bg/toss-boys.png");
		("Tram And Pauline", "/wolf/bg/tram-and-pauline.png");
		("Tram and Pauline R3", "/wolf/bg/tram-and-pauline-r3.png");
		("Winter", "/wolf/bg/winter.png");
		("Wizards Waltz", "/wolf/bg/wizards-waltz.png");
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

	public static int totalItems = (backgrounds.size() + hats.size() + bodies.size() + paws.size() + tails.size() + noses.size() + neck.size() + shirts.size() + eyes.size());

	public static String drop() {
		Random rand = new Random();
		int type = rand.nextInt(8);
		int drop;
		switch(type) {
			case 0: {
				drop = rand.nextInt(backgrounds.size());
				String value = (new ArrayList<>(backgrounds.values())).get(drop);
				String item = getKeyName(backgrounds, value);
				if(item.equals("None")) return drop();
				return item;
			}
			case 1: {
				drop = rand.nextInt(hats.size());
				String value = (new ArrayList<>(hats.values())).get(drop);
				String item = getKeyName(hats, value);
				if(item.equals("None")) return drop();
				return item;
			}
			case 2: {
				drop = rand.nextInt(bodies.size());
				String value = (new ArrayList<>(bodies.values())).get(drop);
				String item = getKeyName(bodies, value);
				if(item.equals("None")) return drop();
				return item;
			}
			case 3: {
				drop = rand.nextInt(paws.size());
				String value = (new ArrayList<>(paws.values())).get(drop);
				String item = getKeyName(paws, value);
				if(item.equals("None")) return drop();
				return item;
			}
			case 4: {
				drop = rand.nextInt(tails.size());
				String value = (new ArrayList<>(tails.values())).get(drop);
				String item = getKeyName(tails, value);
				if(item.equals("None")) return drop();
				return item;
			}
			case 5: {
				drop = rand.nextInt(shirts.size());
				String value = (new ArrayList<>(shirts.values())).get(drop);
				String item = getKeyName(shirts, value);
				if(item.equals("None")) return drop();
				return item;
			}
			case 6: {
				drop = rand.nextInt(noses.size());
				String value = (new ArrayList<>(noses.values())).get(drop);
				String item = getKeyName(noses, value);
				if(item.equals("None")) return drop();
				return item;
			}
			case 7: {
				drop = rand.nextInt(neck.size());
				String value = (new ArrayList<>(neck.values())).get(drop);
				String item = getKeyName(neck, value);
				if(item.equals("None")) return drop();
				return item;
			}
			case 8: {
				drop = rand.nextInt(eyes.size());
				String value = (new ArrayList<>(eyes.values())).get(drop);
				String item = getKeyName(eyes, value);
				if(item.equals("None")) return drop();
				return item;
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

	public static String getItemPath(String item) {
		return
		backgrounds.containsKey(item) ? backgrounds.get(item) :
		hats.containsKey(item) ? hats.get(item) :
		paws.containsKey(item) ? paws.get(item) :
		bodies.containsKey(item) ? bodies.get(item) :
		tails.containsKey(item) ? tails.get(item) :
		shirts.containsKey(item) ? shirts.get(item) :
		noses.containsKey(item) ? noses.get(item) :
		neck.containsKey(item) ? neck.get(item) :
		eyes.containsKey(item) ? eyes.get(item) :
		null;
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
}*/