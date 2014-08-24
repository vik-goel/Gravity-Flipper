package me.vik.gravity.entity;

import java.util.ArrayList;

import me.vik.gravity.util.Sounds;
import me.vik.gravity.util.Textures;
import me.vik.gravity.util.Util;

import com.badlogic.gdx.graphics.Color;

public class TimePowerUp extends Item {

	public static final float WIDTH = 0.05f;
	public static final float HEIGHT = WIDTH * ((float) Textures.time.getHeight() / (float) Textures.time.getWidth());
	
	public TimePowerUp(float x, float y, Color color, ArrayList<Player> players) {
		super(x, y, players);
		init(x, y, color, players);
	}
	
	public void init(float x, float y, Color color, ArrayList<Player> players) {
		this.x = x;
		this.y = y;
		this.color = color;
		
		if (bounds != null)
			Util.rectPool.recycle(bounds);
		
		bounds = Util.createRect(x, y, WIDTH, HEIGHT);
		
		reset();
	}
	
	public void render(Camera camera) {
		super.render(camera);
		
		batch.begin();
		batch.setColor(color);
		batch.draw(Textures.time, bounds.x, bounds.y, bounds.width, bounds.height);
		batch.end();
	}

	protected void onPickup(Camera camera) {
		Sounds.play(Sounds.time);
		camera.slowDown(0.5f);
	}

}
