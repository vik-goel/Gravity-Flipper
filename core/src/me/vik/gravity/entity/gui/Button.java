package me.vik.gravity.entity.gui;

import me.vik.gravity.entity.Camera;
import me.vik.gravity.entity.Entity;
import me.vik.gravity.util.Sounds;
import me.vik.gravity.util.TouchInput;
import me.vik.gravity.util.Util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public abstract class Button extends Entity {

	private static final float SCALE_ACCELERATION = 0.008f;
	
	protected Texture texture;
	protected Rectangle bounds;
	
	private boolean selected = false;
	protected float scale = 1;
	private float scaleSpeed = 0f;
	private float minScale = 0.8f;
	
	private Color mouseOver = Util.getColor(220, 220, 220);
	protected float origX;
	
	public Button(float x, float y, float width, float height, Texture texture) {
		super(x, y);
		this.texture = texture;
		bounds = Util.createRect(x, y, width, height);
		this.origX = x;
	}

	public void update(float dt, Camera camera) {
		super.update(dt, camera);
		
		x = camera.x + origX;
		bounds.x = x;
		
		if (!TouchInput.isDown()) {
			if (selected) {
				onClick();
				selected = false;
				scaleSpeed = 0;
				Sounds.play(Sounds.click);
			} else if (scale < 1) {
				scaleSpeed += SCALE_ACCELERATION;
				scale = Math.min(1, scale + scaleSpeed);
			}
			
			return;
		}
		
		scaleSpeed += SCALE_ACCELERATION;
		
		if (touchedInBounds(camera)) {
			if (!selected)
				scaleSpeed = 0;
			
			selected = true;
			scale = Math.max(minScale, scale - scaleSpeed);
		} else {
			if (selected)
				scaleSpeed = 0;
			
			selected = false;
			scale = Math.min(1, scale + scaleSpeed);
		}
	}
	
	public void render(Camera camera) {
		super.render(camera);
		
		float width = bounds.width;
		float height = bounds.height;
		
		float scaleXOffs = width * scale - width;
		float scaleYOffs = height * scale - height;
		
		batch.begin();
		
		if (!Util.onMobile() && bounds.contains(TouchInput.getX() + camera.x, TouchInput.getY()) ) {
			batch.setColor(mouseOver);
		} else {
			batch.setColor(Color.WHITE);
		}
		
		
		batch.draw(texture, bounds.x - scaleXOffs / 2, bounds.y - scaleYOffs / 2, width + scaleXOffs, height + scaleYOffs);
		batch.end();
	}
	
	public boolean touchedInBounds(Camera camera) {
		return bounds.contains(TouchInput.getX() + camera.x, TouchInput.getY()) && TouchInput.isDown();
	}
	
	public abstract void onClick();

}
