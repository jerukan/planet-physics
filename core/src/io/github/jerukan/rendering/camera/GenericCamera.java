package io.github.jerukan.rendering.camera;

import com.badlogic.gdx.math.MathUtils;
import io.github.jerukan.util.Functions;

public class GenericCamera extends OrthoCameraWrapper {

    public final float MAX_ZOOM = 3f;
    public final float MIN_ZOOM = 0.1f;
    public final float ZOOM_SPEED = 0.05f;

    public final float MAX_VELOCITY = 5f;
    public final float ZERO_SPEED_THRESHOLD = 0.1f;
    public final float MAX_ACCELERATION = 0.15f;

    private float camZoom = 1f;
    private float camTargetZoom = 1f;

    private boolean[] inputKeys = new boolean[4]; //[up, down, left, right]

    public void setInputKeys(boolean[] keys) {
        inputKeys = keys;
    }
    @Override
    public void update() {
        updateOffsets();

        updateVectors();
        updateZoom();

        camera.translate(velocity.x * camera.zoom, velocity.y * camera.zoom);
        camera.zoom = MathUtils.clamp(camZoom, MIN_ZOOM, MAX_ZOOM);

        camera.update();

        hitbox.setPosition(camera.position.x - camera.viewportWidth / 2, camera.position.y - camera.viewportHeight / 2);
    }

    @Override
    public void updateVectors() {
        if(inputKeys[2]) {
            acceleration.x = -MAX_ACCELERATION;
        }
        if(inputKeys[3]) {
            acceleration.x = MAX_ACCELERATION;
        }
        if(!inputKeys[2] && !inputKeys[3]) {
            if(Math.abs(velocity.x) < ZERO_SPEED_THRESHOLD) {
                velocity.x = 0f;
                acceleration.x = 0f;
            }
            else {
                acceleration.x = MAX_ACCELERATION * -Math.signum(velocity.x);
            }
        }
        if(inputKeys[0]) {
            acceleration.y = MAX_ACCELERATION;
        }
        if(inputKeys[1]) {
            acceleration.y = -MAX_ACCELERATION;
        }
        if(!inputKeys[0] && !inputKeys[1]) {
            if(Math.abs(velocity.y) < ZERO_SPEED_THRESHOLD) {
            velocity.y = 0f;
            acceleration.y = 0f;
            }
            else {
                acceleration.y = MAX_ACCELERATION * -Math.signum(velocity.y);
            }
        }
        velocity.add(acceleration);
        velocity.x = MathUtils.clamp(velocity.x, -MAX_VELOCITY, MAX_VELOCITY);
        velocity.y = MathUtils.clamp(velocity.y, -MAX_VELOCITY, MAX_VELOCITY);
    }

    @Override
    public void updateZoom() {
        if(!Functions.withinThreshold(camZoom, camTargetZoom, 0.1f)) {
            float zoomDir = Math.signum(camTargetZoom - camera.zoom);
            camZoom += ZOOM_SPEED * zoomDir;
            camZoom = MathUtils.clamp(camZoom, MIN_ZOOM, MAX_ZOOM);
        }
    }

    public void addTargetZoom(float zoom) {
        camTargetZoom += zoom;
        MathUtils.clamp(camTargetZoom, MIN_ZOOM, MAX_ZOOM);
    }
}