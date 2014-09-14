package com.mango.game;

import java.util.HashMap;

public class AlchemyGame {
	public MangoGame game;
	
	public HashMap<Ingredient, Integer> currentRecipe;
	public Ingredient currentIngredient;
	
	
	public AlchemyGame(MangoGame game) {
		currentRecipe = new HashMap<Ingredient, Integer>();
		this.game = game;
		game.player.inventory.addIngredient(game.INGREDIENTS.get(16), 1);
		game.player.inventory.addIngredient(game.INGREDIENTS.get(12), 1);
	}
	
	/**
	 * Add Ingredient in desired amount to current recipe player is creating
	 */
	public void addIngredient(Ingredient i, int amount) {
		
		// If ingredient already in recipe, increase total amount
		if(currentRecipe.containsKey(i)) {
			int x = currentRecipe.get(i);
			x += amount;
			currentRecipe.put(i, x);
		}
		
		else if (amount == 0) {
			//do nothing
		}
		
		else {
			currentRecipe.put(i, amount);
		}
	}
	

	/**
	 * Check recipe against concrete defined recipes to see if character made something. 
	 * Returns Recipe if successful, null otherwise
	 */
	public Recipe checkRecipe(Recipe recipe) {
		for(Recipe r : game.RECIPES) {
			if(r.equals(recipe)) {
				return r;
			}
		}
		
		return null;
	}
}
