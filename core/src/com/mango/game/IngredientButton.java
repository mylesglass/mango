package com.mango.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class IngredientButton {
	
	public Vector2 position;
	public Rectangle bounds;
	public float width;
	public Ingredient ingredient;
	public int count;
	private boolean hover;
	private boolean isClicked;
	
	/**
	 * Used to display an item on the screen, for inverntory and shop interfaces. Maybe recipes too?
	 */
	public IngredientButton(float x, float y, float width, Ingredient i, int count) {
		this.position = new Vector2(x,y);
		this.bounds = new Rectangle(x, y, width, width);
		this.width = width;
		this.ingredient = i;
		this.count = count;
		this.hover = false;
		this.isClicked = false;
	}
	
	/**
	 * Check if x and y is on button
	 */
	public boolean checkCollision(float x, float y) {
		if(bounds.contains(new Vector2(x,y))) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * same as above, not sure if needed?
	 */
	public void checkHover(float x, float y) {
		if(checkCollision(x,y)) {
			this.hover = true;
		}
		
		else this.hover = false;
	}
	
	public void setClicked(Boolean b) {
		this.isClicked = b;
	}
	
	public void draw(SpriteBatch batch) {
		batch.draw(Assets.greySquare, position.x - 5, position.y - 5, width + 5, width + 5);
		
		if(hover) batch.draw(Assets.salmonSquare, position.x, position.y, width, width);
		else if (isClicked) batch.draw(Assets.orangeSquare, position.x, position.y, width, width);
		else batch.draw(Assets.whiteSquare, position.x, position.y, width, width);
		batch.draw(ingredient.texture, position.x, position.y, width, width);
		batch.draw(Assets.orangeSquare, position.x + width - 30, position.y, 30, 30);
		Assets.fontSmall.draw(batch, ""+count, position.x + width - 19, position.y + 19);
		Assets.fontSmall.draw(batch, ingredient.name, position.x, position.y - 10);
	}

}
