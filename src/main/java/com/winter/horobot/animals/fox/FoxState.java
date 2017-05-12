package com.winter.horobot.animals.fox;

public class FoxState {

	public enum FOX_STATE {
		HAPPY(0, "Happy"),
		SAD(1, "Sad"),
		SLEEPING(2, "Sleeping"),
		HUNGRY(3, "Hungry");

		private final int id;
		private final String name;

		FOX_STATE(int id, String name) {
			this.id = id;
			this.name = name;
		}

		public int getID() { return id; }

		public String getName() {
			return name;
		}
	}
}