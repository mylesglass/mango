package com.mango.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.Timer;

public class LoadScreen extends ScreenAdapter {

	public MangoGame game;
	OrthographicCamera camera;
	
	
	/**
	 * Main menu / loading screen. You won't see this much.
	 */
	public LoadScreen(MangoGame game) {
		this.game = game;
		this.camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		this.camera.position.set(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, 0);
	}
	public float timeStart = Gdx.graphics.getDeltaTime();
	
	public void update() {
		if(Gdx.input.isKeyPressed(Keys.ENTER) || timeStart < Gdx.graphics.getDeltaTime() - 0.02)
			game.setScreen(game.menuScreen);
		    Assets.intro.play();
	}
	
	public void draw() {
		GL20 gl = Gdx.gl;
		gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		game.batch.setProjectionMatrix(camera.combined);
		
		game.batch.begin();
		drawLogo();
		game.batch.end();
	}
	
	private void drawLogo() {
		
		game.batch.disableBlending();
		game.batch.draw(Assets.loadBackground, 0, 0);
		game.batch.enableBlending();
	}
	
	@Override
	public void render(float delta) {
		update();
		draw();
	}
	
}
