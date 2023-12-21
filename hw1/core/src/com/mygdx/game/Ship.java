package com.mygdx.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Ship implements InputProcessor {

    private Vector2 position;
    private SpriteBatch batch;

    private final float maxSpeed;
    private final float maxRotationSpeed;
    private float rotation;
    private int movingForward;
    private int rotationDirection;

    public Ship(SpriteBatch batch, Vector2 position, float maxSpeed, float maxRotationSpeed) {
        this.batch = batch;
        this.position = position;
        this.maxSpeed = maxSpeed;
        this.maxRotationSpeed = maxRotationSpeed;
        this.rotation = MathUtils.random(0, MathUtils.PI2);
        this.movingForward = 0;
        this.rotationDirection = 0;
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
                -rotation,
                region.getRegionX(),
                region.getRegionY(),
                region.getRegionWidth(),
                region.getRegionHeight(),
                false,
                false);
    }

    public void update(float delta) {
        this.rotation += this.rotationDirection * maxRotationSpeed * delta;

        this.position.x += movingForward * getForwardVector().x * maxSpeed * delta;
        this.position.y += movingForward * getForwardVector().y * maxSpeed * delta;
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
                rotationDirection = -1;
                break;
            case Input.Keys.D:
                rotationDirection = 1;
                break;
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.W:
                movingForward = 0;
                break;
            case Input.Keys.S:
                movingForward = 0;
                break;
            case Input.Keys.A:
                rotationDirection = 0;
                break;
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
        return new Vector2(MathUtils.cos(rotation), MathUtils.sin(rotation));
    }
}
