package com.winter.horobot.util;

import com.winter.horobot.Main;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class DevUtil {

	public static void changeAvatar(String uri) {
		try {
			URL url = new URL(uri);
			Main.getClient().changeAvatar(ImageUtil.imageFromURL(url)); // TODO
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}