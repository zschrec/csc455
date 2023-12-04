package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;

public class Assets implements Disposable, AssetErrorListener {

    public static final String TAG = Assets.class.getName();

    public static final Assets instance = new Assets();
    Texture texture;

    public ShipAssets shipAssets;
    public AsteroidsAssets asteroidsAssets;

    private Assets() {
    }

    public void init() {
        texture = new Texture(Gdx.files.internal(Constants.SPRITE_SHEET_FILE));
        shipAssets = new ShipAssets(texture);
        asteroidsAssets = new AsteroidsAssets(texture);
    }

    @Override
    public void error(AssetDescriptor asset, Throwable throwable) {
        Gdx.app.error(TAG, "Couldn't load asset: " + asset.fileName, throwable);
    }

    @Override
    public void dispose() {
        texture.dispose();
    }

    public class ShipAssets {

        public TextureRegion shipRegion;
        public TextureRegion bulletRegion;

        public ShipAssets(Texture texture) {
            shipRegion = new TextureRegion(texture, 4, 34, 24, 24);
            bulletRegion = new TextureRegion(texture, 6, 133, 4, 6);
        }
    }

    public class AsteroidsAssets {

        public TextureRegion largeAsteroidRegion;

        public AsteroidsAssets(Texture texture) {
            largeAsteroidRegion = new TextureRegion(texture, 66, 194, 58, 61);
        }
    }
}
