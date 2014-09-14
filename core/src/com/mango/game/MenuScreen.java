package com.mango.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class MenuScreen extends ScreenAdapter {
	public MangoGame game;
	OrthographicCamera camera;
	
	
	/**
	 * Main menu / loading screen. You won't see this much.
	 */
	public MenuScreen(MangoGame game) {
		this.game = game;
		this.camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		this.camera.position.set(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, 0);
	}
	
	public void update() {
		if(Gdx.input.isKeyPressed(Keys.ENTER))
			//Assets.backGround.setLooping(true);
		    //Assets.backGround.play();
			game.setScreen(game.alchemyScreen);
	}
	
	public void draw() {
		GL20 gl = Gdx.gl;
		gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		game.batch.setProjectionMatrix(camera.combined);
		
		game.batch.begin();
		game.batch.disableBlending();
		game.batch.draw(Assets.menuBackground, 0, 0);
		game.batch.enableBlending();
		game.batch.end();
	}
	
	@Override
	public void render(float delta) {
		update();
		draw();
	}
	
}

