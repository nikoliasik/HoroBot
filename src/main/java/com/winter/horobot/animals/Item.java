package com.winter.horobot.animals;

public class Item {

	private final String file;
	private final String name;

	public Item(String path, String name) {
		this.file = path;
		this.name = name;
	}

	public String getFile() {
		return file;
	}

	public String getName() {
		return name;
	}
}
