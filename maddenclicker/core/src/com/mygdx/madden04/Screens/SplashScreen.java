package com.mygdx.madden04.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.madden04.MaddenClicker;

public class SplashScreen implements Screen {

    private final String TEXT = "MaddenClicker!";

    MaddenClicker game;

    Texture splashImg;
    Sprite madden;
    private final int ROTATION_SPEED = -3;

    FreeTypeFontGenerator fontGenerator;
    FreeTypeFontGenerator.FreeTypeFontParameter fontParameter;
    BitmapFont font;
    float textWidth;

    Texture background;

    long startTime;

    public SplashScreen(MaddenClicker m){
        this.game = m;

        splashImg = new Texture("maddenSmile.png");
        madden = new Sprite(splashImg);
        madden.setPosition((Gdx.graphics.getWidth()/2)-(splashImg.getWidth()/2),Gdx.graphics.getHeight()/2);

        fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));
        fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.size = 80; fontParameter.color = Color.WHITE;
        font = fontGenerator.generateFont(fontParameter);

        GlyphLayout layout = new GlyphLayout(font, TEXT);
        textWidth = layout.width;

        background = new Texture("background.jpg");

        startTime = TimeUtils.millis();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();

        game.batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        font.draw(game.batch, TEXT, (Gdx.graphics.getWidth()/2)-(textWidth/2), (Gdx.graphics.getHeight()/2)-100);

        madden.draw(game.batch);

        game.batch.end();

        if(TimeUtils.timeSinceMillis(startTime) > 3000){
            game.setScreen(game.mainMenu);
            Gdx.input.setInputProcessor(game.mainMenu.stage);
        }

        float rotation = madden.getRotation();
        rotation += ROTATION_SPEED;
        if(rotation < -360) rotation += 360;
        madden.setRotation(rotation);
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

    }
}
