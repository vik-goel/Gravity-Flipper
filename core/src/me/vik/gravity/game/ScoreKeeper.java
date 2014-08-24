package me.vik.gravity.game;

import java.util.ArrayList;

import me.vik.gravity.screen.GameScreen;
import me.vik.gravity.util.Fonts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class ScoreKeeper {

	private static ArrayList<String> strings = new ArrayList<String>(5000);
	private static ScoreKeeper instance = new ScoreKeeper();
	private static Preferences prefs = Gdx.app.getPreferences("gravity-switcher");
	private static int bestScore = 0;

	private int score = 0;
	private String scoreString = "0";
	private static String bestScoreString;

	static {
		bestScore = prefs.getInteger("best", 0);
		bestScoreString = String.valueOf(bestScore);
	}

	public static ScoreKeeper getInstance() {
		return instance;
	}

	public void incrementScore() {
		score++;
		updateScoreString();
	}

	public void reset() {
		score = 0;
		updateScoreString();
	}

	public int getScore() {
		return score;
	}

	public void addCoin() {
		score += 150;
	}

	public void saveScore() {
		if (score > bestScore) {
			bestScore = score;
			bestScoreString = String.valueOf(bestScore);
			prefs.putInteger("best", bestScore);
			prefs.flush();
		}
	}

	public int getBestScore() {
		return bestScore;
	}

	public String toString() {
		return scoreString;
	}

	private void updateScoreString() {
		if (strings.size() <= score) {
			for (int i = strings.size(); i <= score; i++) {
				strings.add(String.valueOf(i));
			}
		}
		
		scoreString = strings.get(score);
	}

	public void drawScore() {
		Fonts.drawString(Fonts.font, GameScreen.LINE_THICKNESS / 4f, 1 - GameScreen.LINE_THICKNESS * 7f / 8f, scoreString, 0.047f, GameScreen.BOTTOM, true, false);
	}
	
	public boolean isMusicOn() {
		return prefs.getBoolean("musicOn", true);
	}
	
	public void setMusicOn(boolean musicOn) {
		prefs.putBoolean("musicOn", musicOn);
		prefs.flush();
	}

	public String getBestScoreString() {
		return bestScoreString;
	}

}
