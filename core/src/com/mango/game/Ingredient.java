package com.mango.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Ingredient {
	public String name;
	public int buyPrice;
	public int sellPrice;
	public Texture texture;
	public boolean soldInShop;
	
	float stateTime;
	
	/**
	 * An Individual Object that represents an Ingredient.
	 */
	public Ingredient(String name, String sellable, int buy, int sell, String pathToTexture) {
		this.name = name;
		
		// Can the object be sold in a shop
		if(sellable.equals("y")) 
			this.soldInShop = true;
		else
			this.soldInShop = false;
		
		// Prices
		this.buyPrice = buy;
		this.sellPrice = sell;
		
		this.texture = new Texture(Gdx.files.internal(pathToTexture));
		stateTime = 0;
		System.out.println("[Ingredient] "+this.name+" created successfully");
	}
	
	public void update(float deltaTime) {
		stateTime += deltaTime;
	}
}
