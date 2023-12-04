package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.badlogic.gdx.utils.ScreenUtils;

public class AsteroidsGame extends ApplicationAdapter {

	private DelayedRemovalArray<Asteroid> asteroids;
	private Ship ship;
	private final int maxAsteroids = 10;
	private final float maxAsteroidSpeed = 100f;
	private final float maxShipSpeed = 200f;
	private final float asteroidCreationDelay = 5;
	Asteroid asteroid;

	private SpriteBatch batch;
	@Override
	public void create () {
		asteroids = new DelayedRemovalArray<>(maxAsteroids);
		batch = new SpriteBatch();

		ship = new Ship(batch, new Vector2(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2));

		asteroid = new Asteroid(batch, 0, 0, Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight(), AsteroidType.LARGE);
		Assets.instance.init();
	}

	@Override
	public void render () {
		ScreenUtils.clear(0,0,0, 1);


		batch.begin();
		ship.render();
		asteroid.render();
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
