package io.github.jerukan.rendering.camera;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import io.github.jerukan.util.shapes.Rectangle;

/**
 * Wraps an [OrthographicCamera] with easier access to movement and modification of the camera.
 */
public abstract class OrthoCameraWrapper {
    public OrthographicCamera camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

    public Rectangle hitbox = new Rectangle(new Vector2(camera.position.x - camera.viewportWidth / 2, camera.position.y - camera.viewportHeight / 2), camera.viewportWidth, camera.viewportHeight);

    public Vector2 velocity = new Vector2();
    public Vector2 acceleration = new Vector2();

    public float zoom = 1f;

    private float camOriginX = 0f;
    private float camOriginY = 0f;

    private float camOffsetX = 0f;
    private float camOffsetY = 0f;

    /**
     * Will be called every frame to update camera information.
     */
    public abstract void update();

    /**
     * Specific method to update camera vectors such as position and velocity.
     */
    public abstract void updateVectors();

    /**
     * Specific method to update camera zoom.
     */
    public abstract void updateZoom();

    public void updateOrigin() {
        camOriginX = (Gdx.graphics.getWidth() / 2);
        camOriginY = (Gdx.graphics.getHeight()/ 2);

        updateOffsets();
    }

    public void updateOffsets() {
        camOffsetX = camera.position.x - camOriginX;
        camOffsetY = camera.position.y - camOriginY;
    }

    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
        updateOrigin();
    }
}