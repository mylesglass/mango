package com.mango.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class AlchemyScreen extends ScreenAdapter {
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
	private Rectangle addButton;
	private Rectangle mixButton;
	private Rectangle shopButton;
	private Rectangle sellButton;
	
	AlchemyGame alchemyGame;
	
	/**
	 * Main menu / loading screen. You won't see this much.
	 */
	public AlchemyScreen(MangoGame game) {
		this.game = game;
		this.camera = new OrthographicCamera(WINDOW_WIDTH, WINDOW_HEIGHT);
		this.camera.position.set(WINDOW_WIDTH / 2, WINDOW_HEIGHT / 2, 0);
		this.alchemyGame = new AlchemyGame(game);
		this.ingredientButtons = new ArrayList<IngredientButton>();
		updateButtons();
		this.addButton = new Rectangle();
		this.mixButton = new Rectangle();
		this.shopButton = new Rectangle();
		this.sellButton = new Rectangle();
		
	}
	
	public void update() {
		updateButtons();
		if(Gdx.input.isButtonPressed(Buttons.LEFT)) {
			if(this.addButton.contains(new Vector2(Gdx.input.getX(), WINDOW_HEIGHT - Gdx.input.getY())) && alchemyGame.currentIngredient != null) {
				alchemyGame.addIngredient(alchemyGame.currentIngredient, 1);
				game.player.inventory.removeIngredient(alchemyGame.currentIngredient, 1);
				this.updateButtons();
				System.out.println(alchemyGame.currentIngredient.name+ "Added");
				alchemyGame.currentIngredient = null;
			}
			if(!alchemyGame.currentRecipe.isEmpty() && this.mixButton.contains(new Vector2(Gdx.input.getX(), WINDOW_HEIGHT - Gdx.input.getY()))) {
				for(Recipe r : game.RECIPES) {
					if(r.compareRecipes(alchemyGame.currentRecipe)) {
						System.out.println("You made "+ r.name);
						for(Ingredient i : game.INGREDIENTS) {
							if(i.name.equals(r.name)) {
								System.out.println("here");
								game.player.inventory.addIngredient(i, 1);
								break;
							}
						}
					}
				}
				updateButtons();
				alchemyGame.currentRecipe.clear();

			}
			if(this.shopButton.contains(new Vector2(Gdx.input.getX(), WINDOW_HEIGHT - Gdx.input.getY()))) {
				Assets.backGround.stop();
				Assets.shopMusic.setLooping(true);
				Assets.shopMusic.play();
				game.setScreen(game.shopScreen);
			}
			
			if(this.alchemyGame.currentIngredient != null && this.sellButton.contains(new Vector2(Gdx.input.getX(), WINDOW_HEIGHT - Gdx.input.getY()))) {
				game.player.addSilver(alchemyGame.currentIngredient.sellPrice);
				game.player.inventory.removeIngredient(alchemyGame.currentIngredient, 1);
				updateButtons();
				alchemyGame.currentIngredient = null;
			}
		}
		
		for(IngredientButton ib : ingredientButtons) {
			ib.checkHover(Gdx.input.getX(), WINDOW_HEIGHT - Gdx.input.getY());
			if(Gdx.input.isButtonPressed(Buttons.LEFT)) {
				if(ib.checkCollision(Gdx.input.getX(), WINDOW_HEIGHT - Gdx.input.getY())) {
					ib.setClicked(true);
					alchemyGame.currentIngredient = ib.ingredient;
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
		if(!alchemyGame.currentRecipe.isEmpty())
			drawRecipe();
		if(alchemyGame.currentIngredient != null) 
			drawCurrentIngredient();
		game.batch.end();
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
	
	private void updateButtons() {
		ingredientButtons.clear();
		int rowCount = 0;
		int colCount = 0;
		for(Ingredient i : game.player.inventory.ingredients.keySet()) {
			ingredientButtons.add(
					new IngredientButton(
							XSPACING + (colCount * ICON_WIDTH) + (XSPACING * colCount), 
							YSPACING + (rowCount * ICON_WIDTH) + (YSPACING * rowCount), 
							ICON_WIDTH, 
							i, 
							game.player.inventory.ingredients.get(i) 
						)
					);
			
			colCount++;
			
			if(colCount == ROW_ICON_NUM) {
				colCount = 0;
				rowCount++;
			}
		}
	}
	
	private void drawBackground() {
		//game.batch.disableBlending();
		//game.batch.draw(Assets.alchemyBackground, 0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
		//game.batch.enableBlending();
		game.batch.draw(Assets.greenSquare, 0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
		game.batch.draw(Assets.character, WINDOW_WIDTH / 5, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
	}
	
	private void drawButtons() {
		// shop button
		float width = WINDOW_WIDTH / 3 - XSPACING * 4;
		float height = XSPACING * 3;
		float x = WINDOW_WIDTH / 3 * 2 + XSPACING * 3;
		float y = WINDOW_HEIGHT - XSPACING * 4;
		
		
		game.batch.draw(Assets.lightGreySquare, x - 5, y - 5, width + 5, height + 5);
		game.batch.draw(Assets.orangeSquare, x, y, width , height);
		Assets.fontRegular.draw(game.batch, "Shop", x + XSPACING * 7, y + XSPACING * 2);
		
		shopButton.x = x;
		shopButton.y = y;
		shopButton.width = width;
		shopButton.height = height;
		
		
	}
	
	private void drawRecipe() {
		// draw bg
		game.batch.draw(Assets.lightGreySquare, (WINDOW_WIDTH / 3) * 2 + XSPACING * 3, XSPACING, WINDOW_WIDTH / 3 - XSPACING * 4, WINDOW_HEIGHT /3);
		Assets.fontBold.draw(game.batch, "Current Recipe", (WINDOW_WIDTH / 3) * 2 + XSPACING * 3 + XSPACING, WINDOW_HEIGHT / 3 );
		// write each name
		int count = 0;
		for(Ingredient i : alchemyGame.currentRecipe.keySet()) {
			Assets.fontSmall.draw(game.batch, i.name+ " x "+alchemyGame.currentRecipe.get(i), (WINDOW_WIDTH / 3) * 2 + XSPACING * 3 + XSPACING, WINDOW_HEIGHT / 3 - (XSPACING * 2 +(XSPACING) * count));
			count++;
		}
		// mix button
		game.batch.draw(Assets.greySquare, (WINDOW_WIDTH / 3) * 2 + (XSPACING * 4) - 5, XSPACING * 2 - 5, (WINDOW_WIDTH / 3) - (XSPACING * 6) + 5, XSPACING * 3 +5);
		game.batch.draw(Assets.orangeSquare, (WINDOW_WIDTH / 3) * 2 + (XSPACING * 4), XSPACING * 2, (WINDOW_WIDTH / 3) - (XSPACING * 6), XSPACING * 3);
		Assets.fontRegular.draw(game.batch, "Mix", (WINDOW_WIDTH / 3) * 2 + (XSPACING * 10), XSPACING * 4);
		mixButton.x = (WINDOW_WIDTH / 3) * 2 + (XSPACING * 4);
		mixButton.y = XSPACING * 2;
		mixButton.width = (WINDOW_WIDTH / 3) - (XSPACING * 6);
		mixButton.height = XSPACING * 3;
		// if match, show what you made
		
		// else show fail
	}
	
	private void drawIngredients() {
		// draw panel background.
		game.batch.draw(Assets.lightGreySquare, 0, 0, WINDOW_WIDTH / 3 + XSPACING, WINDOW_HEIGHT);
		Assets.fontBold.draw(game.batch, "Inventory", XSPACING, WINDOW_HEIGHT - XSPACING);
		Assets.fontRegular.draw(game.batch, "Click an ingredient for more information", XSPACING, WINDOW_HEIGHT - XSPACING * 3);

		for(IngredientButton ib : this.ingredientButtons) {
			ib.draw(game.batch);
		}
	}
	
	private void drawCurrentIngredient() {
		float x = WINDOW_WIDTH / 3 + (XSPACING * 2);
		float width = WINDOW_WIDTH / 3;
		float height = (WINDOW_HEIGHT / 3);
		
		// draw bg
		game.batch.draw(Assets.lightGreySquare, x, XSPACING, width, height);
		
		// draw ingredient
		game.batch.draw(alchemyGame.currentIngredient.texture, x + XSPACING, height / 2 - (ICON_WIDTH), ICON_WIDTH * 2, ICON_WIDTH * 2);
		
		// draw name
		Assets.fontBold.draw(game.batch, alchemyGame.currentIngredient.name, x + XSPACING + (ICON_WIDTH * 2), height - (XSPACING * 2));
		
		//draw amount
		Assets.fontRegular.draw(
				game.batch, 
				"Amount: "+game.player.inventory.ingredients.get(alchemyGame.currentIngredient), 
				x + XSPACING + (ICON_WIDTH * 2), 
				height - (XSPACING * 4));
		
		// draw add button
		game.batch.draw(
				Assets.greySquare,
				x + (ICON_WIDTH * 2) + XSPACING - 5,
				height - (XSPACING * 7) - 5,
				(width / 2) + 5,
				XSPACING * 2 + 5
				);
		game.batch.draw(
				Assets.orangeSquare,
				(x + (ICON_WIDTH * 2) + XSPACING),
				height - (XSPACING * 7),
				(width / 2) ,
				XSPACING * 2
				);
		Assets.fontRegular.draw(game.batch, "Add", x + (ICON_WIDTH * 2) + XSPACING * 5, height - (XSPACING * 6) + 5);
		this.addButton.x = (x + (ICON_WIDTH * 2) + XSPACING);
		this.addButton.y = height - (XSPACING * 7);
		this.addButton.width =  (width / 2);
		this.addButton.height = XSPACING * 2;
		
		game.batch.draw(Assets.greySquare, x + ICON_WIDTH * 2 + XSPACING - 5, height - (XSPACING * 10) - 5, width / 2 +5, XSPACING * 2 +5);
		game.batch.draw(Assets.orangeSquare, x + ICON_WIDTH * 2 + XSPACING, height - (XSPACING * 10), width / 2, XSPACING * 2);
		Assets.fontRegular.draw(game.batch, "Sell for "+this.alchemyGame.currentIngredient.sellPrice, x + (ICON_WIDTH * 2) + XSPACING * 4, height - (XSPACING * 9) + 5);
		
		this.sellButton.x = x + ICON_WIDTH * 2 + XSPACING;
		this.sellButton.y = height - (XSPACING * 10);
		this.sellButton.width = width / 2;
		this.sellButton.height = XSPACING * 2;
	}
	
	@Override
	public void render(float delta) {
		update();
		draw();
	}
	
}
