package me.vik.gravity.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;

public class Textures {

	public static Texture player = createTexture("player.png");
	public static Texture time = createTexture("time.png");
	public static Texture ring = createTexture("ring.png");
	public static Texture border = createTexture("border.png");
	
	public static Texture playButton = createTexture("play.png");
	public static Texture retryButton = createTexture("retry.png");
	public static Texture continueButton = createTexture("continue.png");
	public static Texture musicOn = createTexture("music on.png");
	public static Texture musicOff = createTexture("music off.png");
	public static Texture pauseButton = createTexture("pause.png");
	
	private static Texture createTexture(String fileName) {
		String path = "textures/" + fileName;
		
		Texture texture = new Texture(Gdx.files.internal(path));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		return texture;
	}
	
}
