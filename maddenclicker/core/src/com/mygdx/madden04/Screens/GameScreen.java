package com.mygdx.madden04.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.madden04.MaddenClicker;

public class GameScreen implements Screen {

    private final String SCOREBOARD = "MADDENS: ";
    private final int[] LEVELS_PLUSONE = new int[]{50,100,150};
    private final int[] LEVELS_ADDITIONALCLICK = new int[]{30,60,90};

    Preferences prefs;
    long score;
    int numberOfPlusOnes; int numberOfAdditionalClicks;

    FreeTypeFontGenerator fontGenerator;
    FreeTypeFontGenerator.FreeTypeFontParameter fontParameter;
    BitmapFont font;
    BitmapFont font2;

    int screen_width, screen_height;

    Texture maddenNeutralFace, maddenSmileFace;
    int maddenX, maddenY;
    boolean maddenTouch;

    Texture plusOne, additional, moreMaddens;
    Texture background;

    public Stage stage;
    Skin skin;
    ImageButton b2;

    MaddenClicker game;

    Sound sound;

    public GameScreen(MaddenClicker m){
        this.game = m;

        sound = Gdx.audio.newSound(Gdx.files.internal("audio.wav"));

        prefs = Gdx.app.getPreferences("game preferences");

        /*prefs.putLong("score", 0);
        prefs.putLong("newestScore", 0);
        prefs.putInteger("numberOfPrestiges", 0);
        prefs.putInteger("numberOfPlusOnes", 0);
        prefs.putInteger("numberOfAdditionalClicks", 0);
        prefs.flush();*/

        score = prefs.getLong("score");
        numberOfPlusOnes = prefs.getInteger("numberOfPlusOnes");
        numberOfAdditionalClicks = prefs.getInteger("numberOfAdditionalClicks");

        fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));
        fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.size = 80; fontParameter.color = Color.WHITE;
        font = fontGenerator.generateFont(fontParameter);

        fontParameter.size = 200;
        font2 = fontGenerator.generateFont(fontParameter);

        maddenNeutralFace = new Texture("maddenNeutral.png");
        maddenSmileFace = new Texture("maddenSmile.png");
        maddenX = (Gdx.graphics.getWidth()/2)-(maddenNeutralFace.getWidth()/2); maddenY = (Gdx.graphics.getHeight()/2)-(maddenNeutralFace.getHeight()/2);
        maddenTouch = false;

        plusOne = new Texture("plusOneClick.png");
        additional = new Texture("additionalClicker.png");
        moreMaddens = new Texture("maddenNeutralDup.png");
        background = new Texture("background.jpg");

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));
        b2 = new ImageButton(skin);
        b2.setSize(Gdx.graphics.getWidth()/10, Gdx.graphics.getHeight()/18);
        b2.getStyle().imageUp = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("x.png"))));
        b2.getStyle().imageDown = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("x.png"))));
        b2.setPosition(Gdx.graphics.getWidth()/1.2f,Gdx.graphics.getHeight()/1.1f);

        b2.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                long time = System.currentTimeMillis();
                prefs.putLong("startTime",time);
                prefs.flush();

                game.setScreen(game.mainMenu);
                Gdx.input.setInputProcessor(game.mainMenu.stage);
                return true;
            }
        });

        stage.addActor(b2);
    }

    @Override
    public void show() {
        score = prefs.getLong("score");
        numberOfPlusOnes = prefs.getInteger("numberOfPlusOnes");
        numberOfAdditionalClicks = prefs.getInteger("numberOfAdditionalClicks");

        if(numberOfAdditionalClicks > 0){
            long time2 = System.currentTimeMillis();
            long time1 = prefs.getLong("startTime");

            if(time2-time1 > 10000){
                score += ((int) ((time2-time1)/10000))*numberOfAdditionalClicks;

                prefs.putLong("score", score);
                prefs.flush();
            }
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();

        game.batch.draw(background, 0, 0, screen_width, screen_height);

        font.draw(game.batch, SCOREBOARD + score, 10, screen_height-10);

        game.batch.draw(plusOne, 0, 0);
        font2.draw(game.batch, numberOfPlusOnes + "", plusOne.getWidth()/2, additional.getHeight()+90);
        game.batch.draw(additional, plusOne.getWidth(), 0);
        font2.draw(game.batch, numberOfAdditionalClicks + "", plusOne.getWidth()+additional.getWidth()/2, additional.getHeight()+90);
        game.batch.draw(moreMaddens, plusOne.getWidth()+additional.getWidth(), 0);

        if(Gdx.input.justTouched()){
            int x = Gdx.input.getX();
            int y = screen_height-Gdx.input.getY();

            if(x>maddenX && x<maddenX+maddenNeutralFace.getWidth()
                    && y>maddenY && y<maddenY+maddenNeutralFace.getHeight())   {
                maddenTouch = !maddenTouch;
                score += numberOfPlusOnes + 1;

                prefs.putLong("score", score);
                prefs.flush();
            }else if(x>0 && x<plusOne.getWidth()
                        && y>0 && y<plusOne.getHeight()){
                plusOneMethod();
            }else if(x>plusOne.getWidth() && x<plusOne.getWidth()+additional.getWidth()
                        && y>0 && y<additional.getHeight()){
                additionalClicksMethod();
            }else if(x>plusOne.getWidth()+additional.getWidth() &&
                        x<plusOne.getWidth()+additional.getWidth()+moreMaddens.getWidth()
                            && y>0 && y<moreMaddens.getHeight()){
                sound.play();
            }
        }

        if(maddenTouch){
            game.batch.draw(maddenSmileFace, maddenX, maddenY);
        }else{
            game.batch.draw(maddenNeutralFace, maddenX, maddenY);
        }

        game.batch.end();

        stage.act();
        stage.draw();
    }

    public void plusOneMethod(){
        if(numberOfPlusOnes < LEVELS_PLUSONE.length && score >= LEVELS_PLUSONE[numberOfPlusOnes]){
            score -= LEVELS_PLUSONE[numberOfPlusOnes];

            prefs.putLong("score", score);
            prefs.putInteger("numberOfPlusOnes", ++numberOfPlusOnes);
            prefs.flush();
        }
    }

    public void additionalClicksMethod(){
        if(numberOfAdditionalClicks < LEVELS_ADDITIONALCLICK.length && score >= LEVELS_ADDITIONALCLICK[numberOfAdditionalClicks]){
            score -= LEVELS_ADDITIONALCLICK[numberOfAdditionalClicks];

            prefs.putLong("score", score);
            prefs.putInteger("numberOfAdditionalClicks", ++numberOfAdditionalClicks);
            prefs.flush();
        }
    }

    @Override
    public void resize(int width, int height) {
        screen_width = width;
        screen_height = height;
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
