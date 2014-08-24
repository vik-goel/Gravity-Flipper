package me.vik.gravity.screen;

import me.vik.gravity.LD30Game;
import me.vik.gravity.entity.Entity;
import me.vik.gravity.entity.Player;
import me.vik.gravity.entity.gui.ContinueButton;
import me.vik.gravity.entity.gui.MusicButton;
import me.vik.gravity.entity.gui.PauseButton;
import me.vik.gravity.game.CameraShaker;
import me.vik.gravity.game.ItemGenerator;
import me.vik.gravity.game.ObstacleGenerator;
import me.vik.gravity.game.ScoreKeeper;
import me.vik.gravity.game.ScreenSwitcher;
import me.vik.gravity.util.Fonts;
import me.vik.gravity.util.Sounds;
import me.vik.gravity.util.TouchInput;
import me.vik.gravity.util.Util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class GameScreen extends RenderScreen {

	public static final float LINE_THICKNESS = 0.04f;

	public static final Color TOP = Util.getColor(135, 109, 95);
	public static final Color BOTTOM = Util.getColor(235, 220, 195);

	private ObstacleGenerator obstacleGenerator;
	private ItemGenerator itemGenerator;
	private Player topPlayer, bottomPlayer;

	private static final Color MIDDLE_LINE_COLOR = Util.getColor(178, 161, 161);
	public static final float MIDDLE_LINE_THICKNESS = 2f / (float) Gdx.graphics.getHeight();

	private static final float MAX_SWITCH_DELAY = 10f;
	private float switchDelay;

	public static boolean destroyed = false;
	private boolean started = false;

	private PauseButton pauseButton = new PauseButton(1 - LINE_THICKNESS, LINE_THICKNESS);
	private ContinueButton continueButton = new ContinueButton(pauseButton);
	private MusicButton musicButton = new MusicButton(pauseButton.x + LINE_THICKNESS * 1.25f, pauseButton.y, LINE_THICKNESS);

	private String instruction1 = Util.onMobile() ? "Tap to reverse gravity" : "Click or press space to reverse gravity";
	private String instruction2 = "Try not to die";

	public GameScreen(LD30Game game) {
		super(game);
		obstacleGenerator = new ObstacleGenerator(manager);
		itemGenerator = new ItemGenerator(manager);
	}

	public void show() {
		super.show();

		Player.clear();

		if (topPlayer == null) {
			topPlayer = new Player(0.5f + MIDDLE_LINE_THICKNESS / 2f, false, camera);
		} else {
			topPlayer.reset(0.5f + MIDDLE_LINE_THICKNESS / 2f, false, camera);
		}

		if (bottomPlayer == null) {
			bottomPlayer = new Player(0.5f - Player.SIZE - MIDDLE_LINE_THICKNESS / 2f, true, camera);
		} else {
			bottomPlayer.reset(0.5f - Player.SIZE - MIDDLE_LINE_THICKNESS / 2f, true, camera);
		}

		manager.clear();
		manager.addEntity(camera);
		manager.addEntity(topPlayer);
		manager.addEntity(bottomPlayer);
		manager.addEntity(pauseButton);
		manager.addEntity(continueButton);

		if (!Util.onMobile())
			manager.addEntity(musicButton);

		switchDelay = MAX_SWITCH_DELAY;
		destroyed = false;
		started = false;

		ScoreKeeper.getInstance().reset();
		CameraShaker.getInstance().reset();

		obstacleGenerator.reset();
		itemGenerator.reset();
	}

	public void updateScreen(float dt) {
		TouchInput.update();

		if (pauseButton.isPaused()) {
			pauseButton.update(dt, camera);
			continueButton.update(dt, camera);
			if (!Util.onMobile())
				musicButton.update(dt, camera);
			return;
		}

		if (!started) {
			if (TouchInput.spaceClicked() || (TouchInput.justDown() && !pauseButton.touchedInBounds(camera) && !musicButton.touchedInBounds(camera)))
				started = true;
			else {
				pauseButton.update(dt, camera);
				if (!Util.onMobile())
					musicButton.update(dt, camera);
				return;
			}
		}

		Player.updateGravity(camera, pauseButton, musicButton);
		manager.update(dt, camera);
		obstacleGenerator.update(dt, camera);
		itemGenerator.update(dt, camera);

		checkPlayerRemoval(dt);

		if (!destroyed)
			ScoreKeeper.getInstance().incrementScore();
	}

	private void checkPlayerRemoval(float dt) {
		CameraShaker.getInstance().update(dt);

		if (topPlayer.isRemoved() || bottomPlayer.isRemoved()) {

			if (!destroyed) {
				Sounds.play(Sounds.death);

				CameraShaker.getInstance().shake(camera);

				if (topPlayer.isRemoved())
					topPlayer.onRemove();

				if (bottomPlayer.isRemoved())
					bottomPlayer.onRemove();

				destroyed = true;
			} else {
				if (switchDelay < 0)
					ScreenSwitcher.getInstance(game).startSwitch(ScreenType.GAMEOVER);
			}

			switchDelay -= dt;
		}
	}

	public void renderScreen() {
		projectDrawBatches();

		drawBackground();
		drawLines();

		manager.render(camera);

		ScoreKeeper.getInstance().drawScore();

		drawInstructions();

		continueButton.render(camera);
	}

	private void drawInstructions() {
		float textSize = Util.onMobile() ? 0.14f : 0.08f;

		if (!started) {
			Fonts.drawString(Fonts.simplex, 0.18f, 0.7f, instruction1, textSize, TOP, false, true);
			Fonts.drawString(Fonts.simplex, 0.23f, 0.2f, instruction2, textSize, BOTTOM, false, true);
		}
	}

	private void projectDrawBatches() {
		camera.project(Entity.batch);
		camera.project(Entity.sr);
	}

	private void drawBackground() {
		ShapeRenderer sr = Entity.sr;

		sr.begin(ShapeType.Filled);
		sr.setColor(TOP);
		sr.rect(camera.x, LINE_THICKNESS, camera.getWidth(), 0.5f - LINE_THICKNESS);
	}

	private void drawLines() {
		ShapeRenderer sr = Entity.sr;

		sr.setColor(TOP);
		sr.rect(camera.x, camera.getHeight() - LINE_THICKNESS, camera.getWidth(), LINE_THICKNESS);

		sr.setColor(MIDDLE_LINE_COLOR);
		sr.rect(camera.x, 0.5f - MIDDLE_LINE_THICKNESS / 2, camera.getWidth(), MIDDLE_LINE_THICKNESS);

		sr.end();
	}

	public void onBackPressed() {
		pauseButton.onClick();
	}

	public void pause() {
		if (!pauseButton.isPaused())
			pauseButton.onClick();
	}

}
