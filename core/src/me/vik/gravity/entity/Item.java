package me.vik.gravity.entity;

import java.util.ArrayList;

import me.vik.gravity.game.EntityManager;
import me.vik.gravity.util.Util;

import com.badlogic.gdx.math.Rectangle;

public abstract class Item extends Entity {

	protected Rectangle bounds;
	protected ArrayList<Player> players;
	
	public Item(float x, float y, ArrayList<Player> players) {
		super(x, y);
		this.players = players;
	}
	
	public void update(float dt, Camera camera) {
		super.update(dt, camera);

		if (bounds.x + bounds.width < camera.x)
			remove();

		for (int i = 0; i < players.size(); i++) {
			if (players.get(i).getBounds().overlaps(bounds)) {
				remove();
				manager.addEntity(Util.createRing(bounds.x, bounds.y, true, color.r, color.g, color.b));
				onPickup(camera);
				return;
			}
		}
	}
	
	public void setManager(EntityManager manager) {
		super.setManager(manager);
		
		ArrayList<Obstacle> obstacles = manager.getObstacles();
		
		for (int i = 0; i < obstacles.size(); i++) {
			if (obstacles.get(i).isCollidingWithRect(bounds)) {
				remove();
				return;
			}
		}
	}
	
	public Rectangle getBounds() {
		return bounds;
	}

	protected abstract void onPickup(Camera camera);

}
