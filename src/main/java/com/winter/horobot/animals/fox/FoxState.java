package com.winter.horobot.animals.fox;

public class FoxState {

	public enum FOX_STATE {
		HAPPY("Happy"),
		SAD("Sad"),
		SLEEPING("Sleeping"),
		HUNGRY("Hungry");

		private final String name;

		FOX_STATE(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
	}
}