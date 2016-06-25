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

    @Override
    public void create () {
        batch = new SpriteBatch();
        background = new Texture("bg.jpg");
        bees = new Texture[2];
        bees[0] = new Texture("beesecond.png");
        bees[1] = new Texture("beesec.png");

    }

    @Override
    public void render () {
        if(flapState == 0) {
            flapState = 1;
        }else{
            flapState = 0;
        }
        batch.begin();
        batch.draw(background,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        batch.draw(bees[flapState],Gdx.graphics.getWidth()/2 - bees[flapState].getWidth()/2,Gdx.graphics.getHeight()/2 - bees[flapState].getHeight()/2);
        batch.end();
    }

    @Override
    public void dispose () {
        batch.dispose();
        background.dispose();
    }
}
