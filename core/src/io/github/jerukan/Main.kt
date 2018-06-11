package io.github.jerukan

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import io.github.jerukan.physics.PhysicsObject
import io.github.jerukan.physics.PhysicsState
import io.github.jerukan.planetdata.Planet
import io.github.jerukan.planetdata.PlanetState
import io.github.jerukan.rendering.Drawable
import io.github.jerukan.rendering.WorldRenderer
import io.github.jerukan.screens.SimScreen
import io.github.jerukan.ui.UIManager
import io.github.jerukan.util.Input

class Main : Game() {

    override fun create() {
        setScreen(SimScreen())
    }
}