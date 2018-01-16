package io.github.jerukan.physics

import com.badlogic.gdx.math.Vector2

/** Generic circle shape with a mass */

open class Circle(mass: Float, position: Vector2, var radius: Float): PhysicsObject(mass, position) {

    override fun collidesPoint(other: Vector2): Boolean {
        return position.dst(other) <= radius
    }

    override fun collidesRect(other: Rectangle): Boolean {
        return (Math.abs(position.x - other.left) <= radius || Math.abs(position.x - other.right) <= radius)
                && (Math.abs(position.y - other.bottom) <= radius || Math.abs(position.y - other.top) <= radius)
    }

    override fun collidesCircle(other: Circle): Boolean {
        return position.dst(other.position) <= radius + other.radius
    }

    override fun translate(x: Float, y: Float) {
        position.add(x, y)
    }

    override fun scale(magnitude: Float) {
        radius *= Math.sqrt(magnitude.toDouble()).toFloat()
    }

    override fun getArea(): Float {
        return (radius * PhysicsConstants.PIsq).toFloat()
    }

    override fun getPerimeter(): Float {
        return (radius * 2 * PhysicsConstants.PI2).toFloat()
    }
}