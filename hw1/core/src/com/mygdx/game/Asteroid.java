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

    private float rotation;

    private boolean isActive = true;
    private AsteroidType type;

    public Asteroid(SpriteBatch batch, float asteroidForwardSpeed, float asteroidAngularSpeed, float screenWidth,
                    float screenHeight, AsteroidType type) {
        this.batch = batch;
        this.asteroidForwardSpeed = asteroidForwardSpeed;
        this.asteroidAngularSpeed = asteroidAngularSpeed;
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
        this.type = type;
        this.rotation = MathUtils.random(0, MathUtils.PI2);
        position = new Vector2(MathUtils.random(0, screenWidth), MathUtils.random(0, screenHeight));

    }

    public void updateScreenSize(float width, float height) {
        this.screenHeight = height;
        this.screenWidth = width;
    }

    public void update(float delta) {
        position.x += getForwardVector().x * this.asteroidForwardSpeed * delta;
        if (position.x > screenWidth) {
            position.x -= screenWidth;
        } else if (position.x < 0) {
            position.x += screenWidth;
        }
        position.y += getForwardVector().y * this.asteroidForwardSpeed * delta;
        if (position.y > screenHeight) {
            position.y -= screenHeight;
        } else if (position.y < 0) {
            position.y += screenHeight;
        }

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

    private Vector2 getForwardVector() {
        return new Vector2(MathUtils.cos(rotation), MathUtils.sin(rotation));
    }

    public Vector2 getPosition() {
        return position;
    }

    public boolean isActive() {
        return isActive;
    }

    public void destroyed() {
        isActive = false;
    }
}
