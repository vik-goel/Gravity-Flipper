package me.vik.gravity.entity;

import java.util.Random;

import com.badlogic.gdx.graphics.Color;

public class Particle extends Entity {

	private static final Random random = new Random();
	
	private float velX, velY;
	private float width, height;
	
	public Particle(float x, float y, Color color) {
		super(x, y);
		init(x, y, color);
	}
	
	public void init(float x, float y, Color color) {
		this.x = x;
		this.y = y;
		this.color = color;

		reset();
		
		double speed = random.nextDouble() * 0.005 + 0.003;
		
		double dir = random.nextDouble() * Math.PI * 2d;
		velX = (float)(Math.cos(dir) * speed);
		velY = (float)(Math.sin(dir) * speed);
		
		width = 0.025f;
		height = 0.025f;
	}

	public void update(float dt, Camera camera) {
		super.update(dt, camera);
		
		x += velX;
		y += velY;
	}

	public void render(Camera camera) {
		super.render(camera);
		
		sr.setColor(color);
		sr.rect(x, y, width, height);
	}
	
	public boolean usesShapeRendererExclusively() {
		return true;
	}

}
