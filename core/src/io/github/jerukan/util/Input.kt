package io.github.jerukan.util

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.math.Vector2
import io.github.jerukan.physics.PhysicsState
import io.github.jerukan.planetdata.Planet
import io.github.jerukan.rendering.WorldRenderer

class Input(val physicsState: PhysicsState, val renderer: WorldRenderer): InputProcessor {

    val pressedKeys = BooleanArray(4) //[up, down, left, right]

    val maxAccel = renderer.getCamera().MAX_ACCELERATION

    init {
        renderer.getCamera().setInputKeys(pressedKeys)
    }

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        return false
    }

    override fun mouseMoved(screenX: Int, screenY: Int): Boolean {
        return false
    }

    override fun keyTyped(character: Char): Boolean {
        return false
    }

    override fun scrolled(amount: Int): Boolean {
        if (amount == 1) {
            renderer.getCamera().addTargetZoom(0.05f)
        } else if (amount == -1) {
            renderer.getCamera().addTargetZoom(-0.05f)
        }
        return false
    }

    override fun keyUp(keycode: Int): Boolean {
        if (keycode == Input.Keys.LEFT || keycode == Input.Keys.A) {
            pressedKeys[2] = false
        }
        if (keycode == Input.Keys.RIGHT || keycode == Input.Keys.D) {
            pressedKeys[3] = false
        }
        if (keycode == Input.Keys.UP || keycode == Input.Keys.W) {
            pressedKeys[0] = false
        }
        if (keycode == Input.Keys.DOWN || keycode == Input.Keys.S) {
            pressedKeys[1] = false
        }
        return false
    }

    override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean {
        return false
    }

    override fun keyDown(keycode: Int): Boolean {
        if (keycode == Input.Keys.LEFT || keycode == Input.Keys.A) {
            if(pressedKeys[3]) {
                pressedKeys[3] = false
            }
            pressedKeys[2] = true
        }
        if (keycode == Input.Keys.RIGHT || keycode == Input.Keys.D) {
            if(pressedKeys[2]) {
                pressedKeys[2] = false
            }
            pressedKeys[3] = true
        }
        if (keycode == Input.Keys.UP || keycode == Input.Keys.W) {
            if(pressedKeys[1]) {
                pressedKeys[1] = false
            }
            pressedKeys[0] = true
        }
        if (keycode == Input.Keys.DOWN || keycode == Input.Keys.S) {
            if(pressedKeys[0]) {
                pressedKeys[0] = false
            }
            pressedKeys[1] = true
        }

        if (keycode == Input.Keys.PERIOD) {
            physicsState.warp++
        }
        if(keycode == Input.Keys.COMMA) {
            physicsState.warp--
        }

        if(keycode == Input.Keys.F) {
            physicsState.add(Planet("lol", 100f, Vector2(Gdx.input.x.toFloat() - 25f, Gdx.graphics.height - Gdx.input.y - 50f), 50f))
        }

        return false
    }

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        return false
    }

}