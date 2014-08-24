package me.vik.gravity.entity;

import java.util.ArrayList;

import me.vik.gravity.game.EntityManager;

import com.badlogic.gdx.math.Rectangle;

public abstract class Obstacle extends Entity {

	public Obstacle(float x, float y) {
		super(x, y);
	}

	public void update(float dt, Camera camera) {
		super.update(dt, camera);
		
		if (isOffscreen(camera)) {
			remove();
			return;
		}
	}
	
	public void setManager(EntityManager manager) {
		super.setManager(manager);
		
		ArrayList<Item> items = manager.getItems();
		
		for (int i = 0; i < items.size(); i++) {
			if (isCollidingWithRect(items.get(i).getBounds())) {
				remove();
				return;
			}
		}
	}
	
	
	protected abstract boolean isOffscreen(Camera camera);
	protected abstract boolean isCollidingWithPlayer(Player player);
	protected abstract boolean isTopObstacle();
	public abstract boolean contains(float x, float y);
	public abstract boolean isCollidingWithRect(Rectangle bounds);
}
