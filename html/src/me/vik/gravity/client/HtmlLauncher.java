package me.vik.gravity.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;

import me.vik.gravity.LD30Game;

public class HtmlLauncher extends GwtApplication {

	public GwtApplicationConfiguration getConfig() {
		GwtApplicationConfiguration config = new GwtApplicationConfiguration(800, 480);
		
		config.antialiasing = true;
		config.fps = 60;

		return config;
	}

	public ApplicationListener getApplicationListener() {
		return new LD30Game();
	}
}