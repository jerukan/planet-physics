package io.github.jerukan.rendering.camera

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.math.Vector2
import io.github.jerukan.util.shapes.Rectangle

/**
 * Wraps an [OrthographicCamera] with easier access to movement and modification of the camera.
 */
abstract class OrthoCameraWrapper {
    val camera: OrthographicCamera = OrthographicCamera(Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())

    val hitbox: Rectangle = Rectangle(Vector2(camera.position.x - camera.viewportWidth / 2, camera.position.y - camera.viewportHeight / 2), camera.viewportWidth, camera.viewportHeight)

    val velocity: Vector2 = Vector2()
    val acceleration: Vector2 = Vector2()

    var zoom: Float = 1f

    var camOriginX: Float = 0f
    var camOriginY: Float = 0f

    var camOffsetX: Float = 0f
    var camOffsetY: Float = 0f

    /**
     * Will be called every frame to update camera information.
     */
    abstract fun update()

    /**
     * Specific method to update camera vectors such as position and velocity.
     */
    abstract fun updateVectors()

    /**
     * Specific method to update camera zoom.
     */
    abstract fun updateZoom()

    fun updateOrigin() {
        camOriginX = (Gdx.graphics.width / 2).toFloat()
        camOriginY = (Gdx.graphics.height / 2).toFloat()

        updateOffsets()
    }

    fun updateOffsets() {
        camOffsetX = camera.position.x - camOriginX
        camOffsetY = camera.position.y - camOriginY
    }

    fun resize(width: Int, height: Int) {
        camera.setToOrtho(false, width.toFloat(), height.toFloat())
        updateOrigin()
    }
}