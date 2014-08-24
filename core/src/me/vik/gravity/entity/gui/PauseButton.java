package me.vik.gravity.entity.gui;

import me.vik.gravity.entity.Camera;
import me.vik.gravity.screen.GameScreen;
import me.vik.gravity.util.Textures;
import me.vik.gravity.util.TouchInput;
import me.vik.gravity.util.Util;

import com.badlogic.gdx.math.Rectangle;

public class PauseButton extends Button {

	private boolean paused = false;
	private Rectangle collisionBounds;
	
	public PauseButton(float y, float size) {
		super(GameScreen.LINE_THICKNESS * 0.25f, y, size, size, Textures.pauseButton);
		collisionBounds = Util.createRect(bounds.x, bounds.y - bounds.height * 2, bounds.width * 4, bounds.height * 3);
	}

	public void onClick() {
		if (!GameScreen.destroyed)
			paused = !paused;
	}

	public boolean isPaused() {
		return paused;
	}

	public void unpause() {
		paused = false;
	}
	
	public void update(float dt, Camera camera) {
		collisionBounds.x = camera.x + origX;
		super.update(dt, camera);
	}

	public boolean touchedInBounds(Camera camera) {
		if (Util.onMobile())
			return collisionBounds.contains(TouchInput.getX() + camera.x, TouchInput.getY()) && TouchInput.isDown();
		
		return super.touchedInBounds(camera);
	}

}
