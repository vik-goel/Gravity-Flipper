package me.vik.gravity.entity.gui;

import me.vik.gravity.entity.Camera;
import me.vik.gravity.screen.GameScreen;
import me.vik.gravity.util.Textures;
import me.vik.gravity.util.Util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class ContinueButton extends Button {

	private PauseButton pauseButton;

	private boolean clicked = false;
	
	public ContinueButton(PauseButton pauseButton) {
		super((Util.getAspectRatio() - PlayButton.getWidth()) / 2f, 0.4f, PlayButton.getWidth(), 0.2f, Textures.continueButton);
		this.pauseButton = pauseButton;
	}

	public void onClick() {
		clicked = true;
	}
	
	public void update(float dt, Camera camera) {
		if (!pauseButton.isPaused())
			return;
		
		if (clicked && scale >= 1) {
			pauseButton.unpause();
			clicked = false;
		}
		
		super.update(dt, camera);
	}

	public void render(Camera camera) {
		if (!pauseButton.isPaused())
			return;
		
		Gdx.gl20.glEnable(GL20.GL_BLEND);
		Gdx.gl20.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		
		sr.begin(ShapeType.Filled);
		sr.setColor(GameScreen.BOTTOM.r, GameScreen.BOTTOM.g, GameScreen.BOTTOM.b, 0.6f);
		sr.rect(camera.x, 0, camera.getWidth(), camera.getHeight());
		sr.end();
		
		Gdx.gl20.glDisable(GL20.GL_BLEND);
		
		super.render(camera);
	}

}
