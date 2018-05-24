package io.github.jerukan.util.shapes;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

/**
 * Generic rectangle shape.
 * The position is bottom left vertex.
 */
public class Rectangle extends Shape {

    public float width;
    public float height;

    public float left;
    public float right;
    public float bottom;
    public float top;

    public Vector2 center;

    public Vector2 leftbottom;
    public Vector2 lefttop;
    public Vector2 rightbottom;
    public Vector2 righttop;

    public Rectangle(Vector2 position, float width, float height) {
        super(position);
        this.width = width;
        this.height = height;

        left = position.x;
        right = position.x + width;
        bottom = position.y;
        top = position.y + height;

        center = new Vector2(position.x + width / 2, position.y + height/2);

        leftbottom = new Vector2(left, bottom);
        lefttop = new Vector2(left, top);
        rightbottom = new Vector2(right, bottom);
        righttop = new Vector2(right, top);
    }

    @Override
    public boolean collidesPoint(Vector2 other) {
        return other.x >= position.x && other.x <= position.x + width && other.y >= position.y && other.y <= position.y + height;
    }

    @Override
    public boolean collidesRect(Rectangle other) {
        //  edge case where the other rectangle is completely inside this rectangle.
        if(other.left > left && other.right < right && other.top < top && other.bottom > bottom) {
            return true;
        }
        return other.collidesPoint(leftbottom) || other.collidesPoint(lefttop)
        || other.collidesPoint(rightbottom) || other.collidesPoint(righttop);
    }

    @Override
    public boolean collidesCircle(Circle other) {
        return (Math.abs(other.position.x - left) <= other.radius || Math.abs(other.position.x - right) <= other.radius)
        && (Math.abs(other.position.y - bottom) <= other.radius || Math.abs(other.position.y - top) <= other.radius);
    }

    @Override
    public void translate(float x, float y) {
        position.add(x, y);
    }

    @Override
    public void scale(float magnitude) {
        float scale = (float)Math.sqrt(magnitude);
        width *= scale;
        height *= scale;
    }

    @Override
    public float getArea() {
        return width * height;
    }

    @Override
    public float getPerimeter() {
        return (2 * width) + (2 * height);
    }

    @Override
    public void render(ShapeRenderer shapeRenderer) {
        shapeRenderer.rect(position.x, position.y, width, height);
    }

    private void updateVertices() {
        leftbottom.set(left, bottom);
        lefttop.set(left, top);
        rightbottom.set(right, bottom);
        righttop.set(right, top);
    }

    public void setSize(float width, float height) {
        float deltaWidth = width - this.width;
        float deltaHeight = height - this.height;

        right += deltaWidth;
        top += deltaHeight;

        lefttop.add(0f, deltaHeight);
        righttop.add(deltaWidth, deltaHeight);
        rightbottom.add(deltaWidth, 0f);

        center.add(deltaWidth / 2, deltaHeight / 2);

        this.width = width;
        this.height = height;
    }

    /** Just sets the bottom left position */
    @Override
    public void setPosition(float x, float y) {
        position.set(x, y);
        left = x;
        right = x + width;
        bottom = y;
        top = y + width;
        center.set(x + width / 2, y + height/2);
    }

    public void setCenter(float x, float y) {
        center.set(x, y);
        left = x - width / 2;
        right = x + width / 2;
        bottom = y - height / 2;
        top = y + width / 2;
        position.set(left, bottom);
        updateVertices();
    }
}