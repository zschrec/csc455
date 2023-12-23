package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Bullet {

    SpriteBatch batch;
    private Vector2 position;
    private Vector2 forwardVector;
    private float screenWidth;
    private float screenHeight;
    private Array<Asteroid> asteroids;
    private float rotation;
    private boolean isActive = true;
    private float speed = 300f;

    public Bullet(SpriteBatch batch, Vector2 position, Vector2 forwardVector, float screenWidth, float sceenHeight,
                  Array<Asteroid> asteroids) {
        this.batch = batch;
        this.position = position;
        this.forwardVector = forwardVector;
        this.rotation = MathUtils.atan2(forwardVector.y, forwardVector.x) * 180f/MathUtils.PI;
        this.screenWidth = screenWidth;
        this.screenHeight = sceenHeight;
        this.asteroids = asteroids;
    }

    public void update(float delta) {
        this.position.x += forwardVector.x * speed * delta;
        if (position.x > screenWidth || position.x < 0) {
            isActive = false;
        }

        this.position.y += forwardVector.y * speed * delta;
        if (position.y > screenHeight || position.y < 0) {
            isActive = false;
        }

        checkAsteroidCollision();

    }

    public void render() {
        TextureRegion region = Assets.instance.shipAssets.bulletRegion;
        batch.draw(
                region.getTexture(),
                position.x - region.getRegionWidth()/2,
                position.y - region.getRegionHeight()/2,
                region.getRegionWidth()/2,
                region.getRegionHeight()/2,
                region.getRegionWidth(),
                region.getRegionHeight(),
                1,
                1,
                rotation,
                region.getRegionX(),
                region.getRegionY(),
                region.getRegionWidth(),
                region.getRegionHeight(),
                false,
                false);
    }

    public boolean isActive() {
        return isActive;
    }

    private void checkAsteroidCollision() {
        float bulletHeight = Assets.instance.shipAssets.bulletRegion.getRegionHeight();
        float bulletWidth = Assets.instance.shipAssets.bulletRegion.getRegionWidth();
        float bulletRadius = (bulletWidth + bulletHeight)/4;
        for (Asteroid asteroid : asteroids) {
            float asteroidHeight = Assets.instance.asteroidsAssets.largeAsteroidRegion.getRegionHeight();
            float asteroidWidth = Assets.instance.asteroidsAssets.largeAsteroidRegion.getRegionWidth();
            float asteroidRadius = (asteroidHeight + asteroidWidth)/4;

            float distance = position.dst(asteroid.getPosition());
            if (distance < bulletRadius + asteroidRadius) {
                asteroid.destroyed();
                isActive = false;
                return;
            }


        }
    }

}
