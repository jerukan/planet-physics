package io.github.jerukan.ui

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.viewport.ScreenViewport
import io.github.jerukan.planetdata.PlanetState
import io.github.jerukan.util.Assets

class UIManager(batch: SpriteBatch, var planetState: PlanetState) {

    private val viewport: ScreenViewport = ScreenViewport()
    val stage: Stage = Stage(viewport, batch)

    private val infoLabel: Label = Label("None", Assets.uiSkin, "default")
    private val disclaimer: Label = Label("Disclaimer: This simulation does not have proper physics.\nI'm too lazy to actually do it.", Assets.uiSkin, "default")
//    private val font: BitmapFont = BitmapFont()

    init {
        val table = Table()
        table.width = 100f
        table.add(infoLabel).pad(20f).row()
        table.add(disclaimer)
        table.setPosition(20f, Gdx.graphics.height - 40f)
        table.align(Align.topLeft)

        stage.addActor(table)
    }

    fun render() {
        infoLabel.setText("Use WASD to move around\nScroll to zoom\nPress F to add a planet at the mouse's position\nCurrent warp speed: ${planetState.warp}")
        stage.draw()
    }
}