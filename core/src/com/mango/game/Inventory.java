package com.mango.game;

import java.util.ArrayList;
import java.util.HashMap;

public class Inventory {
	
	public ArrayList<Recipe> recipes;
	public HashMap<Ingredient, Integer> ingredients;

	/**
	 * This holds all the stuff the user has. So.. recipes, and ingredients and the amount of that ingredient they have.
	 */
	public Inventory () {
		recipes = new ArrayList<Recipe>();
		ingredients = new HashMap<Ingredient, Integer>();
	}
	
	/**
	 * Add Ingredient in desired amount to cthe inventory
	 */
	public void addIngredient(Ingredient i, int amount) {
		
		// If ingredient already in inventory, increase total amount
		if(ingredients.containsKey(i)) {
			int x = ingredients.get(i);
			x += amount;
			ingredients.put(i, x);
		}
		
		else if (amount == 0) {
			//do nothing
		}
		
		else {
			ingredients.put(i, amount);
		}
	}
	
	public void removeIngredient(Ingredient i, int amount) {
		if(!ingredients.containsKey(i)) {
			
		}
		else if(ingredients.get(i) == 1) {
			ingredients.remove(i);
		}
		else {
			ingredients.put(i, ingredients.get(i) - amount);
		}
	}
	
	public void addRecipe(Recipe r) {
		
	}
	
	public void printInventory() {
		System.out.println("===== PRINT INVENTORY =====");
		System.out.println("===== RECIPES =====");
		for(Recipe r : recipes) {
			r.printRecipe();
		}
		System.out.println("===== Inventory =====");
		for(Ingredient i : ingredients.keySet()) {
			System.out.println(i.name + " - "+ingredients.get(i));
		}
	}
}
