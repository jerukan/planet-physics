package io.github.jerukan.rendering

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import io.github.jerukan.rendering.camera.OrthoCameraWrapper

/**
 * Any object in the game that is to be drawn.
 */
interface Drawable {

    fun render(batch: SpriteBatch)

    fun inCameraWindow(camera: OrthoCameraWrapper): Boolean

    fun dispose()
}