package com.nikki.croakinthecreek;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;


public class CroakInTheCreek extends ApplicationAdapter {


    SpriteBatch batch;
    Texture background;
    Texture topTube;
    Texture bottomTube;
    Texture gameover;

    Texture[] bees;
    int flapState = 0;
    float beeY = 0;
    float velocity = 0;
    int gameState = 0;
    float gravity = 2;
    float gap = 580;
    float maxTubeOffset;
    float tubeVelocity = 4;
    int numberOfTubes = 4;
    float[] tubeX = new float[numberOfTubes];
    float[] tubeOffset = new float[numberOfTubes];
    float distanceBetweenTubes;
    Circle beeCircle;
    Rectangle[] topTubeRectangles;
    Rectangle[] bottomTubeRectangles;
    //ShapeRenderer shapeRenderer;
    Random randomGenerator;
    Random randNumPick;
    int gameScore = 0;
    int scoringTube = 0;
    BitmapFont fontScore;
    BitmapFont fontMessage;
    CharSequence str;
    int index = 0;
    int index1 =0;

    @Override
    public void create () {
        batch = new SpriteBatch();
        fontScore = new BitmapFont();
        fontScore.setColor(Color.WHITE);
        fontScore.getData().setScale(10);
        fontMessage = new BitmapFont();
        fontMessage.setColor(Color.YELLOW);
        fontMessage.getData().setScale(6);
        background = new Texture("bg.jpg");
        gameover = new Texture("gameover.png");
        bees = new Texture[2];
        bees[0] = new Texture("beesecond.png");
        bees[1] = new Texture("beesec.png");
        topTube = new Texture("toptube.png");
        bottomTube = new Texture("bottomtube.png");
        maxTubeOffset = Gdx.graphics.getHeight()/2 - gap/2 - 100;
        randomGenerator = new Random();
        randNumPick = new Random();
        //shapeRenderer = new ShapeRenderer();
        beeCircle = new Circle();
        topTubeRectangles = new Rectangle[numberOfTubes];
        bottomTubeRectangles = new Rectangle[numberOfTubes];
        distanceBetweenTubes = Gdx.graphics.getWidth() * 3/4;

        startGame();
    }

    public void startGame(){

        index1 = randNumPick.nextInt(6);
        beeY = Gdx.graphics.getHeight()/2 - bees[0].getHeight()/2;
        for(int i=0;i<numberOfTubes;i++){
            tubeOffset[i] = (randomGenerator.nextFloat()- 0.5f)*(Gdx.graphics.getHeight() - gap - 200); //generates rand b/w -0.5 and +0.5
            tubeX[i] =  Gdx.graphics.getWidth()/2 - topTube.getWidth()/2 + Gdx.graphics.getWidth()+ i*distanceBetweenTubes;
            topTubeRectangles[i] = new Rectangle();
            bottomTubeRectangles[i] = new Rectangle();
        }


    }
    @Override
    public void render () {
        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        if (gameState == 1) {

            if(tubeX[scoringTube]< Gdx.graphics.getWidth()/2){
                gameScore++;
                Gdx.app.log("Score", String.valueOf(gameScore));
                if(scoringTube < numberOfTubes - 1){
                    scoringTube++;
                }else{
                    scoringTube = 0;
                }
            }

            if (Gdx.input.justTouched()) {
                velocity = -33;  //adds to birds height and shoots it up into air
            }
            for(int i=0;i<numberOfTubes;i++) {
                if(tubeX[i] < -topTube.getWidth()){
                    tubeX[i] = numberOfTubes * distanceBetweenTubes;
                    tubeOffset[i] = (randomGenerator.nextFloat()- 0.5f)*(Gdx.graphics.getHeight() - gap - 200);
                }else {
                    tubeX[i] -= tubeVelocity;
                }
                batch.draw(topTube, tubeX[i], Gdx.graphics.getHeight() / 2 + gap / 2 + tubeOffset[i]);
                batch.draw(bottomTube, tubeX[i], Gdx.graphics.getHeight() / 2 - gap / 2 - bottomTube.getHeight() + tubeOffset[i]);
                topTubeRectangles[i] = new Rectangle(tubeX[i],Gdx.graphics.getHeight() / 2 + gap / 2 + tubeOffset[i], topTube.getWidth(), topTube.getHeight());
                bottomTubeRectangles[i] = new Rectangle(tubeX[i],Gdx.graphics.getHeight() / 2 - gap / 2 - bottomTube.getHeight() + tubeOffset[i], bottomTube.getWidth(), bottomTube.getHeight());
            }

            if(beeY > 0) { //either tap or bird being above bottom of screen should push it up
                velocity += gravity;
                beeY -= velocity;
            }else{
                gameState = 2; //gameover
            }
        }else if(gameState == 0){
            if (Gdx.input.justTouched()) {
                gameState = 1;
            }
        }else if(gameState == 2){
            index = index1;
            if(index == 0){
                str = "         Not Hap-bee?      ";
            }else if(index == 1){
                str = "     May-bee next time?   ";
            }else if(index == 2){
                str = "     Try Again, Honey!   ";
            }else if(index == 3){
                str = "   To bee or not to bee?  ";
            }else if (index == 4){
                str = "       Bee Alive!       ";
            }else if (index == 5){
                str = "Mind your own Bees-ness ";
            }
            fontMessage.draw(batch, str,75, 1250);
            batch.draw(gameover,Gdx.graphics.getWidth()/2 - gameover.getWidth()/2,Gdx.graphics.getHeight()/2 + 550 - gameover.getHeight()/2);
            if (Gdx.input.justTouched()) {
                gameState = 1;
                startGame();
                gameScore = 0;
                scoringTube = 0;
                velocity = 0;
            }
        }
        if (flapState == 0) {
            flapState = 1;
        } else {
            flapState = 0;
        }

        batch.draw(bees[flapState], Gdx.graphics.getWidth() / 2 - bees[flapState].getWidth() / 2, beeY);
        fontScore.draw(batch,String.valueOf(gameScore),100,200);
        batch.end();
        beeCircle.set(Gdx.graphics.getWidth()/2, beeY + bees[flapState].getHeight()/2, bees[flapState].getWidth()/2);
        //shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        //shapeRenderer.setColor(Color.RED);
        for(int i=0;i<numberOfTubes;i++){
            //shapeRenderer.rect(tubeX[i],Gdx.graphics.getHeight() / 2 + gap / 2 + tubeOffset[i], topTube.getWidth(), topTube.getHeight());
            //shapeRenderer.rect(tubeX[i],Gdx.graphics.getHeight() / 2 - gap / 2 - bottomTube.getHeight() + tubeOffset[i], bottomTube.getWidth(), bottomTube.getHeight());
            if(Intersector.overlaps(beeCircle,topTubeRectangles[i]) ||Intersector.overlaps(beeCircle,bottomTubeRectangles[i])) {
                gameState = 2;
            }
        }
        //shapeRenderer.circle(beeCircle.x,beeCircle.y,beeCircle.radius);
        //shapeRenderer.end();
    }

    @Override
    public void dispose () {
        batch.dispose();
        background.dispose();
    }
}
