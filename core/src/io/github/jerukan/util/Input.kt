package io.github.jerukan.util

import com.badlogic.gdx.Input
import com.badlogic.gdx.InputProcessor
import io.github.jerukan.Renderer

class Input: InputProcessor {
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
            Renderer.camwrapper.setCamTargetZoom(0.05f)
        } else if (amount == -1) {
            Renderer.camwrapper.setCamTargetZoom(-0.05f)
        }
        return false
    }

    override fun keyUp(keycode: Int): Boolean {
        if (keycode == Input.Keys.LEFT || keycode == Input.Keys.RIGHT || keycode == Input.Keys.A || keycode == Input.Keys.D) {
            Renderer.camwrapper.setCamAccelX(0f)
            Renderer.camwrapper.camSlowingX = true
        }
        if (keycode == Input.Keys.UP || keycode == Input.Keys.DOWN || keycode == Input.Keys.W || keycode == Input.Keys.S) {
            Renderer.camwrapper.setCamAccelY(0f)
            Renderer.camwrapper.camSlowingY = true
        }
        return false
    }

    override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean {
        return false
    }

    override fun keyDown(keycode: Int): Boolean {
        if (keycode == Input.Keys.LEFT || keycode == Input.Keys.A) {
            Renderer.camwrapper.setCamAccelX(-0.25f)
            Renderer.camwrapper.camSlowingX = false
        }
        if (keycode == Input.Keys.RIGHT || keycode == Input.Keys.D) {
            Renderer.camwrapper.setCamAccelX(0.25f)
            Renderer.camwrapper.camSlowingX = false
        }
        if (keycode == Input.Keys.UP || keycode == Input.Keys.W) {
            Renderer.camwrapper.setCamAccelY(0.25f)
            Renderer.camwrapper.camSlowingY = false
        }
        if (keycode == Input.Keys.DOWN || keycode == Input.Keys.S) {
            Renderer.camwrapper.setCamAccelY(-0.25f)
            Renderer.camwrapper.camSlowingY = false
        }
        return false
    }

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        return false
    }

}