package com.mango.game;

import java.util.HashMap;

public class Recipe {
	public MangoGame game;
	public String name;
	private HashMap<Ingredient, Integer> recipe;
	
	/**
	 * Pretty simple, just a map of ingredients --> int (measurement)
	 * Has a name to identify it, i.e. Gold, Silver, Semen etc
	 */
	public Recipe(String name, MangoGame game) {
		this.name = name;
		recipe = new HashMap<Ingredient, Integer>();
		this.game = game;
	}
	
	// Add to recipe
	public void add(Ingredient i, int x) {
		this.recipe.put(i, x);
	}
	
	public void add(String str, int x) {
		for(Ingredient i : game.INGREDIENTS) {
			if(i.name.equals(str)) {
				recipe.put(i, x);
			}
		}
	}
	
	public void printRecipe() {
		System.out.println("----- "+this.name+" -----");
		for(Ingredient i : recipe.keySet()) {
			System.out.println(i.name + " - "+recipe.get(i));
		}
	}
	
	public boolean compareRecipes(HashMap<Ingredient, Integer> checkRecipe) {
		System.out.println("Checking Recipe---");
		this.printRecipe();
		for(Ingredient i : checkRecipe.keySet()) {
			if(checkIngredient(i)) {
				continue;
			}
			
			else return false;
		}
		
		
		return true;
	}
	
	private boolean checkIngredient(Ingredient i) {
		for(Ingredient x : this.recipe.keySet()) {
			System.out.println("Checking "+i.name+" vs "+x.name);
			if(i.name == x.name) return true;
		}
		return false;
	}
}
