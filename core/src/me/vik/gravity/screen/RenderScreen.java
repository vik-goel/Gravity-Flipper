package me.vik.gravity.screen;

import me.vik.gravity.LD30Game;
import me.vik.gravity.entity.Camera;
import me.vik.gravity.entity.Entity;
import me.vik.gravity.game.EntityManager;
import me.vik.gravity.game.ScreenSwitcher;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;

public abstract class RenderScreen implements Screen {

	private static final Color DEFAULT_BACKGROUND_COLOR = GameScreen.BOTTOM;
	
	protected LD30Game game;
	protected EntityManager manager = new EntityManager();
	protected Camera camera = new Camera();
	protected static Color clearColor = DEFAULT_BACKGROUND_COLOR;
	
	public RenderScreen(LD30Game game) {
		this.game = game;
	}
	
	public void render(float delta) {
		Gdx.gl.glClearColor(clearColor.r, clearColor.g, clearColor.b, clearColor.a);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		float dt = Gdx.graphics.getRawDeltaTime() * 60f;
		dt = Math.min(5, dt);
		
		camera.project(Entity.batch);
		camera.project(Entity.sr);
		
		updateScreen(dt);
		ScreenSwitcher.getInstance(game).update(dt);
		
		renderScreen();
		ScreenSwitcher.getInstance(game).render(camera);
	}
	
	public abstract void updateScreen(float dt);
	public abstract void renderScreen();

	public void resize(int width, int height) {
		
	}

	public void show() {
		camera.reset();
	}

	public void hide() {
		
	}

	public void pause() {
		
	}

	public void resume() {
		
	}

	public void dispose() {
		
	}

	public void onBackPressed() {
		
	}

}
