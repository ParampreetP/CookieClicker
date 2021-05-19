package com.mygdx.madden04;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.mygdx.madden04.Screens.GameScreen;
import com.mygdx.madden04.Screens.LeaderBoard;
import com.mygdx.madden04.Screens.MainMenu;
import com.mygdx.madden04.Screens.PrestigeScreen;
import com.mygdx.madden04.Screens.SplashScreen;

public class MaddenClicker extends Game {
	public SpriteBatch batch;
	public MainMenu mainMenu;
	public LeaderBoard leaderBoard;
	public PrestigeScreen prestigeScreen;
	public GameScreen gameScreen;

	@Override
	public void create () {
		mainMenu = new MainMenu(this);
		leaderBoard = new LeaderBoard(this);
		prestigeScreen = new PrestigeScreen(this);
		gameScreen = new GameScreen(this);
		batch = new SpriteBatch();
		setScreen(new SplashScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
