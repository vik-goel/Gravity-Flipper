package me.vik.gravity.game;

import me.vik.gravity.LD30Game;
import me.vik.gravity.screen.RenderScreen;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

public class BackInputProcessor implements InputProcessor {

	private LD30Game game;

	public BackInputProcessor(LD30Game game) {
		this.game = game;
	}

	public boolean keyDown(int keycode) {
		if (keycode == Input.Keys.BACK) {
			((RenderScreen)game.getScreen()).onBackPressed();
		}

		return false;
	}

	public boolean keyUp(int keycode) {
		return false;
	}

	public boolean keyTyped(char character) {
		return false;
	}

	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	public boolean scrolled(int amount) {
		return false;
	}

}
