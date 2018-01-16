package io.github.jerukan

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import io.github.jerukan.planetdata.Planets
import io.github.jerukan.util.CameraWrapper
import io.github.jerukan.util.Utils
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch


object Renderer {
    var shapeRenderer: ShapeRenderer = ShapeRenderer()
    var camera: OrthographicCamera = OrthographicCamera(Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
    var camwrapper: CameraWrapper = CameraWrapper(camera)
//        var originx: Float = Gdx.graphics.width.toFloat() / 2f
//        var originy: Float = Gdx.graphics.height.toFloat() / 2f
    var originx: Float = 0f
    var originy: Float = 0f

    var font = BitmapFont()

    fun init() {
        camwrapper.init()
    }

    fun render(batch: SpriteBatch) {
        shapeRenderer.projectionMatrix.set(camwrapper.camera.combined)
        shapeRenderer.updateMatrices()

        shapeRenderer.setAutoShapeType(true)
        Gdx.gl20.glEnable(GL20.GL_BLEND)
        Gdx.gl20.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA)

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)
        for(planet in Planets.planetlist) {
            planet.display(shapeRenderer)
        }
        shapeRenderer.end()
        Gdx.gl20.glDisable(GL20.GL_BLEND)
        batch.begin()

        batch.end()
    }
}