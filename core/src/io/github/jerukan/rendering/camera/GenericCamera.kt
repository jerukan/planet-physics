package io.github.jerukan.rendering.camera

import com.badlogic.gdx.math.MathUtils
import io.github.jerukan.util.Functions
import kotlin.math.abs
import kotlin.math.sign

class GenericCamera: OrthoCameraWrapper() {

    val MAX_ZOOM = 3f
    val MIN_ZOOM = 0.1f
    val ZOOM_SPEED = 0.05f

    val MAX_VELOCITY = 5f
    val ZERO_SPEED_THRESHOLD = 0.1f
    val MAX_ACCELERATION = 0.15f

    private var camZoom = 1f
    private var camTargetZoom = 1f

    private var inputKeys: BooleanArray = BooleanArray(4) //[up, down, left, right]

    fun setInputKeys(keys: BooleanArray) {
        inputKeys = keys
    }

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
        if(inputKeys[2]) {
            acceleration.x = -MAX_ACCELERATION
        }
        if(inputKeys[3]) {
            acceleration.x = MAX_ACCELERATION
        }
        if(!inputKeys[2] && !inputKeys[3]) {
            if(abs(velocity.x) < ZERO_SPEED_THRESHOLD) {
                velocity.x = 0f
                acceleration.x = 0f
            }
            else {
                acceleration.x = MAX_ACCELERATION * -sign(velocity.x)
            }
        }
        if(inputKeys[0]) {
            acceleration.y = MAX_ACCELERATION
        }
        if(inputKeys[1]) {
            acceleration.y = -MAX_ACCELERATION
        }
        if(!inputKeys[0] && !inputKeys[1]) {
            if(abs(velocity.y) < ZERO_SPEED_THRESHOLD) {
                velocity.y = 0f
                acceleration.y = 0f
            }
            else {
                acceleration.y = MAX_ACCELERATION * -sign(velocity.y)
            }
        }
        velocity.add(acceleration)
        velocity.x = MathUtils.clamp(velocity.x, -MAX_VELOCITY, MAX_VELOCITY)
        velocity.y = MathUtils.clamp(velocity.y, -MAX_VELOCITY, MAX_VELOCITY)
    }

    override fun updateZoom() {
        if(!Functions.withinThreshold(camZoom, camTargetZoom, 0.1f)) {
            val zoomDir = sign(camTargetZoom - camera.zoom)
            camZoom += ZOOM_SPEED * zoomDir
            camZoom = MathUtils.clamp(camZoom, MIN_ZOOM, MAX_ZOOM)
        }
    }

    fun addTargetZoom(zoom: Float) {
        camTargetZoom += zoom
        MathUtils.clamp(camTargetZoom, MIN_ZOOM, MAX_ZOOM)
    }
}