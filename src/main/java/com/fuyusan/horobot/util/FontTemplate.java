package com.fuyusan.horobot.util;

import java.awt.*;
import java.io.File;

public class FontTemplate {

	public void loadFont() {
		try {
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/fonts/Roboto-Regular.ttf")));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}