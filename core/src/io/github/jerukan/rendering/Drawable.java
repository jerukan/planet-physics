package io.github.jerukan.rendering;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.jerukan.rendering.camera.OrthoCameraWrapper;

/**
 * Any object in the game that is to be drawn.
 */
public interface Drawable {

    void render(SpriteBatch batch);

    boolean inCameraWindow(OrthoCameraWrapper camera);

    void dispose();
}
