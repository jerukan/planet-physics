package io.github.jerukan.util

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.math.MathUtils

class CameraWrapper(val camera: OrthographicCamera) {

    var camOriginX: Float = 0.toFloat()
    var camOriginY: Float = 0.toFloat()

    var camOffsetX: Float = 0.toFloat()
    var camOffsetY: Float = 0.toFloat()

    private var camVelX = 0f
    private var camVelY = 0f

    private var camAccelX = 0f
    private var camAccelY = 0f

    var camSlowingX = false
    var camSlowingY = false

    private var camZoom = 1f
    private var camTargetZoom = 1f

    fun init() {

        camOriginX = (Gdx.graphics.width / 2).toFloat()
        camOriginY = (Gdx.graphics.height / 2).toFloat()

        camOffsetX = camera.position.x - camOriginX
        camOffsetY = camera.position.y - camOriginY
    }

    fun update() {
        updateOffsets()

        if (camSlowingX || camSlowingY) {
            slowCamera()
        }
        setCameraVelX()
        setCameraVelY()
        zoomBoardCamera()

        camera.translate(camVelX * camera.zoom, camVelY * camera.zoom)

        camera.zoom = MathUtils.clamp(camZoom, 0.1f, 6f)

        camera.update()
    }

    fun setCameraVelX() {
        if (Math.abs(camVelX) > 5) {
            camVelX = MathUtils.clamp(camVelX, -5f, 5f)
        } else {
            camVelX += camAccelX
        }
    }


    fun setCameraVelY() {
        if (Math.abs(camVelY) > 5) {
            camVelY = MathUtils.clamp(camVelY, -5f, 5f)
        } else {
            camVelY += camAccelY
        }
    }

    fun setCamAccelX(x: Float) {
        camAccelX = x
    }

    fun setCamAccelY(y: Float) {
        camAccelY = y
    }

    fun slowCamera() {
        val xdir = Math.abs(camVelX) / camVelX
        val ydir = Math.abs(camVelY) / camVelY
        if (camAccelX == 0f) {
            if (Math.abs(camVelX) >= 0.3f) {
                camVelX -= 0.25f * xdir
            } else {
                camVelX = 0f
            }
        }
        if (camAccelY == 0f) {
            if (Math.abs(camVelY) >= 0.3f) {
                camVelY -= 0.25f * ydir
            } else {
                camVelY = 0f
            }
        }
    }

    fun setCamTargetZoom(`val`: Float) {
        camTargetZoom += `val`
        camTargetZoom = MathUtils.clamp(camTargetZoom, 0.1f, 6f)
    }

    fun zoomBoardCamera() {
        if (Math.abs(camZoom) != camTargetZoom) {
            camZoom += 0.05f * (Math.abs(camTargetZoom - camera.zoom) / (camTargetZoom - camera.zoom))
            camZoom = MathUtils.clamp(camZoom, 0.1f, 6f)
        }
    }

    fun updateOffsets() {
        camOffsetX = camera.position.x - camOriginX
        camOffsetY = camera.position.y - camOriginY
    }
}
