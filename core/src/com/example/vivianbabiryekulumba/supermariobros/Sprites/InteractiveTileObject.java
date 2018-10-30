package com.example.vivianbabiryekulumba.supermariobros.Sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import static com.example.vivianbabiryekulumba.supermariobros.SuperMarioBros.PIXELS_PER_METER;

public abstract class InteractiveTileObject {

    protected World world;
    protected TiledMap map;
    protected TiledMapTile tile;
    protected Rectangle bounds;
    protected Body body;
    protected Fixture fixture;

    public InteractiveTileObject(World world, TiledMap map, Rectangle bounds) {
        this.world = world;
        this.map = map;
        this.bounds = bounds;

        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape polygonShape = new PolygonShape();

        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set((bounds.getX() + bounds.getWidth() / 2) / PIXELS_PER_METER, (bounds.getY() + bounds.getHeight() / 2) / PIXELS_PER_METER);
        body = world.createBody(bodyDef);
        polygonShape.setAsBox(bounds.getWidth() / 2 / PIXELS_PER_METER, bounds.getHeight() / 2 / PIXELS_PER_METER);
        fixtureDef.shape = polygonShape;
        fixture = body.createFixture(fixtureDef);
    }
    public abstract void onHeadHit();
}
