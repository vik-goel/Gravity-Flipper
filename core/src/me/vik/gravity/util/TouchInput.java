package me.vik.gravity.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class TouchInput {

	private static boolean spaceWasDown, spaceClicked;
	
	public static boolean isDown() {
		return Gdx.input.isTouched() || Gdx.input.isButtonPressed(0);
	}
	
	public static boolean justDown() {
		return (Gdx.input.justTouched() && isDown());
	}
	
	public static float getX() {
		return (float)Gdx.input.getX() / (float)Gdx.graphics.getHeight();
	}
	
	public static float getY() {
		return 1f - ((float)Gdx.input.getY() / (float)Gdx.graphics.getHeight());
	}
	
	public static boolean spaceDown() {
		return Gdx.input.isKeyPressed(Input.Keys.SPACE);
	}
	
	public static boolean spaceClicked() {
		return spaceClicked;
	}
	
	public static void update() {
		if (!spaceWasDown && spaceDown())
			spaceClicked = true;
		else spaceClicked = false;
		
		spaceWasDown = spaceDown();
	}
}
