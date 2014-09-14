package com.mango.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class ShopScreen extends ScreenAdapter {

	private static float WINDOW_WIDTH = Gdx.graphics.getWidth();
	private static float WINDOW_HEIGHT = Gdx.graphics.getHeight();
	private static float BUTTON_HEIGHT = (WINDOW_HEIGHT / 10);
	private static float BUTTON_WIDTH = (WINDOW_WIDTH / 6);
	private static float XSPACING = 20;
	private static float YSPACING = XSPACING * 2;
	private static int ROW_ICON_NUM = 5;
	private static float ICON_WIDTH = ((WINDOW_WIDTH / 3) - (XSPACING * ROW_ICON_NUM + 1)) / ROW_ICON_NUM;

	public MangoGame game;
	OrthographicCamera camera;

	private ArrayList<IngredientButton> ingredientButtons;
	private Rectangle buyButton;
	private Rectangle sellButton;
	private Rectangle alchemyButton;
	private Ingredient selected;
	
	 Music backGround = Gdx.audio.newMusic(Gdx.files.internal("sfx/alchemyBG.wav"));

	AlchemyGame alchemyGame;

	/**
	 * Main menu / loading screen. You won't see this much.
	 */
	public ShopScreen(MangoGame game) {
		this.game = game;
		this.camera = new OrthographicCamera(WINDOW_WIDTH, WINDOW_HEIGHT);
		this.camera.position.set(WINDOW_WIDTH / 2, WINDOW_HEIGHT / 2, 0);
		this.alchemyGame = new AlchemyGame(game);
		this.ingredientButtons = new ArrayList<IngredientButton>();
		updateButtons();
		this.buyButton = new Rectangle();
		this.sellButton = new Rectangle();
		this.alchemyButton = new Rectangle();
	}

	public void update() {

		if(Gdx.input.isButtonPressed(Buttons.LEFT)) {
			/**if(this.addButton.contains(new Vector2(Gdx.input.getX(), WINDOW_HEIGHT - Gdx.input.getY())) && alchemyGame.currentIngredient != null) {
				alchemyGame.addIngredient(alchemyGame.currentIngredient, 1);
				game.player.inventory.removeIngredient(alchemyGame.currentIngredient, 1);
				this.updateButtons();
				System.out.println(alchemyGame.currentIngredient.name+ "Added");
				alchemyGame.currentIngredient = null;
			}**/
			if(this.selected != null && buyButton.contains(new Vector2(Gdx.input.getX(), WINDOW_HEIGHT - Gdx.input.getY()))) {
				if(game.player.getSilver() >= this.selected.buyPrice) {
					game.player.inventory.addIngredient(this.selected, 1);
					game.player.removeSilver(this.selected.buyPrice);
					for(IngredientButton ib : ingredientButtons) {
						if(ib.ingredient.name.equals(this.selected)) {
							ib.count = ib.count - 1;
							System.out.println("Count Removed");
						}
					}
				}
				updateButtons();

				selected = null;

			}

			if(this.alchemyButton.contains(new Vector2(Gdx.input.getX(), WINDOW_HEIGHT - Gdx.input.getY()))) {
				
				Assets.shopMusic.stop();
				Assets.backGround.setLooping(true);
				Assets.backGround.play();
				
				game.setScreen(game.alchemyScreen);
			}
		}

		for(IngredientButton ib : ingredientButtons) {
			ib.checkHover(Gdx.input.getX(), WINDOW_HEIGHT - Gdx.input.getY());
			if(Gdx.input.justTouched()) {
				if(ib.checkCollision(Gdx.input.getX(), WINDOW_HEIGHT - Gdx.input.getY())) {
					ib.setClicked(true);
					this.selected = ib.ingredient;
				}
				else {
					ib.setClicked(false);
					//alchemyGame.currentIngredient = null;
				}
			}
		}

	}

	public void draw() {
		GL20 gl = Gdx.gl;
		gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		game.batch.setProjectionMatrix(camera.combined);
		game.batch.begin();
		drawBackground();
		drawButtons();
		drawIngredients();
		drawSilver();
		if(this.selected != null) {
			drawBuySell();
		}
		game.batch.end();
	}

	private void updateButtons() {
		ingredientButtons.clear();
		int rowCount = 0;
		int colCount = 0;
		for(Ingredient i : game.INGREDIENTS) {
			if(i.soldInShop) {
				ingredientButtons.add(
						new IngredientButton(
								XSPACING + (colCount * ICON_WIDTH) + (XSPACING * colCount), 
								YSPACING + (rowCount * ICON_WIDTH) + (YSPACING * rowCount), 
								ICON_WIDTH, 
								i, 
								100
								)
						);

				colCount++;

				if(colCount == ROW_ICON_NUM) {
					colCount = 0;
					rowCount++;
				}
			}
		}
	}

	private void drawBackground() {
		game.batch.disableBlending();
		game.batch.draw(Assets.greySquare, 0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
		game.batch.enableBlending();
		game.batch.draw(Assets.shopkeep, WINDOW_WIDTH / 5, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
	}

	private void drawButtons() {
		// shop button
		float width = WINDOW_WIDTH / 3 - XSPACING * 4;
		float height = XSPACING * 3;
		float x = WINDOW_WIDTH / 3 * 2 + XSPACING * 3;
		float y = WINDOW_HEIGHT - XSPACING * 9;


		game.batch.draw(Assets.lightGreySquare, x - 5, y - 5, width + 5, height + 5);
		game.batch.draw(Assets.orangeSquare, x, y, width, height);
		Assets.fontRegular.draw(game.batch, "Alchemy", x + XSPACING * 5, y + XSPACING * 2);

		alchemyButton.x = x;
		alchemyButton.y = y;
		alchemyButton.width = width;
		alchemyButton.height = height;
	}

	private void drawIngredients() {
		// draw panel background.
		game.batch.draw(Assets.lightGreySquare, 0, 0, WINDOW_WIDTH / 3 + XSPACING, WINDOW_HEIGHT);
		Assets.fontBold.draw(game.batch, "Shop", XSPACING, WINDOW_HEIGHT - XSPACING);
		Assets.fontRegular.draw(game.batch, "Buy & Sell Ingredients", XSPACING, WINDOW_HEIGHT - XSPACING * 3);

		for(IngredientButton ib : this.ingredientButtons) {
			ib.draw(game.batch);
		}
	}
	
	private void drawSilver() {
		float width = ICON_WIDTH * 3;
		float height = ICON_WIDTH;
		float x = WINDOW_WIDTH / 3 + XSPACING * 2;
		float y = WINDOW_HEIGHT - height;
		
		game.batch.draw(Assets.lightGreySquare, x, y, width, height);
		game.batch.draw(Assets.silver, x, y, ICON_WIDTH, ICON_WIDTH);
		Assets.fontRegular.draw(game.batch, ""+game.player.getSilver(), x + ICON_WIDTH, y + XSPACING * 2);
		
	}
	
	private void drawBuySell() {
		float x = WINDOW_WIDTH / 3 + (XSPACING * 2);
		float width = WINDOW_WIDTH / 3;
		float height = (WINDOW_HEIGHT / 3);

		// draw bg
		game.batch.draw(Assets.lightGreySquare, x, XSPACING, width, height);

		// draw ingredient
		game.batch.draw(this.selected.texture, x + XSPACING, height / 2 - (ICON_WIDTH), ICON_WIDTH * 2, ICON_WIDTH * 2);

		// draw name
		Assets.fontBold.draw(game.batch, this.selected.name, x + XSPACING + (ICON_WIDTH * 2), height - (XSPACING * 2));

		//draw amount
		Assets.fontRegular.draw(
				game.batch, 
				"Buy: "+this.selected.buyPrice, 
				x + XSPACING + (ICON_WIDTH * 2), 
				height - (XSPACING * 4));

		// draw add button
		game.batch.draw(
				Assets.greySquare,
				x + (ICON_WIDTH * 2) + XSPACING - 5,
				height - (XSPACING * 9) - 5,
				(width / 2) + 5,
				XSPACING * 3 + 5
				);
		game.batch.draw(
				Assets.orangeSquare,
				x + (ICON_WIDTH * 2) + XSPACING,
				height - (XSPACING * 9),
				(width / 2),
				XSPACING * 3
				);
		
		Assets.fontRegular.draw(game.batch, "Buy", x + (ICON_WIDTH * 2) + XSPACING * 5, height - (XSPACING * 7));
		
		this.buyButton.x = x + (ICON_WIDTH * 2) + XSPACING;
		this.buyButton.y = height - (XSPACING * 9);
		this.buyButton.width = width / 2;
		this.buyButton.height = XSPACING * 3;
	}

	@Override
	public void render(float delta) {
		update();
		draw();
	}

}
