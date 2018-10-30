package com.example.vivianbabiryekulumba.supermariobros;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.example.vivianbabiryekulumba.supermariobros.Screens.PlayScreen;

public class SuperMarioBros extends Game {
    public static final int V_WIDTH = 400;
    public static final int V_HEIGHT = 208;
	public SpriteBatch batch;
	public static final float PIXELS_PER_METER = 100;

	
	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new PlayScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
