package com.mygdx.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.DelayedRemovalArray;

public class Ship implements InputProcessor {

    private Vector2 position;
    private SpriteBatch batch;

    private float screenWidth;
    private float screenHeight;
    private final float maxSpeed;
    private final float maxRotationSpeed;
    private float rotation;
    private int movingForward;
    private int rotationDirection;
    private float shootCoolDown = 0.5f;
    private float shootCoolDownTimer = 0f;
    private DelayedRemovalArray<Bullet> bullets = new DelayedRemovalArray<>();
    private DelayedRemovalArray<Asteroid> asteroids;



    public Ship(SpriteBatch batch, Vector2 position, float maxSpeed, float maxRotationSpeed, float screenWidth,
                float screenHeight, DelayedRemovalArray<Asteroid> asteroids) {
        this.batch = batch;
        this.position = position;
        this.maxSpeed = maxSpeed;
        this.maxRotationSpeed = maxRotationSpeed;
        this.rotation = MathUtils.random(0, 360f);
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
        this.movingForward = 0;
        this.rotationDirection = 0;
        this.asteroids = asteroids;
    }

    public void render() {
        TextureRegion region = Assets.instance.shipAssets.shipRegion;
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

        for (Bullet bullet : bullets) {
            bullet.render();
        }
    }

    public void update(float delta) {
        this.rotation += this.rotationDirection * maxRotationSpeed * delta;

        this.position.x += movingForward * getForwardVector().x * maxSpeed * delta;
        if (position.x > screenWidth) {
            position.x -= screenWidth;
        } else if (position.x < 0) {
            position.x += screenWidth;
        }

        this.position.y += movingForward * getForwardVector().y * maxSpeed * delta;
        if (position.y > screenHeight) {
            position.y -= screenHeight;
        } else if (position.y < 0) {
            position.y += screenHeight;
        }

        if (shootCoolDownTimer > 0) {
            shootCoolDownTimer -= delta;
        }

        for (Bullet bullet : bullets) {
            if (bullet.isActive()) {
                bullet.update(delta);
            } else {
                bullets.removeValue(bullet, true);
            }
        }

    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.W:
                movingForward = 1;
                break;
            case Input.Keys.S:
                movingForward = -1;
                break;
            case Input.Keys.A:
                rotationDirection = 1;
                break;
            case Input.Keys.D:
                rotationDirection = -1;
                break;
            case Input.Keys.SPACE:
                shoot();
                break;
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.W:
            case Input.Keys.S:
                movingForward = 0;
                break;
            case Input.Keys.A:
            case Input.Keys.D:
                rotationDirection = 0;
                break;
        }
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

    private Vector2 getForwardVector() {
        float radianRotation = rotation * MathUtils.PI / 180f;
        return new Vector2(-MathUtils.sin(radianRotation), MathUtils.cos(radianRotation));
    }

    public boolean asteroidCollision() {
        float shipHeight = Assets.instance.shipAssets.shipRegion.getRegionHeight();
        float shipWidth = Assets.instance.shipAssets.shipRegion.getRegionWidth();
        float shipRadius = (shipWidth + shipHeight)/4;
        for (Asteroid asteroid : asteroids) {
            float asteroidHeight = Assets.instance.asteroidsAssets.largeAsteroidRegion.getRegionHeight();
            float asteroidWidth = Assets.instance.asteroidsAssets.largeAsteroidRegion.getRegionWidth();
            float asteroidRadius = (asteroidHeight + asteroidWidth)/4;

            float distance = position.dst(asteroid.getPosition());
            if (distance < shipRadius + asteroidRadius) {
                return true;
            }


        }

        return false;
    }

    private void shoot() {
        if (shootCoolDownTimer <= 0) {
            Bullet bullet = new Bullet(batch, position.cpy(), getForwardVector(), screenWidth, screenHeight, asteroids);
            bullets.add(bullet);
            shootCoolDownTimer = shootCoolDown;
        }
    }
}
