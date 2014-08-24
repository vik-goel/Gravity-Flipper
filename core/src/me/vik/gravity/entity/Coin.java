package me.vik.gravity.entity;

import java.util.ArrayList;

import me.vik.gravity.game.ScoreKeeper;
import me.vik.gravity.util.Sounds;
import me.vik.gravity.util.Util;

import com.badlogic.gdx.graphics.Color;

public class Coin extends Item {

	public static final float RADIUS = 0.025f;
	private static final int CIRCLE_SEGEMENTS = 30;

	public Coin(float x, float y, Color color, ArrayList<Player> players) {
		super(x, y, players);
		init(x, y, color, players);
	}
	
	public void init(float x, float y, Color color, ArrayList<Player> players) {
		this.x = x;
		this.y = y;
		this.players = players;
		
		this.color = color;
		
		reset();
		
		if (bounds != null)
			Util.rectPool.recycle(bounds);
		
		bounds = Util.createRect(x - RADIUS, y - RADIUS, RADIUS * 2, RADIUS * 2);
	}

	public void render(Camera camera) {
		super.render(camera);

		if (isRemoved())
			return;
		
		sr.setColor(color);
		sr.circle(x, y, RADIUS, CIRCLE_SEGEMENTS);
	}

	protected void onPickup(Camera camera) {
		ScoreKeeper.getInstance().addCoin();
		Sounds.play(Sounds.coin);
	}
	
	public boolean usesShapeRendererExclusively() {
		return true;
	}

}
