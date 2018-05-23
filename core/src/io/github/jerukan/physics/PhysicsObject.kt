package io.github.jerukan.physics

import com.badlogic.gdx.math.Vector2
import io.github.jerukan.util.shapes.Shape

open class PhysicsObject(var mass: Float, var position: Vector2, var hitbox: Shape) {

    var velocity: Vector2 = Vector2()

    var acceleration: Vector2 = Vector2()

    fun applyForce(force: Vector2) {
        acceleration.add(force.scl(1 / mass, 1 / mass))
    }

    /**
     * Should update whatever vectors in the object need to change.
     */
    open fun updateVectors(deltaTime: Float) {
        velocity.add(acceleration.scl(deltaTime, deltaTime))

        position.add(velocity)
    }
}