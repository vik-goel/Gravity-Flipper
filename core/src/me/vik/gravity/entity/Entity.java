package me.vik.gravity.entity;

import me.vik.gravity.game.EntityManager;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Entity {

	public static SpriteBatch batch = new SpriteBatch();
	public static ShapeRenderer sr = new ShapeRenderer();
	
	public float x, y;
	public Color color = Color.WHITE;
	
	private boolean removed = false;
	protected EntityManager manager;


	public Entity(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public void update(float dt, Camera camera) {
		
	}
	
	public void render(Camera camera) {
		
	}
	
	public void remove() {
		removed = true;
	}
	
	protected void reset() {
		removed = false;
	}
	
	public boolean isRemoved() {
		return removed;
	}
	
	public void setManager(EntityManager manager) {
		this.manager = manager;
	}
	
	public boolean usesShapeRendererExclusively() {
		return false;
	}

}
