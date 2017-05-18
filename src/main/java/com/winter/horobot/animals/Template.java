package com.winter.horobot.animals;

public class Template {
	private final String path;
	private final String name;
	private final int baseHunger;

	Template(String path, String name, int baseHunger) {
		this.path = path;
		this.name = name;
		this.baseHunger = baseHunger;
	}

	public String getPath() {
		return this.path;
	}

	public String getName() {
		return this.name;
	}

	public int getBaseHunger() {
		return this.baseHunger;
	}
}