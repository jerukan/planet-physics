package io.github.jerukan.physics

import io.github.jerukan.util.Timer
import java.util.*

open class PhysicsState {

    var warp: Int = 1
    protected val timer: Timer = Timer()
    protected val physicsObjects: ArrayList<PhysicsObject> = ArrayList()

    fun resetTimer() {
        timer.reset()
    }

    open fun update() {
        for(obj in physicsObjects) {
            obj.updateVectors(timer.getDelta().toFloat())
        }
        timer.update()
    }

    open fun add(physicsObject: PhysicsObject) {
        physicsObjects.add(physicsObject)
    }
}