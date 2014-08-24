package me.vik.gravity.game;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

import me.vik.gravity.entity.Camera;
import me.vik.gravity.entity.Coin;
import me.vik.gravity.entity.Entity;
import me.vik.gravity.entity.Item;
import me.vik.gravity.entity.Obstacle;
import me.vik.gravity.entity.Particle;
import me.vik.gravity.entity.Player;
import me.vik.gravity.entity.Ring;
import me.vik.gravity.entity.TimePowerUp;
import me.vik.gravity.entity.Wall;
import me.vik.gravity.entity.gui.ContinueButton;
import me.vik.gravity.util.Util;

public class EntityManager {

	private ArrayList<Entity> entities = new ArrayList<Entity>(300);
	private ArrayList<Entity> srExclusive = new ArrayList<Entity>(200);
	private ArrayList<Entity> notSRExclusive = new ArrayList<Entity>(200);
	private ArrayList<Obstacle> obstacles = new ArrayList<Obstacle>(50);
	private ArrayList<Player> players = new ArrayList<Player>(3);
	private ArrayList<Item> items = new ArrayList<Item>(50);
	private ContinueButton continueButton;

	public void addEntity(Entity e) {
		if (e instanceof Player)
			players.add((Player) e);
		else if (e instanceof ContinueButton)
			continueButton = (ContinueButton) e;
		else {
			entities.add(e);

			if (e.usesShapeRendererExclusively()) {
				srExclusive.add(e);
			} else {
				notSRExclusive.add(e);
			}
		}

		if (e instanceof Obstacle)
			obstacles.add((Obstacle) e);

		if (e instanceof Item)
			items.add((Item) e);

		e.setManager(this);
	}

	public void update(float dt, Camera camera) {
		for (int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);

			if (e.isRemoved()) {
				dispose(e);

				if (e instanceof Obstacle)
					obstacles.remove((Obstacle) e);

				if (e instanceof Item)
					items.remove((Item) e);

				entities.remove(i--);
				srExclusive.remove(e);
				notSRExclusive.remove(e);

				continue;
			}

			e.update(dt, camera);

			if (continueButton != null)
				continueButton.update(dt, camera);
		}

		for (int i = 0; i < players.size(); i++) {
			Player player = players.get(i);

			if (player.isRemoved()) {
				players.remove(i--);
				continue;
			}

			player.update(dt, camera);
		}
	}

	private void dispose(Entity e) {
		if (e instanceof Wall)
			Util.wallPool.recycle((Wall) e);

		if (e instanceof Coin)
			Util.coinPool.recycle((Coin) e);

		if (e instanceof Particle)
			Util.particlePool.recycle((Particle) e);

		if (e instanceof Ring)
			Util.ringPool.recycle((Ring) e);

		if (e instanceof TimePowerUp)
			Util.timePool.recycle((TimePowerUp) e);
	}

	public void render(Camera camera) {
		Entity.sr.begin(ShapeType.Filled);
		for (int i = 0; i < srExclusive.size(); i++) {
			srExclusive.get(i).render(camera);
		}
		Entity.sr.end();
		
		for (int i = 0; i < notSRExclusive.size(); i++)
			notSRExclusive.get(i).render(camera);

		for (int i = 0; i < players.size(); i++)
			players.get(i).render(camera);
	}

	public void clear() {
		entities.clear();
		srExclusive.clear();
		notSRExclusive.clear();
		obstacles.clear();
		players.clear();
		items.clear();
		continueButton = null;
	}

	public ArrayList<Obstacle> getObstacles() {
		return obstacles;
	}

	public ArrayList<Item> getItems() {
		return items;
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}
}
