package me.vik.gravity.entity.gui;

import me.vik.gravity.LD30Game;
import me.vik.gravity.game.ScreenSwitcher;
import me.vik.gravity.screen.ScreenType;
import me.vik.gravity.util.Textures;

public class PlayButton extends Button {

	private LD30Game game;
	
	public PlayButton(float x, float y, LD30Game game, boolean retry) {
		super(x, y, getWidth(), 0.2f, retry ? Textures.retryButton : Textures.playButton);
		this.game = game;
	}

	public static float getWidth() {
		return 0.8f;
	}

	public void onClick() {
		ScreenSwitcher.getInstance(game).startSwitch(ScreenType.GAME);
	}
}
