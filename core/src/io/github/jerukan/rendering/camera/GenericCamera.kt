package io.github.jerukan.rendering.camera

import com.badlogic.gdx.math.MathUtils
import io.github.jerukan.util.Utils.Functions
import kotlin.math.sign

class GenericCamera: OrthoCameraWrapper() {

    private val MAX_ZOOM = 3f
    private val MIN_ZOOM = 0.1f
    private val ZOOM_SPEED = 0.05f

    private val MAX_VELOCITY = 5f
    private val ZERO_SPEED_THRESHOLD = 0.2f
    private val MAX_ACCELERATION = 0.6f

    private var camZoom = 1f
    private var camTargetZoom = 1f

    override fun update() {
        updateOffsets()

        updateVectors()
        updateZoom()

        camera.translate(velocity.x * camera.zoom, velocity.y * camera.zoom)
        camera.zoom = MathUtils.clamp(camZoom, MIN_ZOOM, MAX_ZOOM)

        camera.update()

        hitbox.setPosition(camera.position.x - camera.viewportWidth / 2, camera.position.y - camera.viewportHeight / 2)
    }

    override fun updateVectors() {
        velocity.add(acceleration)
    }

    override fun updateZoom() {
        if(!Functions.withinThreshold(camZoom, camTargetZoom, 0.1f)) {
            val zoomDir = sign(camTargetZoom - camera.zoom)
            camZoom += ZOOM_SPEED * zoomDir
            camZoom = MathUtils.clamp(camZoom, MIN_ZOOM, MAX_ZOOM)
        }
    }

    fun setCamAccelX(x: Float) {
        acceleration.x = x
    }

    fun setCamAccelY(y: Float) {
        acceleration.y = y
    }

    fun addTargetZoom(zoom: Float) {
        camTargetZoom += zoom
        MathUtils.clamp(camTargetZoom, MIN_ZOOM, MAX_ZOOM)
    }
}