package me.vik.gravity.game;

import java.util.Random;

import me.vik.gravity.entity.Camera;

public class CameraShaker {

	private static final float INITIAL_MAGNITUDE = 0.02f;
	private static final float MIN_MAGNITUDE = 0.002f;
	private static final float ENERGY_LOSS = 0.8f;
	
	private static CameraShaker instance = new CameraShaker();

	private Random random = new Random();
	
	private Camera camera;
	private boolean shaking = false;
	private float xOrigin, yOrigin;
	private float velX, velY;
	private float shakeTime;
	private float shakeMagnitude;
	
	public static CameraShaker getInstance() {
		return instance;
	}
	
	public void shake(Camera camera) {
		if (shaking)
			return;
		
		this.shaking = true;
		this.camera = camera;
		this.xOrigin = camera.x;
		this.yOrigin = camera.y;
		this.shakeMagnitude = INITIAL_MAGNITUDE;
		
		initShake();
	}
	
	public void update(float dt) {
		if (!shaking)
			return;
		
		shakeTime -= dt;
		
		if (shakeTime < 0) {
			initShake();
			return;
		}
		
		camera.move(velX, velY);
	}
	
	private void initShake() {
		shakeMagnitude *= ENERGY_LOSS;
		
		camera.move(xOrigin - camera.x, yOrigin - camera.y);
		
		if (shakeMagnitude < MIN_MAGNITUDE) {
			shaking = false;
			return;
		}
		
		double dir = random.nextDouble() * Math.PI * 2d;
		velX = (float) (Math.cos(dir) * shakeMagnitude);
		velY = (float) (Math.sin(dir) * shakeMagnitude);
		
		shakeTime = 1f + random.nextFloat() * 2.5f;
	}

	public void reset() {
		shaking = false;
	}
}
