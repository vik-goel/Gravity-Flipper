package me.vik.gravity.android;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import me.vik.gravity.LD30Game;

public class AndroidLauncher extends AndroidApplication {

	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.numSamples = 2;
		
		LD30Game game = new LD30Game();
		AndroidAd ad = new AndroidAd(this, game);
		game.setAd(ad);
		
		initialize(game, config);
	}
}
