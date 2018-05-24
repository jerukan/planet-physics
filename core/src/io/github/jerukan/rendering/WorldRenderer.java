package io.github.jerukan.rendering;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.jerukan.rendering.camera.GenericCamera;
import io.github.jerukan.rendering.camera.OrthoCameraWrapper;

import java.util.ArrayList;

public class WorldRenderer {

    private OrthoCameraWrapper camera = new GenericCamera();
    private ArrayList<Drawable> drawables;

    public WorldRenderer(ArrayList<Drawable> drawables) {
        this.drawables = drawables;
    }

    public void add(Drawable drawable) {
        drawables.add(drawable);
    }

    public void render(SpriteBatch batch) {
        for(Drawable drawable : drawables) {
            if(drawable.inCameraWindow(camera)) {
                drawable.render(batch);
            }
        }
    }

    public void dispose() {
        for(Drawable drawable : drawables) {
            drawable.dispose();
        }
    }

    public GenericCamera getCamera(){
        return (GenericCamera) camera;
    }

    public OrthographicCamera getOrthoCamera(){
        return camera.camera;
    }
}