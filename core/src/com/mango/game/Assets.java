package com.mango.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


/**
 * Well basically, we try to load all of the assets here, then call them where we need them.
 * The exception of this is the ingredient textures, which are loaded when each ingredient is created, and has the texture connected to that object.
 * ^hope that makes sense
 * @author PXLJM
 *
 */
public class Assets {
	
	//music
	 public static Music backGround = Gdx.audio.newMusic(Gdx.files.internal("sfx/alchemyBG.wav"));
	 public static Music shopMusic = Gdx.audio.newMusic(Gdx.files.internal("sfx/music.mp3"));
	 public static Sound intro = Gdx.audio.newSound(Gdx.files.internal("sfx/intro.wav"));
	 public static Sound hurray = Gdx.audio.newSound(Gdx.files.internal("sfx/hurray.wav"));
	 public static Sound fail = Gdx.audio.newSound(Gdx.files.internal("sfx/fail.wav"));
	 
	// Intro, Menu
	public static Texture logo;
	public static Texture loadBackground;
	public static Texture menuBackground;
	
	public static Texture coin;
	public static Texture silver;
	public static Animation coinAnim;
	
	public static FileHandle ingredients;
	public static FileHandle recipes;
	
	//General Color Squares
	public static Texture whiteSquare;
	public static Texture lightGreySquare;
	public static Texture greySquare;
	public static Texture salmonSquare;
	public static Texture darkGreySquare;
	public static Texture greenSquare;
	public static Texture orangeSquare;
	
	// Alchemy Screen 
	public static Texture alchemyBackground;
	public static Texture ingredientsPanelBackground;
	public static Texture ingredientsPanelItemFrame;
	public static Texture character;
	
	// Shop Screen
	public static Texture shopkeep;
	
	//fonts
	public static BitmapFont fontBold;
	public static BitmapFont fontSmall;
	public static BitmapFont fontRegular;
	public static BitmapFont fontLarge;
	public static BitmapFont font;
	
	public static Texture loadTexture (String file) {
		return new Texture(Gdx.files.internal(file));
	}
	
	public static void load () {
		// Intro Files
		silver = loadTexture("images/ingredients/silver.png");
		logo = loadTexture("intro/platty.jpg");
		loadBackground = loadTexture("images/misc/logo.png");
		menuBackground = loadTexture("images/misc/maintitle.png");
		
		//txt's
		ingredients = Gdx.files.internal("ingredients.txt");
		recipes = Gdx.files.internal("recipes.txt");
		
		// Squares
		whiteSquare = loadTexture("images/squares/white_square.png");
		lightGreySquare = loadTexture("images/squares/lightgrey_square.png");
		greySquare = loadTexture("images/squares/grey_square.png");
		salmonSquare = loadTexture("images/squares/salmon_square.png");
		darkGreySquare = loadTexture("images/squares/444444.png");
		greenSquare = loadTexture("images/squares/6f7b62.png");
		orangeSquare = loadTexture("images/squares/e2a052.png");
		
		// Alchemy Screen 
		alchemyBackground = loadTexture("images/alchemy/background.jpg");
		ingredientsPanelBackground = loadTexture("images/alchemy/ingredients_bg.png");
		ingredientsPanelItemFrame = loadTexture("images/alchemy/ingredient_individual_bg.png");
		character = loadTexture("images/alchemy/mainchar.png");
		
		// Shop Screen
		shopkeep = loadTexture("images/shop/shopKeep.png");
		
		
		// Coin Anim Test (PLACEHOLDER)
		coin = loadTexture("images/ingredients/coins_1.png");
		coinAnim = new Animation(
				0.2f, 
				new TextureRegion(coin, 0f, 0f, 40f, 44.5f), 
				new TextureRegion(coin, 0f, 44.5f, 40f, 44.5f),
				new TextureRegion(coin, 0f, 89f, 40f, 44.5f),
				new TextureRegion(coin, 0f, 133.5f, 40f, 44.5f)
				);
		
		//fonts
		fontSmall = new BitmapFont(Gdx.files.internal("font/lato_reg_12.fnt"), Gdx.files.internal("font/lato_reg_12_0.png"), false);
		fontRegular = new BitmapFont(Gdx.files.internal("font/lato_reg_28.fnt"), Gdx.files.internal("font/lato_reg_28_0.png"), false);
		fontLarge = new BitmapFont(Gdx.files.internal("font/lato_reg_36.fnt"), Gdx.files.internal("font/lato_reg_36_0.png"), false);
		fontBold = new BitmapFont(Gdx.files.internal("font/lato_bold_36.fnt"), Gdx.files.internal("font/lato_bold_36_0.png"), false);
		font = new BitmapFont();
	}

}
