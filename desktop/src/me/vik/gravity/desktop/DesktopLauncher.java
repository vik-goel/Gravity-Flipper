package me.vik.gravity.desktop;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import me.vik.gravity.LD30Game;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		config.width = 800;
		config.height = 480;
		config.resizable = false;
		config.backgroundFPS = -1;
		config.foregroundFPS = 60;
		config.samples = 4;
		config.title = "Gravity Flipper";
		
		config.addIcon("textures/icon-128.png", FileType.Internal);
		config.addIcon("textures/icon-32.png", FileType.Internal);
		config.addIcon("textures/icon-16.png", FileType.Internal);
		
		new LwjglApplication(new LD30Game(), config);
	}
}
