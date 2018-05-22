package io.github.jerukan.util

import com.badlogic.gdx.Input
import com.badlogic.gdx.InputProcessor
import io.github.jerukan.rendering.WorldRenderer

class Input(val renderer: WorldRenderer): InputProcessor {

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
        if (keycode == Input.Keys.LEFT || keycode == Input.Keys.RIGHT || keycode == Input.Keys.A || keycode == Input.Keys.D) {
            renderer.getCamera().setCamAccelX(0f)
        }
        if (keycode == Input.Keys.UP || keycode == Input.Keys.DOWN || keycode == Input.Keys.W || keycode == Input.Keys.S) {
            renderer.getCamera().setCamAccelY(0f)
        }
        return false
    }

    override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean {
        return false
    }

    override fun keyDown(keycode: Int): Boolean {
        if (keycode == Input.Keys.LEFT || keycode == Input.Keys.A) {
            renderer.getCamera().setCamAccelX(-0.25f)
        }
        if (keycode == Input.Keys.RIGHT || keycode == Input.Keys.D) {
            renderer.getCamera().setCamAccelX(0.25f)
        }
        if (keycode == Input.Keys.UP || keycode == Input.Keys.W) {
            renderer.getCamera().setCamAccelY(0.25f)
        }
        if (keycode == Input.Keys.DOWN || keycode == Input.Keys.S) {
            renderer.getCamera().setCamAccelY(-0.25f)
        }
        return false
    }

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        return false
    }

}