package com.mango.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class Player {
	
	public Inventory inventory; // Items and Recipes that the user has
	private int silver; // currency in Australian outback
	private Sound money = Gdx.audio.newSound(Gdx.files.internal("sfx/purchase.wav"));
	
	/**
	 * Represents the user in the game
	 */
	public Player () {
		inventory = new Inventory();
		silver =  100;
	}
	
	public int getSilver() {
		return this.silver;
	}
	
	public void addSilver(int num) {
		this.silver += num;
	   money.play(1f);
		
	}
	
	public void removeSilver(int num) {
		this.silver = this.silver - num;
	}
	
	
	
}
