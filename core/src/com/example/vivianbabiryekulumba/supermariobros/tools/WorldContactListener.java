package com.example.vivianbabiryekulumba.supermariobros.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.example.vivianbabiryekulumba.supermariobros.Sprites.InteractiveTileObject;

//ContactListener is what gets called when two fixtures collide with each other in Box2d
public class WorldContactListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        //beginContact gets called when two fixtures begin to make a connection
        //or begin to collide
        Gdx.app.log("Begin contact: ", "");
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        if(fixtureA.getUserData() == "head" || fixtureB.getUserData() == "head"){
            Fixture head = fixtureA.getUserData() == "head" ? fixtureA : fixtureB;
            Fixture object = head == fixtureA ? fixtureB : fixtureA;

            if(object.getUserData() instanceof InteractiveTileObject){
                ((InteractiveTileObject) object.getUserData()).onHeadHit();
            }
        }

    }

    @Override
    public void endContact(Contact contact) {
        //endContact gets called when two fixtures disconnect from each other

        Gdx.app.log("End contact: ", "");
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        //once something has collided you can change the characteristics of that collision

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        //gives the results of what happened due to that collision like for example
        //what angles the fixtures went off at etc.
    }
}
