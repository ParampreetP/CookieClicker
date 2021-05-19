package com.mygdx.madden04.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.madden04.MaddenClicker;

public class PrestigeScreen implements Screen {
    Preferences prefs;
    long score;
    MaddenClicker game;
    public Stage stage;
    TextButton b1, winYes, winNo;
    ImageButton b2;
    Skin skin;
    Label outputLabel;
    Window window;

    int numberOfPrestiges;

    public PrestigeScreen(MaddenClicker m){
        prefs = Gdx.app.getPreferences("game preferences");
        this.game = m;

        numberOfPrestiges = prefs.getInteger("numberOfPrestiges");

        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));
        b1 = new TextButton("Prestige", skin, "default");
        window = new Window("Are you sure?", skin);
        window.setResizable(true);
        window.setPosition(240,300);
        winYes = new TextButton("Yes", skin, "default");
        winNo = new TextButton("Cancel", skin, "default");
        winYes.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                if(prefs.getLong("score") > 500){ // choose arbitrary number for now
                    outputLabel.setText("Success");
                    prefs.putInteger("numberOfPrestiges", ++numberOfPrestiges);
                    prefs.putLong("newestScore", prefs.getLong("score"));
                    prefs.putLong("score", 0+(numberOfPrestiges*50));
                    prefs.putInteger("numberOfPlusOnes", 0);
                    prefs.putInteger("numberOfAdditionalClicks", 0);
                    prefs.flush();
                }
                else{
                    outputLabel.setText("Score too low");
                }
                return true;
            }
        });
        winNo.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                window.remove();
                return true;
            }
        });

        window.add(winYes);
        window.add(winNo);
        window.pack();

        b1.setSize(Gdx.graphics.getWidth()/3, Gdx.graphics.getHeight()/12);
        b1.setPosition(Gdx.graphics.getWidth()/3f,Gdx.graphics.getHeight()/2);
        b2 = new ImageButton(skin);
        b2.setSize(Gdx.graphics.getWidth()/10, Gdx.graphics.getHeight()/18);
        b2.getStyle().imageUp = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("x.png"))));
        b2.getStyle().imageDown = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("x.png"))));
        b2.setPosition(Gdx.graphics.getWidth()/1.2f,Gdx.graphics.getHeight()/1.1f);

        b1.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                System.out.println("TESTING");
                stage.addActor(window);
                return true;
            }
        });

        b2.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                game.setScreen(game.mainMenu);
                Gdx.input.setInputProcessor(game.mainMenu.stage);
                return true;
            }
        });

        stage.addActor(b1);
        stage.addActor(b2);
        outputLabel = new Label("Press the button to prestige", skin, "black");
        outputLabel.setFontScale(2f);
        outputLabel.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()/5);
        outputLabel.setAlignment(Align.center);
        stage.addActor(outputLabel);

    }
    @Override
    public void show() {
        System.out.println(score);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(102f/255f,1,1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();

        game.batch.begin();

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
       skin.dispose();
    }
}
