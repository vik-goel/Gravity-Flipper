package me.vik.gravity.game;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.graphics.Color;

import me.vik.gravity.entity.Camera;
import me.vik.gravity.entity.Coin;
import me.vik.gravity.entity.Obstacle;
import me.vik.gravity.entity.TimePowerUp;
import me.vik.gravity.screen.GameScreen;
import me.vik.gravity.util.Util;

public class ItemGenerator {

	private static Random random = new Random();

	private EntityManager manager;

	private float spawnDelay = 120, spawnDelayCounter = spawnDelay;
	//private Color itemLight = Util.getColor(224, 194, 148);
	//private Color itemDark = Util.getColor(166, 118, 83);
	private boolean lastSpawnWasTime = false;
	
	public ItemGenerator(EntityManager manager) {
		this.manager = manager;
	}
	
	public void reset() {
		spawnDelayCounter = spawnDelay;
	}

	public void update(float dt, Camera camera) {
		spawnDelayCounter += dt * camera.getSpeed();

		if (spawnDelayCounter < spawnDelay)
			return;

		if (random.nextInt(25) == 0) {
			spawnItem(dt, camera);
			spawnDelayCounter = 0;
		}
	}

	private void spawnItem(float dt, Camera camera) {
		boolean isCoin = random.nextInt(8) != 0;
		
		if (lastSpawnWasTime)
			isCoin = true;
			
		float x = camera.getWidth() * 1.25f + camera.x;
		float y = getItemY(isCoin);

		if (!validPos(x, y))
			return;

		Color color = y < 0.5f ? GameScreen.BOTTOM : GameScreen.TOP;

		//Color color = y < 0.5f ? itemLight : itemDark;

		if (isCoin) {
			manager.addEntity(Util.createCoin(x, y, color, manager.getPlayers()));
		} else {
			manager.addEntity(Util.createTimePowerUp(x, y, color, manager.getPlayers()));
			lastSpawnWasTime = true;
		}
	}

	private boolean validPos(float x, float y) {
		ArrayList<Obstacle> obstacles = manager.getObstacles();

		for (int i = 0; i < obstacles.size(); i++) {
			if (obstacles.get(i).contains(x, y)) {
				return false;
			}
		}

		return true;
	}

	private float getItemY(boolean isCoin) {
		boolean top = random.nextBoolean();

		float minY = 0, maxY = 0;

		if (isCoin) {
			if (top) {
				minY = 0.5f + GameScreen.MIDDLE_LINE_THICKNESS / 2f + Coin.RADIUS;
				maxY = 1f - GameScreen.LINE_THICKNESS - Coin.RADIUS;
			} else {
				minY = GameScreen.LINE_THICKNESS + Coin.RADIUS;
				maxY = 0.5f - GameScreen.MIDDLE_LINE_THICKNESS / 2f - Coin.RADIUS;
			}
		} else {
			if (top) {
				minY = 0.5f + GameScreen.MIDDLE_LINE_THICKNESS / 2f + Coin.RADIUS;
				maxY = 1f - GameScreen.LINE_THICKNESS - TimePowerUp.HEIGHT;
			} else {
				minY = GameScreen.LINE_THICKNESS;
				maxY = 0.5f - GameScreen.MIDDLE_LINE_THICKNESS / 2f - TimePowerUp.HEIGHT;
			}
		}

		return random.nextFloat() * (maxY - minY) + minY;
	}

}
