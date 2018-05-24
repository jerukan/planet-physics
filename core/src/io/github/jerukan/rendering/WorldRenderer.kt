package io.github.jerukan.rendering

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import io.github.jerukan.rendering.camera.GenericCamera
import io.github.jerukan.rendering.camera.OrthoCameraWrapper

class WorldRenderer(private val drawables: ArrayList<Drawable>) {

    private val camera: OrthoCameraWrapper = GenericCamera()

    fun add(drawable: Drawable) {
        drawables.add(drawable)
    }

    fun render(batch: SpriteBatch) {
        for(drawable in drawables) {
            if(drawable.inCameraWindow(camera)) {
                drawable.render(batch)
            }
        }
    }

    fun dispose() {
        for(drawable in drawables) {
            drawable.dispose()
        }
    }

    fun getCamera(): GenericCamera {
        return camera as GenericCamera
    }

    fun getOrthoCamera(): OrthographicCamera {
        return camera.camera
    }
}