package com.fuyusan.horobot.util;

import java.awt.*;
import java.io.File;

public class FontTemplate {

	public void loadFont() {
		try {
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File(getClass().getResource("/fonts/Roboto-Regular.ttf").toURI())));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}