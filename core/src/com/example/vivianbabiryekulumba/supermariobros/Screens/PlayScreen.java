package com.example.vivianbabiryekulumba.supermariobros.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.example.vivianbabiryekulumba.supermariobros.Scenes.Hud;
import com.example.vivianbabiryekulumba.supermariobros.Sprites.Mario;
import com.example.vivianbabiryekulumba.supermariobros.SuperMarioBros;
import com.example.vivianbabiryekulumba.supermariobros.tools.B2WorldCreator;
import com.example.vivianbabiryekulumba.supermariobros.tools.WorldContactListener;

import static com.example.vivianbabiryekulumba.supermariobros.SuperMarioBros.PIXELS_PER_METER;

public class PlayScreen implements Screen {

    private static final String TAG = "PlayScreen";
    private Mario player;
    private SuperMarioBros game;
    private Hud hud;
    private OrthographicCamera gameCam;
    private Viewport gamePort;
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private World world;
    private Box2DDebugRenderer debugRenderer;
    private TextureAtlas atlas;

    public PlayScreen(SuperMarioBros game){
        atlas = new TextureAtlas("Mario_and_Enemies.pack");
        this.game = game;
        gameCam = new OrthographicCamera();
        gamePort = new FitViewport(SuperMarioBros.V_WIDTH / PIXELS_PER_METER, SuperMarioBros.V_HEIGHT / PIXELS_PER_METER, gameCam);
        hud = new Hud(game.batch);
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("level1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / PIXELS_PER_METER);
        gameCam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);
        world = new World(new Vector2(0, -10), true);
        debugRenderer = new Box2DDebugRenderer();
        player = new Mario(world, this);
        new B2WorldCreator(world, map);

        world.setContactListener(new WorldContactListener());

    }

    @Override
    public void show() {

    }

    public TextureAtlas getAtlas(){
        return atlas;
    }

    public void handleInput(float dt){
        if(Gdx.input.justTouched()){
            player.b2Body.applyLinearImpulse(new Vector2(0, 3f), player.b2Body.getWorldCenter(), true);
        }
        if(Gdx.input.isTouched() && player.b2Body.getLinearVelocity().x <= 2)
            player.b2Body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2Body.getWorldCenter(), true);
        if(Gdx.input.isTouched() && player.b2Body.getLinearVelocity().x == -2)
            player.b2Body.applyLinearImpulse(new Vector2(0, -300f), player.b2Body.getWorldCenter(), true);
        if(Gdx.input.isTouched() && player.b2Body.getLinearVelocity().x > -2)
            player.b2Body.applyLinearImpulse(new Vector2(gameCam.direction.x, gameCam.direction.y), player.b2Body.getWorldCenter(), true);
    }

    public void update(float dt){
        handleInput(dt);
        world.step(1/60f, 6, 2);
        player.update(dt);
        gameCam.position.x = player.b2Body.getPosition().x;
        gameCam.update();//Always update camera every iteration of your render cycle
        renderer.setView(gameCam);
    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderer.render();
        debugRenderer.render(world, gameCam.combined);


        game.batch.setProjectionMatrix(gameCam.combined);
        game.batch.begin();
        player.draw(game.batch);
        game.batch.end();

        //Set our batch to now draw what the hud camera will see
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
    }

    public World getWorld(){
        return world;
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        world.dispose();
        debugRenderer.dispose();
        hud.dispose();
    }
}
