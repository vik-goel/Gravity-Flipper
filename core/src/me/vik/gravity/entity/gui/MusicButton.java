package me.vik.gravity.entity.gui;

import me.vik.gravity.LD30Game;
import me.vik.gravity.entity.Camera;
import me.vik.gravity.game.ScoreKeeper;
import me.vik.gravity.util.Textures;

import com.badlogic.gdx.graphics.Texture;

public class MusicButton extends Button {

	private static boolean musicOn = ScoreKeeper.getInstance().isMusicOn();
	
	public MusicButton(float x, float y, float size) {
		super(x, y, size, size, getTexture());
	}
	
	public void render(Camera camera) {
		super.render(camera);
		texture = getTexture();
	}

	public void onClick() {
		musicOn = !musicOn;
		texture = getTexture();
		
		if (musicOn)
			LD30Game.startMusic();
		else LD30Game.stopMusic();
		
		ScoreKeeper.getInstance().setMusicOn(musicOn);
	}
	
	private static Texture getTexture() {
		return musicOn ? Textures.musicOn : Textures.musicOff;
	}

	public static boolean isSoundEnabled() {
		return musicOn;
	}
	

}
