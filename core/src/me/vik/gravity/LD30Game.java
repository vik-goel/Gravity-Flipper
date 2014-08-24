package me.vik.gravity;

import me.vik.gravity.entity.gui.MusicButton;
import me.vik.gravity.game.Ad;
import me.vik.gravity.game.BackInputProcessor;
import me.vik.gravity.screen.GameOverScreen;
import me.vik.gravity.screen.GameScreen;
import me.vik.gravity.screen.MenuScreen;
import me.vik.gravity.screen.ScreenType;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class LD30Game extends Game {
	
	public static final String AD_ID = "ca-app-pub-3934076033972627/5734334197";
	
	private static Music backgroundMusic;
	
	private MenuScreen menu;
	private GameScreen game;
	private GameOverScreen gameOverScreen;
	
	private Ad ad = null;
	private int gameCounter = 0;
	private boolean justDisplayedAd = false;
	private boolean canDisplayAd = false;
	
	public void create () {
		switchToMenuScreen();
		
		Gdx.input.setInputProcessor(new BackInputProcessor(this));
		
		backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/background music.mp3"));
		backgroundMusic.setLooping(true);
		
		if (MusicButton.isSoundEnabled())
			startMusic();
	}
	
	public void switchScreen(ScreenType type) {
		switch (type) {
		case GAME:
			switchToGameScreen();
			break;
		case GAMEOVER:
			switchToGameOverScreen();
			break;
		case MENU:
			switchToMenuScreen();
			break;
		}
	}

	public void switchToMenuScreen() {
		if (menu == null)
			menu = new MenuScreen(this);
		
		setScreen(menu);
	}
	
	public void switchToGameScreen() {
		if (game == null)
			game = new GameScreen(this);
		
		setScreen(game);
		
		if (ad != null)
			ad.load();
	}

	public void switchToGameOverScreen() {
		if (willDisplayAd()) {
			ad.show();
			justDisplayedAd = true;
			gameCounter = 0;
			canDisplayAd = false;
		} else {
			if (gameOverScreen == null)
				gameOverScreen = new GameOverScreen(this);
			
			justDisplayedAd = false;
			gameCounter++;
			
			if (Math.random() > 0.5f)
				canDisplayAd = true;
			else canDisplayAd = false;
			
			setScreen(gameOverScreen);
		}
	}
	
	public static void stopMusic() {
		backgroundMusic.stop();
	}
	
	public static void startMusic() {
		backgroundMusic.play();
	}
	
	public boolean willDisplayAd() {
		return ad != null && !justDisplayedAd && gameCounter > 3 && canDisplayAd;
	}
	
	public void setAd(Ad ad) {
		this.ad = ad;
		ad.setId(AD_ID);
	}
	
}
