package me.vik.gravity.screen;

import me.vik.gravity.LD30Game;
import me.vik.gravity.entity.Entity;
import me.vik.gravity.entity.gui.MusicButton;
import me.vik.gravity.entity.gui.PlayButton;
import me.vik.gravity.game.ScoreKeeper;
import me.vik.gravity.game.ScreenSwitcher;
import me.vik.gravity.util.Fonts;
import me.vik.gravity.util.Textures;
import me.vik.gravity.util.TouchInput;
import me.vik.gravity.util.Util;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameOverScreen extends RenderScreen {

	private String gameOver = "GAME OVER";
	private String scoreText = "Score";
	private String bestScoreText = "Best";
	
	public GameOverScreen(LD30Game game) {
		super(game);
		manager.addEntity(new PlayButton((Util.getAspectRatio() - PlayButton.getWidth()) / 2f, 0.05f, game, true));
		manager.addEntity(new MusicButton(Util.getAspectRatio() - 0.25f, 0.05f, 0.2f));
	}

	public void show() {
		super.show();
		ScoreKeeper.getInstance().saveScore();
	}

	public void updateScreen(float dt) {
		manager.update(dt, camera);

		TouchInput.update();

		if (TouchInput.spaceClicked()) {
			ScreenSwitcher.getInstance(game).startSwitch(ScreenType.GAME);
		}
	}

	public void renderScreen() {
		manager.render(camera);

		String score = ScoreKeeper.getInstance().toString();
		String bestScore = ScoreKeeper.getInstance().getBestScoreString();

		Fonts.drawString(Fonts.simplex, 0.22f, 0.825f, gameOver, 0.2f, GameScreen.TOP, false, true);
		Fonts.drawString(Fonts.simplex, 0.4f, 0.64f, scoreText, 0.1f, GameScreen.TOP, false, true);
		Fonts.drawString(Fonts.simplex, 0, 0.56f, score, 0.08f, GameScreen.TOP, false, true);
		Fonts.drawString(Fonts.simplex, 0.4f, 0.46f, bestScoreText, 0.1f, GameScreen.TOP, false, true);
		Fonts.drawString(Fonts.simplex, 0, 0.38f, bestScore, 0.08f, GameScreen.TOP, false, true);
		
		SpriteBatch batch = Entity.batch;
		batch.begin();
		batch.setColor(GameScreen.TOP);
		batch.draw(Textures.border, 0.4f * Util.getAspectRatio(), 0.34f, 0.2f * Util.getAspectRatio(), 0.385f);
		batch.end();
	}

}
