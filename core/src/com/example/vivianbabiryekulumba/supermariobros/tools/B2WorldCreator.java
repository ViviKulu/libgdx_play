package com.example.vivianbabiryekulumba.supermariobros.tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.example.vivianbabiryekulumba.supermariobros.Sprites.Brick;
import com.example.vivianbabiryekulumba.supermariobros.Sprites.Coin;
import com.example.vivianbabiryekulumba.supermariobros.Sprites.SinkingSand;

import static com.example.vivianbabiryekulumba.supermariobros.SuperMarioBros.PIXELS_PER_METER;

public class B2WorldCreator {

    public B2WorldCreator(World world, TiledMap map) {

        //Add bodies and fixtures to the game world, fine to put them in the constructor for now
        //but later on, you'll need to put them into their own classes.
        BodyDef bodyDef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fixtureDef = new FixtureDef();
        Body body;

        //Create ground bodies/fixtures
        for (MapObject mapObject : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = ((RectangleMapObject) mapObject).getRectangle();
            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set((rectangle.getX() + rectangle.getWidth() / 2) / PIXELS_PER_METER, (rectangle.getY() + rectangle.getHeight() / 2) / PIXELS_PER_METER);
            body = world.createBody(bodyDef);
            shape.setAsBox(rectangle.getWidth() / 2 / PIXELS_PER_METER, rectangle.getHeight() / 2 / PIXELS_PER_METER);
            fixtureDef.shape = shape;
            body.createFixture(fixtureDef);
        }

        //create pipe bodies/fixtures
        //maybe when Mario falls on top of the pipes, they suck you in.(challenge)
        for (MapObject mapObject : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = ((RectangleMapObject) mapObject).getRectangle();
            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set((rectangle.getX() + rectangle.getWidth() / 2) / PIXELS_PER_METER, (rectangle.getY() + rectangle.getHeight() / 2) / PIXELS_PER_METER);
            body = world.createBody(bodyDef);
            shape.setAsBox(rectangle.getWidth() / 2 / PIXELS_PER_METER, rectangle.getHeight() / 2 / PIXELS_PER_METER);
            fixtureDef.shape = shape;
            body.createFixture(fixtureDef);
        }

        //create brick bodies/fixtures
        for (MapObject mapObject : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = ((RectangleMapObject) mapObject).getRectangle();
            new Brick(world, map, rectangle);
        }

        //create coin bodies/fixtures
        for (MapObject mapObject : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = ((RectangleMapObject) mapObject).getRectangle();
            new Coin(world, map, rectangle);
        }

        //create sinking sands bodies/fixtures
        for (MapObject mapObject : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = ((RectangleMapObject) mapObject).getRectangle();
            new SinkingSand(world, map, rectangle);
        }

        //create steps bodies/fixtures
        for (MapObject mapObject : map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = ((RectangleMapObject) mapObject).getRectangle();
            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set((rectangle.getX() + rectangle.getWidth() / 2) / PIXELS_PER_METER, (rectangle.getY() + rectangle.getHeight() / 2) / PIXELS_PER_METER);
            body = world.createBody(bodyDef);
            shape.setAsBox(rectangle.getWidth() / 2 / PIXELS_PER_METER, rectangle.getHeight() / 2 / PIXELS_PER_METER);
            fixtureDef.shape = shape;
            body.createFixture(fixtureDef);
        }

    }
}
