package me.vik.gravity.entity;

import me.vik.gravity.screen.GameScreen;
import me.vik.gravity.util.Util;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Camera extends Entity {

	private static final float ACCELERATION = 0.000416f;
	private static final float MAX_SPEED = 2.5f;
	
	private OrthographicCamera camera;
	private float xVel = 0.005f;
	private float speed;
	
	private float slowFactor = 1;
	private float slowTime = 120f, slowTimeCounter = 0f;
	
	public Camera() {
		super(0, 0);
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Util.getAspectRatio(), 1);

		reset();
	}
	
	public void update(float dt, Camera camera) {
		super.update(dt, camera);
		
		if (GameScreen.destroyed)
			return;
		
		speed += ACCELERATION;
		
		if (speed > MAX_SPEED)
			speed = MAX_SPEED;
		
		slowTimeCounter += dt;
		
		if (slowTimeCounter > slowTime)
			slowFactor = Math.min(slowFactor + 0.0015f, 1f);
		
		move(getXVel() * dt, 0);
	}

	public void move(float xAmt, float yAmt) {
		x += xAmt;
		y += yAmt;
		
		camera.position.x += xAmt;
		camera.position.y += yAmt;
		
		camera.update();
	}
	
	public void reset() {
		camera.position.x = getWidth() / 2;
		camera.position.y = getHeight() / 2;
		
		x = y = 0;
		speed = 1;
		
		slowFactor = 1;
		slowTimeCounter = 0;
		
		camera.update();
	}

	public float getWidth() {
		return camera.viewportWidth;
	}
	
	public float getHeight() {
		return camera.viewportHeight;
	}

	public float getXVel() {
		return speed * xVel * slowFactor;
	}
	
	public float getSpeed() {
		return speed * slowFactor;
	}
	
	public float getSlowFactor() {
		return slowFactor;
	}
	
	public float getUnslowedSpeed() {
		return speed;
	}
	
	public void project(SpriteBatch batch) {
		batch.setProjectionMatrix(camera.combined);
	}

	public void project(ShapeRenderer sr) {
		sr.setProjectionMatrix(camera.combined);
	}

	public void slowDown(float slowFactor) {
		this.slowFactor = slowFactor;//1 - (1 - this.slowFactor) - (1 - slowFactor);
		slowTimeCounter = 0;
	}

}
