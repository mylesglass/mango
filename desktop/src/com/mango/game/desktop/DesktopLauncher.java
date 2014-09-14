package com.mango.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mango.game.MangoGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		config.title = "Banana Game";
		config.width = 1280;
		config.height = 720;
		
		new LwjglApplication(new MangoGame(), config);
	}
}
