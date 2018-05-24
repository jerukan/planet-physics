package io.github.jerukan.util.shapes;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

/**
 * Generic circle shape.
 * Position is the center of the circle.
 */

public class Circle extends Shape {

    public float radius;
    public Circle(Vector2 position, float radius) {
        super(position);
        this.radius = radius;
    }

    @Override
    public boolean collidesPoint(Vector2 other) {
        return position.dst(other) <= radius;
    }

    @Override
    public boolean collidesRect(Rectangle other) {
        return (Math.abs(position.x - other.left) <= radius || Math.abs(position.x - other.right) <= radius)
        && (Math.abs(position.y - other.bottom) <= radius || Math.abs(position.y - other.top) <= radius);
    }

    @Override
    public boolean collidesCircle(Circle other) {
        return position.dst2(other.position) <= (radius + other.radius) * (radius + other.radius);
    }

    @Override
    public void translate(float x, float y) {
        position.add(x, y);
    }

    @Override
    public void scale(float magnitude) {
        radius *= Math.sqrt(magnitude);
    }

    @Override
    public float getArea() {
        return (float)(radius * Math.PI * Math.PI);
    }

    @Override
    public float getPerimeter() {
        return (float)(radius * 4 * Math.PI);
    }

    @Override
    public void render(ShapeRenderer shapeRenderer) {
        shapeRenderer.circle(position.x, position.y, radius);
    }
}