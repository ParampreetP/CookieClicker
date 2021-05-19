package com.mygdx.madden04.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.madden04.MaddenClicker;

import javax.swing.JTextField;

public class MainMenu implements Screen {
    MaddenClicker game;
    Button leaderboardButton, gameButton, prestigeButton;
    FreeTypeFontGenerator fontGenerator;
    FreeTypeFontGenerator.FreeTypeFontParameter fontParameter;
    BitmapFont font;
    Texture texture;
    public Stage stage;


    public MainMenu(MaddenClicker m){
        this.game = m;
        Skin mySkin = new Skin(Gdx.files.internal("glassy/skin/glassy-ui.json"));
        ScreenViewport viewport = new ScreenViewport();
        stage = new Stage(viewport);
        fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));
        fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.color = Color.WHITE;
        fontParameter.size = 100;
        font = fontGenerator.generateFont(fontParameter);
        texture = new Texture(Gdx.files.internal("MaddenForCongress.png"));





        //libGDX Stage will be the closest thing to an input processor
        /*
        Buttons will be Actor(s), can pass in Button
        Gdx.input.setInputProcessor
         */
        leaderboardButton = new TextButton("Leaderboard", mySkin, "default");
        leaderboardButton.addListener(new ClickListener()
        {


            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(game.leaderBoard);
                Gdx.input.setInputProcessor(game.leaderBoard.stage);
            }
        });

        gameButton = new TextButton("Game", mySkin, "default");
        gameButton.addListener(new ClickListener()
        {


            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(game.gameScreen);
                Gdx.input.setInputProcessor(game.gameScreen.stage);
            }
        });

        prestigeButton = new TextButton("Prestige", mySkin, "default");
        prestigeButton.addListener(new ClickListener()
        {


            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(game.prestigeScreen);
                Gdx.input.setInputProcessor(game.prestigeScreen.stage);
            }
        });
        //breakpoint
        leaderboardButton.setPosition(2* Gdx.graphics.getWidth() / 3, 5);
        leaderboardButton.setSize(Gdx.graphics.getWidth() / 3, 400);
        gameButton.setPosition(Gdx.graphics.getWidth() / 3, 5);
        gameButton.setSize(Gdx.graphics.getWidth() / 3, 400);
        prestigeButton.setPosition( 0, 5);
        prestigeButton.setSize(Gdx.graphics.getWidth() / 3, 400);




        stage.addActor(this.leaderboardButton);
        stage.addActor(this.gameButton);
        stage.addActor(this.prestigeButton);
        Gdx.input.setInputProcessor(stage);
    }


    @Override
    public void show() {

    }

    //RENDER FUNCTION IS ONLY FOR DRAWING!!@!!!!
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(135/255f, 206/255f, 235/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        leaderboardButton.draw(game.batch, 1);
        gameButton.draw(game.batch, 1);
        prestigeButton.draw(game.batch, 1);
        font.draw(game.batch, " Welcome to the", 10, Gdx.graphics.getHeight()-10);
        font.draw(game.batch, " Madden Clicker.", 10, Gdx.graphics.getHeight() - 100);
        font.draw(game.batch, " Press a button", 10, Gdx.graphics.getHeight() - 190);
        font.draw(game.batch, "to go somewhere.", 10, Gdx.graphics.getHeight() - 280);
        font.draw(game.batch, "  Have fun :)", 10, Gdx.graphics.getHeight() - 370);
        game.batch.draw(texture, 55,  3 * Gdx.graphics.getHeight() / 10);
        game.batch.end();


    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
