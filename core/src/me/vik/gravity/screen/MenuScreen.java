package me.vik.gravity.screen;

import me.vik.gravity.LD30Game;
import me.vik.gravity.entity.gui.MusicButton;
import me.vik.gravity.entity.gui.PlayButton;
import me.vik.gravity.game.ScreenSwitcher;
import me.vik.gravity.util.Fonts;
import me.vik.gravity.util.TouchInput;
import me.vik.gravity.util.Util;

public class MenuScreen extends RenderScreen {

	private String title = "Gravity Flipper";
	
	public MenuScreen(LD30Game game) {
		super(game);
		manager.addEntity(new PlayButton((Util.getAspectRatio() - PlayButton.getWidth()) / 2f, 0.35f, game, false));
		manager.addEntity(new MusicButton((Util.getAspectRatio() - 0.2f) / 2f, 0.05f, 0.2f));
	}
	
	public void show() {
		super.show();
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
		Fonts.drawString(Fonts.simplex, 0.051f, 0.7f, title, 0.23f, GameScreen.TOP, false, true);
	}

}
