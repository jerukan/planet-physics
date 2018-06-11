package io.github.jerukan.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import io.github.jerukan.physics.PhysicsObject
import io.github.jerukan.planetdata.Planet
import io.github.jerukan.planetdata.PlanetState
import io.github.jerukan.rendering.Drawable
import io.github.jerukan.rendering.WorldRenderer
import io.github.jerukan.ui.UIManager
import io.github.jerukan.util.Input

class SimScreen: Screen {

    lateinit var batch: SpriteBatch
    lateinit var renderer: WorldRenderer
    lateinit var planetState: PlanetState
    lateinit var uiManager: UIManager

    lateinit var inputs: InputMultiplexer

    val drawables: ArrayList<Drawable> = ArrayList()
    val planetList: ArrayList<Planet> = ArrayList()

    override fun hide() {
        dispose()
    }

    override fun show() {
        batch = SpriteBatch()
        planetState = PlanetState(planetList, drawables)
        renderer = WorldRenderer(drawables)
        uiManager = UIManager(batch, planetState)
        inputs = InputMultiplexer()
        inputs.addProcessor(uiManager.stage)
        inputs.addProcessor(Input(planetState, renderer))
        Gdx.input.inputProcessor = inputs
        batch = SpriteBatch()

        val p1 = Planet("bap", 500f, Vector2(250f, 250f), 50f)
//        p1.stationary = true
        val p2 = Planet("bip", 500f, Vector2(400f, 250f), 50f)
        p2.setCircularOrbit(p1)
        add(p1)
        add(p2)
    }

    override fun render(delta: Float) {
        renderer.getCamera().update()
        batch.projectionMatrix.set(renderer.getOrthoCamera().combined)
        planetState.update()
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        batch.begin()
        renderer.render(batch)
        batch.end()
        uiManager.render()
    }

    override fun pause() {
    }

    override fun resume() {
    }

    override fun resize(width: Int, height: Int) {
        renderer.getOrthoCamera().setToOrtho(false)
    }

    override fun dispose() {
        batch.dispose()
        renderer.dispose()
    }

    /**
     * Modified to specifically add planets.
     * @param obj anything that is a PhysicsObject, Drawable, or both.
     */
    fun add(obj: Any) {
        if(obj is Planet) {
            planetState.add(obj)
        }
        else if(obj is PhysicsObject) {
            planetState.add(obj)
        }
        if(obj is Drawable) {
            renderer.add(obj)
        }
    }
}