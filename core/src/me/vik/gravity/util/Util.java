package me.vik.gravity.util;

import java.util.ArrayList;

import me.vik.gravity.entity.Coin;
import me.vik.gravity.entity.Particle;
import me.vik.gravity.entity.Player;
import me.vik.gravity.entity.Ring;
import me.vik.gravity.entity.TimePowerUp;
import me.vik.gravity.entity.Wall;
import me.vik.gravity.game.EntityManager;
import me.vik.gravity.screen.GameScreen;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;

public class Util {

	public static ObjectPool<Rectangle> rectPool = new ObjectPool<Rectangle>();
	public static ObjectPool<Wall> wallPool = new ObjectPool<Wall>();
	public static ObjectPool<Coin> coinPool = new ObjectPool<Coin>();
	public static ObjectPool<Particle> particlePool = new ObjectPool<Particle>();
	public static ObjectPool<Ring> ringPool = new ObjectPool<Ring>();
	public static ObjectPool<TimePowerUp> timePool = new ObjectPool<TimePowerUp>();

	public static float getAspectRatio() {
		return (float) Gdx.graphics.getWidth() / (float) Gdx.graphics.getHeight();
	}

	public static Color getColor(int r, int g, int b) {
		return new Color(r / 255f, g / 255f, b / 255f, 1f);
	}

	public static Rectangle createRect(float x, float y, float width, float height) {
		Rectangle rect = rectPool.get();

		if (rect == null)
			return new Rectangle(x, y, width, height);

		rect.set(x, y, width, height);

		return rect;
	}

	public static void spawnParticles(EntityManager manager, float x, float y, Color color, int numParticles) {
		for (int i = 0; i < numParticles; i++)
			manager.addEntity(createParticle(x, y, color));
	}

	public static Wall createWall(float x, float y, float width, float height, Color color) {
		Wall wall = wallPool.get();

		if (wall == null)
			wall = new Wall(x, y, width, height, color);
		else
			wall.init(x, y, width, height, color);

		return wall;
	}

	public static Wall createMovingWall(float x, float y, float width, float height, Color color, boolean top) {
		float maxY = 0, minY = 0;

		if (top) {
			maxY = 1f - GameScreen.LINE_THICKNESS;
			minY = 0.5f + GameScreen.MIDDLE_LINE_THICKNESS / 2f;
		} else {
			minY = GameScreen.LINE_THICKNESS;
			maxY = 0.5f - GameScreen.MIDDLE_LINE_THICKNESS / 2f;
		}

		Wall wall = wallPool.get();

		if (wall == null)
			wall = new Wall(x, y, width, height, color, maxY, minY);
		else
			wall.init(x, y, width, height, color, maxY, minY);

		return wall;
	}

	public static Coin createCoin(float x, float y, Color color, ArrayList<Player> players) {
		Coin coin = coinPool.get();

		if (coin == null)
			coin = new Coin(x, y, color, players);
		else
			coin.init(x, y, color, players);

		return coin;
	}
	
	public static Particle createParticle(float x, float y, Color color) {
		Particle particle = particlePool.get();

		if (particle == null)
			particle = new Particle(x, y, color);
		else
			particle.init(x, y, color);

		return particle;
	}
	
	public static Ring createRing(float x, float y, boolean small, float r, float g, float b) {
		Ring ring = ringPool.get();

		if (ring == null)
			ring = new Ring(x, y, small, r, g, b);
		else
			ring.init(x, y, small, r, g, b);

		return ring;
	}
	
	public static TimePowerUp createTimePowerUp(float x, float y, Color color, ArrayList<Player> players) {
		TimePowerUp time = timePool.get();

		if (time == null)
			time = new TimePowerUp(x, y, color, players);
		else
			time.init(x, y, color, players);

		return time;
	}

	public static boolean onMobile() {
		return Gdx.app.getType() == ApplicationType.Android || Gdx.app.getType() == ApplicationType.iOS;
	}

}
