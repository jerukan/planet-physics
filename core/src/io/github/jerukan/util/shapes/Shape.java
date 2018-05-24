package io.github.jerukan.util.shapes;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

/**
 * Generic shape that holds only its position.
 */

public abstract class Shape {

    public Vector2 position;

    public Shape(Vector2 pos) {
        position = pos;
    }

    public abstract boolean collidesPoint(Vector2 other);

    public abstract boolean collidesRect(Rectangle other);

    public abstract boolean collidesCircle(Circle other);

    public boolean collides(Shape other) {
        if(other instanceof Circle) {
            return collidesCircle((Circle)other);
        }
        else if(other instanceof Rectangle) {
            return collidesRect((Rectangle) other);
        }
        return false;
    }

    public void setPosition(float x, float y) {
        position.set(x, y);
    }

    public void translate(float x, float y) {
        position.add(x, y);
    }

    public abstract void scale(float magnitude);

    public abstract float getArea();

    public abstract float getPerimeter();

    public abstract void render(ShapeRenderer shapeRenderer);
}