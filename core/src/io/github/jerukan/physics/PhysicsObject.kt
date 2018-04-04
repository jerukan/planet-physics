package io.github.jerukan.physics

import com.badlogic.gdx.math.Vector2

abstract class PhysicsObject(var mass: Float, var position: Vector2) {

    var velocity: Vector2 = Vector2()

    var acceleration: Vector2 = Vector2()

    abstract fun collidesPoint(other: Vector2): Boolean

    abstract fun collidesRect(other: Rectangle): Boolean

    abstract fun collidesCircle(other: Circle): Boolean

    abstract fun translate(x: Float, y: Float)

    abstract fun scale(magnitude: Float)

    abstract fun getArea(): Float

    abstract fun getPerimeter(): Float

    open fun updatePosition(time: Float) {
        velocity.add(acceleration.scl(time, time))

        position.add(velocity)
    }
}