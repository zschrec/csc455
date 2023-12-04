package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Asteroid {

    private SpriteBatch batch;
    private Vector2 position;
    private float screenWidth;
    private float screenHeight;
    private final float asteroidForwardSpeed;
    private final float asteroidAngularSpeed;

    private AsteroidType type;

    public Asteroid(SpriteBatch batch, float asteroidForwardSpeed, float asteroidAngularSpeed, float screenWidth,
                    float screanHeight, AsteroidType type) {
        this.batch = batch;
        this.asteroidForwardSpeed = asteroidForwardSpeed;
        this.asteroidAngularSpeed = asteroidAngularSpeed;
        this.screenHeight = screanHeight;
        this.screenWidth = screenWidth;
        this.type = type;
        position = new Vector2(MathUtils.random(0, screenWidth), MathUtils.random(0, screanHeight));

    }

    public void updateScreenSize(float width, float height) {
        this.screenHeight = height;
        this.screenWidth = width;
    }

    public void update(float delta) {

    }

    public void render() {
        TextureRegion region;
        switch (type) {
            case LARGE:
                region = Assets.instance.asteroidsAssets.largeAsteroidRegion;
                break;
            default:
                return;
        }
        batch.draw(region, position.x - region.getRegionWidth()/2, position.y - region.getRegionHeight()/2);
    }

}
