package me.vik.gravity.util;

import me.vik.gravity.entity.gui.MusicButton;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class Sounds {

	public static Sound gravityDown = createSound("gravity down.mp3");
	public static Sound gravityUp = createSound("gravity up.mp3");
	public static Sound death = createSound("death.mp3");
	public static Sound click = createSound("click2.wav");
	public static Sound coin = createSound("coin.wav");
	public static Sound time = createSound("time.wav");
	
	private static Sound createSound(String fileName) {
		return Gdx.audio.newSound(Gdx.files.internal("sounds/" + fileName));
	}
	
	public static void play(Sound sound) {
		if (MusicButton.isSoundEnabled())
			sound.play();
	}
	
}
