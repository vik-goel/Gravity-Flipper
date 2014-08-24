package me.vik.gravity.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Fonts {

	public static BitmapFont font = createFont("font");
	public static BitmapFont simplex = createFont("simplex");
	
	private static SpriteBatch batch = new SpriteBatch();
	
	public static void drawString(BitmapFont font, float x, float y, String s, float size, Color color, boolean rightAlign, boolean centerAlign) {
		scaleFont(font, size);
		
		x *= Gdx.graphics.getWidth();
		y *= Gdx.graphics.getHeight();
		
		TextBounds bounds = font.getBounds(s);
		y += bounds.height;
		
		if (rightAlign) 
			x = Gdx.graphics.getWidth() - x - bounds.width;
		
		if (centerAlign) {
			x = (1 - getWidth(font, s, size)) / 2f * Gdx.graphics.getWidth();
		}
		
		batch.begin();
		font.setColor(color);
		font.draw(batch, s, x, y);
		batch.end();
	}
	
	public static float getWidth(BitmapFont font, String s, float size) {
		scaleFont(font, size);
		
		float pxWidth = font.getBounds(s).width;
		
		return pxWidth / Gdx.graphics.getWidth();
	}
	
	private static void scaleFont(BitmapFont font, float size) {
		final float FONT_SIZE = 128f;
		
		size *= Gdx.graphics.getHeight();
		float scale = size / FONT_SIZE;
		
		font.setScale(scale);
	}
	
	private static BitmapFont createFont(String fontName) {
		BitmapFont font = new BitmapFont(Gdx.files.internal("fonts/" + fontName + ".fnt"), Gdx.files.internal("fonts/" + fontName + ".png"), false);
		font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		return font;
	}

}
