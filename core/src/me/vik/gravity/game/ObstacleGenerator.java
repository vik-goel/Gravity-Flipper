package me.vik.gravity.game;

import java.util.Random;

import me.vik.gravity.entity.Camera;
import me.vik.gravity.entity.Wall;
import me.vik.gravity.screen.GameScreen;
import me.vik.gravity.util.Util;

import com.badlogic.gdx.graphics.Color;

public class ObstacleGenerator {

	private static final float MIN_COMBINED_SPAWN_DELAY = 30f;
	private static final float MIN_SPAWN_DELAY = 55f;
	private static final float MOVING_SPAWN_DELAY = 45f;

	private static Random random = new Random();

	private EntityManager manager;
	private float topSpawnDelay = 0, bottomSpawnDelay = 0, movingSpawnDelay = 0, combinedSpawnDelay = 0;

	public ObstacleGenerator(EntityManager manager) {
		this.manager = manager;
	}
	
	public void reset() {
		topSpawnDelay = MIN_SPAWN_DELAY;
		bottomSpawnDelay = MIN_SPAWN_DELAY;
		movingSpawnDelay = MOVING_SPAWN_DELAY;
		combinedSpawnDelay = MIN_COMBINED_SPAWN_DELAY;
	}

	public void update(float dt, Camera camera) {
		topSpawnDelay += dt * camera.getSpeed();
		bottomSpawnDelay += dt * camera.getSpeed();
		movingSpawnDelay += dt * camera.getSpeed();
		combinedSpawnDelay += dt * camera.getSpeed();

		if (random.nextInt(20) == 0) {
			float x = camera.x + camera.getWidth();

			float width = 0.04f;
			float height = 0.06f + random.nextFloat() * 0.08f;

			boolean top = random.nextBoolean();

			float maxOffset = (camera.getHeight() - GameScreen.LINE_THICKNESS * 2 - GameScreen.MIDDLE_LINE_THICKNESS) / 2f - height;
			float heightPercentage = random.nextFloat() < 0.35f ? 1 : (random.nextFloat() * maxOffset);
			float offsetAmt = maxOffset * heightPercentage;//random.nextFloat() * maxOffset;//(random.nextBoolean() ? 1 : 0);//random.nextFloat() * maxOffset;

			float y = 0;

			if (top) {
				y = camera.getHeight() - height - GameScreen.LINE_THICKNESS - offsetAmt;

				if (topSpawnDelay < MIN_SPAWN_DELAY)
					return;

				topSpawnDelay = 0;
			}
			if (!top) {
				y = GameScreen.LINE_THICKNESS + offsetAmt;

				if (bottomSpawnDelay < MIN_SPAWN_DELAY)
					return;

				bottomSpawnDelay = 0;
			}
			
			if (combinedSpawnDelay < MIN_COMBINED_SPAWN_DELAY)
				return;
			
			combinedSpawnDelay = 0;

			Color color = top ? GameScreen.TOP : GameScreen.BOTTOM;

			Wall wall;

			if (random.nextFloat() > 0.65f && movingSpawnDelay > MOVING_SPAWN_DELAY) {
				wall = Util.createMovingWall(x, y, width, height, color, top);
				movingSpawnDelay = 0;
			} else {
				wall = Util.createWall(x, y, width, height, color);
			}

			manager.addEntity(wall);
		}
	}
}
