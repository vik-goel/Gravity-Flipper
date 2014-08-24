package me.vik.gravity.game;

import me.vik.gravity.LD30Game;
import me.vik.gravity.entity.Camera;
import me.vik.gravity.entity.Entity;
import me.vik.gravity.screen.ScreenType;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class ScreenSwitcher {

	private static ScreenSwitcher instance = new ScreenSwitcher();
	private static final float ACCELERATION = 0.001f;

	private static LD30Game game;

	private boolean active = false;
	private ScreenType dstScreen;
	private float height;
	private float speed;
	private boolean increasing;

	public static ScreenSwitcher getInstance(LD30Game game) {
		ScreenSwitcher.game = game;
		return instance;
	}

	public void update(float dt) {
		if (!active)
			return;

		speed += ACCELERATION;

		if (increasing) {
			height += speed;

			if (height > 0.5f) {
				increasing = false;
				speed = 0;
				game.switchScreen(dstScreen);
			}

		} else {
			height -= speed;

			if (height < 0) {
				active = false;
			}
		}
	}

	public void render(Camera camera) {
		if (!active)
			return;

		ShapeRenderer sr = Entity.sr;

		sr.begin(ShapeType.Filled);
		sr.setColor(Color.BLACK);
		sr.rect(camera.x, 0f, camera.getWidth(), height);
		sr.rect(camera.x, camera.getHeight() - height, camera.getWidth(), height);
		sr.end();
	}

	public void startSwitch(ScreenType dstScreen) {
		if (active)
			return;

		this.dstScreen = dstScreen;
		height = 0;
		speed = 0;
		increasing = true;
		active = true;
	}

}
