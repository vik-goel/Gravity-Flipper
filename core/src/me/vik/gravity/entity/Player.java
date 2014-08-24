package me.vik.gravity.entity;

import java.util.ArrayList;

import me.vik.gravity.entity.gui.MusicButton;
import me.vik.gravity.entity.gui.PauseButton;
import me.vik.gravity.screen.GameScreen;
import me.vik.gravity.util.Sounds;
import me.vik.gravity.util.Textures;
import me.vik.gravity.util.TouchInput;
import me.vik.gravity.util.Util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;

public class Player extends Entity {

	public static final float SIZE = 0.1f;

	public static final Color TOP_PLAYER_COLOR = GameScreen.TOP;
	public static final Color BOTTOM_PLAYER_COLOR = GameScreen.BOTTOM;

	private static ArrayList<Player> players = new ArrayList<Player>();

	private static float gravity = -0.0006f;

	private float yVel;
	private float limit;
	private boolean ceiling;

	private Rectangle bounds;

	public Player(float y, boolean ceiling, Camera camera) {
		super(0, y);
		reset(y, ceiling, camera);
	}

	public void reset(float y, boolean ceiling, Camera camera) {
		super.reset();

		this.x = 0.4f;
		this.y = y;

		this.limit = y;
		this.ceiling = ceiling;

		if (ceiling) {
			color = GameScreen.BOTTOM;
			limit += SIZE;
		} else {
			color = GameScreen.TOP;
		}

		bounds = new Rectangle(x, y, SIZE, SIZE);
		players.add(this);

		yVel = 0;
	}

	public void update(float dt, Camera camera) {
		super.update(dt, camera);

		if (GameScreen.destroyed)
			return;

		integrate(dt, camera);
		boundsCheck(camera);
	}

	private void integrate(float dt, Camera camera) {
		if (ceiling)
			yVel -= gravity;
		else
			yVel += gravity;

		move(camera.getXVel() * dt, yVel * dt, camera);
	}

	private void move(float xAmt, float yAmt, Camera camera) {
		if (xAmt != 0 && yAmt != 0) {
			move(0, yAmt, camera);
			move(xAmt, 0, camera);
			return;
		}

		final float maxStep = 0.01f;

		if (xAmt > maxStep) {
			move(maxStep, 0, camera);
			move(xAmt - maxStep, 0, camera);
			return;
		}

		if (yAmt > maxStep) {
			move(0, maxStep, camera);
			move(0, yAmt - maxStep, camera);
			return;
		}

		if (yAmt < -maxStep) {
			move(0, -maxStep, camera);
			move(0, yAmt + maxStep, camera);
			return;
		}

		x += xAmt;
		y += yAmt;

		boundsCheck(camera);
		checkCollisions();
	}

	private void checkCollisions() {
		ArrayList<Obstacle> obstacles = manager.getObstacles();

		for (int i = 0; i < obstacles.size(); i++) {
			//if ((!ceiling && !obstacles.get(i).isTopObstacle()) || (ceiling && obstacles.get(i).isTopObstacle()))
			//continue;

			if (obstacles.get(i).isCollidingWithPlayer(this))
				remove();
		}
	}

	private void setBoundsToCurrentPos() {
		bounds.x = x;
		bounds.y = y;
	}

	private void boundsCheck(Camera camera) {
		if (ceiling) {
			if (y + SIZE > limit)
				y = limit - SIZE;
		} else {
			y = Math.max(limit, y);
		}

		y = Math.max(GameScreen.LINE_THICKNESS, y);
		y = Math.min(camera.getHeight() - SIZE - GameScreen.LINE_THICKNESS, y);

		setBoundsToCurrentPos();
	}

	public void render(Camera camera) {
		super.render(camera);

		batch.begin();
		batch.setColor(color);
		batch.draw(Textures.player, bounds.x, bounds.y, bounds.width, bounds.height);
		batch.end();
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public void onRemove() {
		manager.addEntity(Util.createRing(bounds.x, bounds.y, false, color.r, color.g, color.b));
		Util.spawnParticles(manager, bounds.x + bounds.width / 2f, bounds.y + bounds.height / 2f, color, 40);
	}

	public static void updateGravity(Camera camera, PauseButton pauseButton, MusicButton musicButton) {
		if (TouchInput.spaceClicked() || (TouchInput.justDown() && !pauseButton.touchedInBounds(camera) && !musicButton.touchedInBounds(camera))) {
			gravity *= -1;

			if (gravity > 0)
				Sounds.play(Sounds.gravityUp);
			else
				Sounds.play(Sounds.gravityDown);

			for (int i = 0; i < players.size(); i++) {
				Player player = players.get(i);

				player.yVel = 0;
			}
		}
	}

	public static void clear() {
		players.clear();

		if (gravity > 0)
			gravity *= -1;
	}

}
