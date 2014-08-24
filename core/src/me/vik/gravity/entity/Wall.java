package me.vik.gravity.entity;

import java.util.Random;

import me.vik.gravity.screen.GameScreen;
import me.vik.gravity.util.Util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;

public class Wall extends Obstacle {

	private static Random random = new Random();

	private Rectangle bounds;

	private boolean moving;
	private float maxY, minY;
	private float velY = 0;
	private boolean top;

	public Wall(float x, float y, float width, float height, Color color) {
		super(x, y);
		init(x, y, width, height, color);
	}

	public void init(float x, float y, float width, float height, Color color) {
		this.x = x;
		this.y = y;

		if (bounds != null)
			Util.rectPool.recycle(bounds);

		bounds = Util.createRect(x, y, width, height);
		this.color = color;

		if (y < 0.5f)
			top = false;
		else
			top = true;

		moving = false;
		
		reset();
	}

	public Wall(float x, float y, float width, float height, Color color, float maxY, float minY) {
		super(x, y);
		init(x, y, width, height, color, maxY, minY);
	}

	public void init(float x, float y, float width, float height, Color color, float maxY, float minY) {
		this.x = x;
		this.y = y;

		if (bounds != null)
			Util.rectPool.recycle(bounds);

		bounds = Util.createRect(x, y, width, height);
		this.color = color;
		this.maxY = maxY - height;
		this.minY = minY;
		velY = 0.002f + random.nextFloat() * 0.002f;
		moving = true;

		if (y == GameScreen.LINE_THICKNESS)
			top = false;
		
		reset();
	}

	public void update(float dt, Camera camera) {
		super.update(dt, camera);

		if (!moving || GameScreen.destroyed)
			return;

		y += velY * camera.getSpeed();

		if (y < minY) {
			y = minY;
			velY *= -1;
		}

		else if (y > maxY) {
			y = maxY;
			velY *= -1;
		}

		bounds.y = y;
		
		//body.setTransform(bounds.x + bounds.width / 2, bounds.y + bounds.height / 2, 0);
	}

	public void render(Camera camera) {
		super.render(camera);

		sr.setColor(color);
		sr.rect(bounds.x, bounds.y, bounds.width, bounds.height);
	}

	protected boolean isOffscreen(Camera camera) {
		return bounds.x + bounds.width < camera.x;
	}

	protected boolean isCollidingWithPlayer(Player player) {
		return bounds.overlaps(player.getBounds());
	}

	protected boolean isTopObstacle() {
		return top;
	}

	public boolean contains(float x, float y) {
		return bounds.contains(x, y);
	}

	public boolean isCollidingWithRect(Rectangle r) {
		return r.overlaps(this.bounds);
	}
	
	public boolean usesShapeRendererExclusively() {
		return true;
	}

}
