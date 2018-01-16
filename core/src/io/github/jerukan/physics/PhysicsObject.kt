package io.github.jerukan.physics

import io.github.jerukan.util.Position

abstract class PhysicsObject(var mass: Float, var position: Position) {

    var xVelocity: Float = 0f
    var yVelocity: Float = 0f

    var xAccel: Float = 0f
    var yAccel: Float = 0f

    abstract fun collidesPoint(other: Position): Boolean

    abstract fun collidesRect(other: Rectangle): Boolean

    abstract fun collidesCircle(other: Circle): Boolean

    abstract fun translate(x: Float, y: Float)

    abstract fun scale(magnitude: Float)

    abstract fun getArea(): Float

    abstract fun getPerimeter(): Float

    open fun updatePosition(time: Float) {
        xVelocity += xAccel * time
        yVelocity += yAccel * time

        translate(xVelocity, yVelocity)
    }
}