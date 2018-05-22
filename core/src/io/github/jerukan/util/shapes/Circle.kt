package io.github.jerukan.physics

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import io.github.jerukan.util.shapes.Rectangle

/**
 * Generic circle shape.
 * Position is the center of the circle.
 */

open class Circle(position: Vector2, var radius: Float): Shape(position) {

    override fun collidesPoint(other: Vector2): Boolean {
        return position.dst(other) <= radius
    }

    override fun collidesRect(other: Rectangle): Boolean {
        return (Math.abs(position.x - other.left) <= radius || Math.abs(position.x - other.right) <= radius)
                && (Math.abs(position.y - other.bottom) <= radius || Math.abs(position.y - other.top) <= radius)
    }

    override fun collidesCircle(other: Circle): Boolean {
        return position.dst2(other.position) <= (radius + other.radius) * (radius + other.radius)
    }

    override fun translate(x: Float, y: Float) {
        position.add(x, y)
    }

    override fun scale(magnitude: Float) {
        radius *= Math.sqrt(magnitude.toDouble()).toFloat()
    }

    override fun getArea(): Float {
        return (radius * Math.PI * Math.PI).toFloat()
    }

    override fun getPerimeter(): Float {
        return (radius * 4 * Math.PI).toFloat()
    }

    override fun render(shapeRenderer: ShapeRenderer) {
        shapeRenderer.circle(position.x, position.y, radius)
    }
}