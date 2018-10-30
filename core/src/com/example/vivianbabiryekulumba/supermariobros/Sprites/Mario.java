package com.example.vivianbabiryekulumba.supermariobros.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.example.vivianbabiryekulumba.supermariobros.Screens.PlayScreen;

import static com.example.vivianbabiryekulumba.supermariobros.SuperMarioBros.PIXELS_PER_METER;

public class Mario extends Sprite {

    public enum State{
        FALLING,
        JUMPING,
        STANDING,
        RUNNING
    }

    public State currentState;
    public State previousState;
    public World world;
    public Body b2Body;
    private TextureRegion marioStand;
    private Animation<TextureRegion> marioRun;
    private Animation<TextureRegion> marioJump;
    private boolean runningRight;
    private float stateTimer;

    public Mario(World world, PlayScreen screen) {
        super(screen.getAtlas().findRegion("little_mario"));
        this.world = world;
        defineMario();
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = true;

        Array<TextureRegion> frames = new Array<TextureRegion>();

        for(int i = 1; i < 4; i++){
            frames.add(new TextureRegion(screen.getAtlas().findRegion("little_mario"), i * 16,0, 16, 16));
            marioRun = new Animation<TextureRegion>(0.1f, frames);
            frames.clear();
        }

        for(int i = 4; i < 6; i++){
            frames.add(new TextureRegion(screen.getAtlas().findRegion("little_mario"), i * 16, 0, 16, 16));
            marioJump = new Animation<TextureRegion>(0.1f, frames);
        }

        marioStand = new TextureRegion(getTexture(), 0, 10, 16, 16);

        setBounds(0, 0, 16 / PIXELS_PER_METER, 16 / PIXELS_PER_METER);
        setRegion(marioStand);
    }

    public void update(float dt){
        setPosition(b2Body.getPosition().x - getWidth() / 2, b2Body.getPosition().y - getHeight() / 3);
        setRegion(getFrame(dt));//will return the appropriate frame that we need to display as the sprites texture region.
    }

    private TextureRegion getFrame(float dt) {
        currentState = getState();
        TextureRegion region;
        switch (currentState) {
            case JUMPING:
                region = marioJump.getKeyFrame(stateTimer);
                break;
            case RUNNING:
                region = marioRun.getKeyFrame(stateTimer, true);
                break;
            case FALLING:
            case STANDING:
                default:
                    region = marioStand;
                    break;
        }

        if((b2Body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()){
            region.flip(true, false);
            runningRight = false;
        }else if((b2Body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()){
            region.flip(true, false);
            runningRight = true;
        }
        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        //if it doesn't equal the previous state then we must have transitioned to a new state and in that event,
        //we need to reset the timer.
        previousState = currentState;
        return region;
    }

    private State getState() {
        if(b2Body.getLinearVelocity().y > 0 || b2Body.getLinearVelocity().y < 0 && previousState == State.JUMPING)
            return State.JUMPING;
        else if(b2Body.getLinearVelocity().y < 0)
            return State.FALLING;
        else if(b2Body.getLinearVelocity().x != 0)
            return State.RUNNING;
        else
        return State.STANDING;
    }

    private void defineMario() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(64 / PIXELS_PER_METER, 32 / PIXELS_PER_METER);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        b2Body = world.createBody(bodyDef);
        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / PIXELS_PER_METER);
        fixtureDef.shape = shape;
        b2Body.createFixture(fixtureDef);
        EdgeShape head = new EdgeShape();//just a line between two different points
        head.set(new Vector2(-2 / PIXELS_PER_METER, 6 / PIXELS_PER_METER), new Vector2(2 / PIXELS_PER_METER, 6 / PIXELS_PER_METER));
        fixtureDef.shape = head;
        fixtureDef.isSensor = true;
        b2Body.createFixture(fixtureDef).setUserData("head");
    }

}
