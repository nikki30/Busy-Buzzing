package com.nikki.croakinthecreek;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class CroakInTheCreek extends ApplicationAdapter {


    SpriteBatch batch;
    Texture background;

    Texture[] bees;
    int flapState = 0;
    float beeY = 0;
    float velocity = 0;
    int gameState = 0;
    float gravity = 2;

    @Override
    public void create () {
        batch = new SpriteBatch();
        background = new Texture("bg.jpg");
        bees = new Texture[2];
        bees[0] = new Texture("beesecond.png");
        bees[1] = new Texture("beesec.png");
        beeY = Gdx.graphics.getHeight()/2 - bees[0].getHeight()/2;
    }

    @Override
    public void render () {
        if (gameState != 0) {
            if (Gdx.input.justTouched()) {
                velocity = -40;  //adds to birds height and shoots it up into air
            }
            if(beeY > 0 || velocity < 0) { //either tap or bird being above bottom of screen should push it up
                velocity += gravity;
                beeY -= velocity;
            }
        } else {
            if (Gdx.input.justTouched()) {
                gameState = 1;
            }
        }
        if (flapState == 0) {
            flapState = 1;
        } else {
            flapState = 0;
        }
        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(bees[flapState], Gdx.graphics.getWidth() / 2 - bees[flapState].getWidth() / 2, beeY);
        batch.end();
    }

    @Override
    public void dispose () {
        batch.dispose();
        background.dispose();
    }
}
