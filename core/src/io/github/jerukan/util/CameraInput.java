package io.github.jerukan.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import io.github.jerukan.physics.PhysicsState;
import io.github.jerukan.planetdata.Planet;
import io.github.jerukan.rendering.WorldRenderer;

public class CameraInput implements InputProcessor {

    public boolean[] pressedKeys = new boolean[4]; //[up, down, left, right]

    public WorldRenderer renderer;
    public PhysicsState physicsState;

    public float maxAccel;

    public CameraInput(PhysicsState physicsState, WorldRenderer renderer) {
        this.physicsState = physicsState;
        this.renderer = renderer;

        maxAccel = renderer.getCamera().MAX_ACCELERATION;
        renderer.getCamera().setInputKeys(pressedKeys);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        if (amount == 1) {
            renderer.getCamera().addTargetZoom(0.05f);
        } else if (amount == -1) {
            renderer.getCamera().addTargetZoom(-0.05f);
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.LEFT|| keycode == Input.Keys.A) {
            pressedKeys[2] = false;
        }
        if (keycode == Input.Keys.RIGHT || keycode == Input.Keys.D) {
            pressedKeys[3] = false;
        }
        if (keycode == Input.Keys.UP || keycode == Input.Keys.W) {
            pressedKeys[0] = false;
        }
        if (keycode == Input.Keys.DOWN || keycode == Input.Keys.S) {
            pressedKeys[1] = false;
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.LEFT || keycode == Input.Keys.A) {
            if(pressedKeys[3]) {
                pressedKeys[3] = false;
            }
            pressedKeys[2] = true;
        }
        if (keycode == Input.Keys.RIGHT || keycode == Input.Keys.D) {
            if(pressedKeys[2]) {
                pressedKeys[2] = false;
            }
            pressedKeys[3] = true;
        }
        if (keycode == Input.Keys.UP || keycode == Input.Keys.W) {
            if(pressedKeys[1]) {
                pressedKeys[1] = false;
            }
            pressedKeys[0] = true;
        }
        if (keycode == Input.Keys.DOWN || keycode == Input.Keys.S) {
            if(pressedKeys[0]) {
                pressedKeys[0] = false;
            }
            pressedKeys[1] = true;
        }

        if (keycode == Input.Keys.PERIOD) {
            physicsState.warp++;
        }
        if(keycode == Input.Keys.COMMA) {
            physicsState.warp--;
        }

        if(keycode == Input.Keys.F) {
            physicsState.add(new Planet("lol", 100f, new Vector2(Gdx.input.getX() - 25f, Gdx.graphics.getHeight() - Gdx.input.getY() - 50f), 50f));
        }

        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

}