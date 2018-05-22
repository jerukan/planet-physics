package io.github.jerukan

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.SpriteBatch
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
//    var time: Double = 0.0

    override fun create() {
        batch = SpriteBatch()
        planetState = PlanetState()
        renderer = WorldRenderer(batch)
        Gdx.input.inputProcessor = Input(renderer)
        batch = SpriteBatch()
    }

    override fun render() {
//        time += Utils.Constants.t_step
        renderer.getCamera().update()
        batch.projectionMatrix.set(renderer.getOrthoCamera().combined)
        planetState.update()
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        batch.begin()
        renderer.render()
        batch.end()
    }

    override fun resize(width: Int, height: Int) {
        renderer.getOrthoCamera().setToOrtho(false)
    }

    override fun dispose() {
        batch.dispose()
        renderer.dispose()
    }

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