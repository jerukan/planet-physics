package io.github.jerukan

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import io.github.jerukan.physics.PhysicsObject
import io.github.jerukan.physics.PhysicsState
import io.github.jerukan.planetdata.Planet
import io.github.jerukan.planetdata.PlanetState
import io.github.jerukan.rendering.Drawable
import io.github.jerukan.rendering.WorldRenderer
import io.github.jerukan.util.Input

class Main : Game() {
    lateinit var batch: SpriteBatch
    lateinit var renderer: WorldRenderer
    lateinit var planetState: PlanetState

    val drawables: ArrayList<Drawable> = ArrayList()
    val planetList: ArrayList<Planet> = ArrayList()

    override fun create() {
        batch = SpriteBatch()
        planetState = PlanetState(planetList)
        renderer = WorldRenderer(drawables)
        Gdx.input.inputProcessor = Input(planetState, renderer)
        batch = SpriteBatch()

        add(Planet("bap", 500f, Vector2(0f, 0f), 50f))
        add(Planet("bip", 500f, Vector2(200f, 200f), 50f))
    }

    override fun render() {
        renderer.getCamera().update()
        batch.projectionMatrix.set(renderer.getOrthoCamera().combined)
        planetState.update()
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        batch.begin()
        renderer.render(batch)
        batch.end()
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