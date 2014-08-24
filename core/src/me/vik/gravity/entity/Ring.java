package me.vik.gravity.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

import me.vik.gravity.util.Textures;

public class Ring extends Entity {
	
	private float radius;
	private float radiusGrow;
	private float growSpeed;
	
	private float alpha;
	private float alphaDecay;
	private float alphaDecayAcceleration;
	
	private float r, g, b;

	public Ring(float x, float y, boolean small, float r, float g, float b) {
		super(x, y);
		init(x, y, small, r, g, b);
	}
	
	public void init(float x, float y, boolean small, float r, float g, float b) {
		this.x = x;
		this.y = y;
		
		radiusGrow = 0;
		alpha = 1f;
		alphaDecay = 0f;
		growSpeed = 0.0015f * 20f;
		
		radius = small ? 0.05f : 0.1f;
		alphaDecayAcceleration = small ? 0.004f : 0.001f;
		
		this.r = r;
		this.g = g;
		this.b = b;
		
		reset();
	}

	public void update(float dt, Camera camera) {
		super.update(dt, camera);
		radiusGrow += growSpeed * dt * radius;
		
		alphaDecay += alphaDecayAcceleration;
		alpha -= alphaDecay * dt;
		
		if (alpha <= 0)
			remove();
	}

	public void render(Camera camera) {
		super.render(camera);
		
		if (isRemoved())
			return;

		Gdx.gl20.glEnable(GL20.GL_BLEND);
		Gdx.gl20.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		
		batch.begin();
		batch.setColor(r, g, b, alpha);
		batch.draw(Textures.ring, x - radiusGrow / 2, y - radiusGrow / 2, radius + radiusGrow, radius + radiusGrow);
		batch.end();
		
		Gdx.gl20.glDisable(GL20.GL_BLEND);
	}
	
}
