package com.mango.game;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Scanner;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MangoGame extends Game {
	// Screens
	public ShopScreen shopScreen;
	public AlchemyScreen alchemyScreen;
	public LoadScreen loadScreen;
	public MenuScreen menuScreen;

	// Player Things
	public Player player;

	//SpriteBatcher
	public SpriteBatch batch;

	// Ingredients List
	public ArrayList<Ingredient> INGREDIENTS;

	// Recipe List
	public ArrayList<Recipe> RECIPES;

	@Override
	public void create () {
		this.player = new Player();
		batch = new SpriteBatch();

		// Loading Assets
		Assets.load();
		INGREDIENTS = new ArrayList<Ingredient>();
		loadIngredients();
		RECIPES = new ArrayList<Recipe>();
		loadRecipes();


		//Screens
		loadScreen = new LoadScreen(this);
		shopScreen = new ShopScreen(this);
		alchemyScreen = new AlchemyScreen(this);
		menuScreen = new MenuScreen(this);

		setScreen(loadScreen);
	}

	@Override
	public void render () {
		super.render();
	}

	/** 
	 * Load each ingredient from file.
	 */
	private void loadIngredients() {
		/**
		System.out.println("[MangoGame] Loading Ingredients");
		Scanner scan = new Scanner(Assets.ingredients.readString());

		while(scan.hasNext()) {
			// TODO: replace path string with 'scan.nextLine()' once files are in place
			this.INGREDIENTS.add(new Ingredient(scan.nextLine(), "images/ingredients/flame.png", scan.nextInt(), scan.nextInt(), scan.nextLine()));
		}

		scan.close();**/
		BufferedReader reader = new BufferedReader(new StringReader(Assets.ingredients.readString()));
		
		try {
			String line = null;
			while((line = reader.readLine()) != null) {
				String name = line;
				String canSell = reader.readLine();
				int buy = Integer.parseInt(reader.readLine());
				int sell = Integer.parseInt(reader.readLine());
				String path = reader.readLine();
				this.INGREDIENTS.add(new Ingredient(name, canSell, buy, sell, path+".png"));
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * Load each recipe from file
	 */
	private void loadRecipes() {
		BufferedReader reader = new BufferedReader(new StringReader(Assets.recipes.readString()));
		
		try {
			String line = null;
			while((line = reader.readLine()) != null) {
				String name = line;
				Recipe recipe = new Recipe(name, this);
				recipe.add(reader.readLine(), 1);
				recipe.add(reader.readLine(), 1);
				recipe.printRecipe();
				RECIPES.add(recipe);
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
