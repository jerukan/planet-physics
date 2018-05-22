package io.github.jerukan.physics

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import io.github.jerukan.util.shapes.Rectangle

/**
 * Generic shape that holds only its position.
 */

abstract class Shape(pos: Vector2) {

    var position: Vector2 = pos
    set(value) {
        setPosition(value.x, value.y)
    }

    open fun setPosition(x: Float, y: Float) {
        position.set(x, y)
    }

    abstract fun collidesPoint(other: Vector2): Boolean

    abstract fun collidesRect(other: Rectangle): Boolean

    abstract fun collidesCircle(other: Circle): Boolean

    fun collides(other: Shape): Boolean {
        if(other is Circle) {
            return collidesCircle(other)
        }
        else if(other is Rectangle) {
            return collidesRect(other)
        }
        return false
    }

    abstract fun translate(x: Float, y: Float)

    abstract fun scale(magnitude: Float)

    abstract fun getArea(): Float

    abstract fun getPerimeter(): Float

    abstract fun render(shapeRenderer: ShapeRenderer)
}