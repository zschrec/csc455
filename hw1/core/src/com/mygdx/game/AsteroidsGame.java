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
	private final float maxShipRotationSpeed = 200f;

	private boolean gameOver = false;
	private boolean won = false;

	private float gameOverTime = 0;

	private float startInvincibility = 2;

	private SpriteBatch batch;
	@Override
	public void create () {
		asteroids = new DelayedRemovalArray<>(maxAsteroids);
		batch = new SpriteBatch();

		ship = new Ship(batch, new Vector2(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2), maxShipSpeed,
				maxShipRotationSpeed, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), asteroids);

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
			if (asteroid.isActive()) {
				asteroid.update(delta);
			} else {
				asteroids.removeValue(asteroid, true);
			}
		}
		if (asteroids.size == 0 && !won) {
			System.out.println("You Win!");
			won = true;
		}
		ship.update(delta);

		if (startInvincibility > 0) {
			startInvincibility -= delta;
		} else {
			if (ship.asteroidCollision() && !gameOver) {
				System.out.println("Game over");
				gameOver = true;
			}
		}

		if (gameOver || won) {
			gameOverTime += delta;
			if (gameOverTime > 3) {
				Gdx.app.exit();
			}
		}

		batch.begin();
		if (!gameOver) {
			ship.render();
		}
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
