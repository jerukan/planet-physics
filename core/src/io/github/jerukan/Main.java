package io.github.jerukan;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import io.github.jerukan.physics.PhysicsObject;
import io.github.jerukan.physics.PhysicsState;
import io.github.jerukan.planetdata.Planet;
import io.github.jerukan.planetdata.PlanetState;
import io.github.jerukan.rendering.Drawable;
import io.github.jerukan.rendering.WorldRenderer;
import io.github.jerukan.ui.UIManager;
import io.github.jerukan.util.CameraInput;

import java.util.ArrayList;

public class Main extends Game {
    private SpriteBatch batch;
    private WorldRenderer renderer;
    private PlanetState planetState;
    private UIManager uiManager;

    private InputMultiplexer inputs;

    private ArrayList<Drawable> drawables = new ArrayList<>();
    private ArrayList<Planet> planetList = new ArrayList<>();

    @Override
    public void create() {
        batch = new SpriteBatch();
        planetState = new PlanetState(planetList, drawables);
        renderer = new WorldRenderer(drawables);
        uiManager = new UIManager(batch, planetState);
        inputs = new InputMultiplexer();
        inputs.addProcessor(uiManager.stage);
        inputs.addProcessor(new CameraInput(planetState, renderer));
        Gdx.input.setInputProcessor(inputs);

        Planet p1 = new Planet("bap", 500f, new Vector2(250f, 250f), 50f);
        Planet p2 = new Planet("bip", 5f, new Vector2(400f, 250f), 5f);
        p2.setCircularOrbit(p1);
        add(p1);
        add(p2);
    }

    @Override
    public void render() {
        renderer.getCamera().update();
        batch.getProjectionMatrix().set(renderer.getOrthoCamera().combined);
        planetState.update();
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        renderer.render(batch);
        batch.end();
        uiManager.render();
    }

    @Override
    public void resize(int width, int height) {
        renderer.getOrthoCamera().setToOrtho(false);
    }

    @Override
    public void dispose() {
        batch.dispose();
        renderer.dispose();
    }

    /**
     * Modified to specifically add planets.
     * @param obj anything that is a PhysicsObject, Drawable, or both.
     */
    public void add(Object obj) {
        if(obj instanceof Planet) {
            planetState.add((Planet) obj);
        }
        else if(obj instanceof PhysicsObject) {
            planetState.add((PhysicsObject)obj);
        }
        if(obj instanceof Drawable) {
            renderer.add((Drawable) obj);
        }
    }
}