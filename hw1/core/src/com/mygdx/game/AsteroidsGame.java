package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.badlogic.gdx.utils.ScreenUtils;

public class AsteroidsGame extends ApplicationAdapter {

	private DelayedRemovalArray<Asteroid> asteroids;
	private Ship ship;
	private final int maxAsteroids = 10;
	private final float minAsteroidSpeed = 150f;
	private final float maxAsteroidSpeed = 150f;
	private final float maxShipSpeed = 200f;
	private final float maxShipRotationSpeed = 100f;

	private SpriteBatch batch;
	@Override
	public void create () {
		asteroids = new DelayedRemovalArray<>(maxAsteroids);
		batch = new SpriteBatch();

		ship = new Ship(batch, new Vector2(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2), maxShipSpeed,
				maxShipRotationSpeed);

		for (int i = 0; i < maxAsteroids; ++i) {
			asteroids.add(new Asteroid(batch, MathUtils.random(minAsteroidSpeed, maxAsteroidSpeed), 0, Gdx.graphics.getWidth(),
					Gdx.graphics.getHeight(), AsteroidType.LARGE));
		}
		Gdx.input.setInputProcessor(ship);
		Assets.instance.init();
	}

	@Override
	public void render () {
		ScreenUtils.clear(0,0,0, 1);
		float delta = Gdx.graphics.getDeltaTime();

		for (Asteroid asteroid : asteroids) {
			asteroid.update(delta);
		}
		ship.update(delta);

		batch.begin();
		ship.render();
		for (Asteroid asteroid : asteroids) {
			asteroid.render();
		}
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
