package io.github.jerukan.util

class Timer {

    val nanoToSecond = 1000000000

    private var startTime: Long = 0
    private var previousTime: Long = 0

    init {
        reset()
    }

    fun reset() {
        startTime = System.nanoTime()
        previousTime = System.nanoTime()
    }

    /**
     * Preferably called at the end of a loop to update the previous time.
     */
    fun update() {
        previousTime = System.nanoTime()
    }

    /**
     * Gets total time elapsed in seconds.
     */
    fun getElapsed(): Double {
        return (System.nanoTime() - startTime).toDouble() / nanoToSecond
    }

    /**
     * Gets the delta time from the previous time it was updated.
     */
    fun getDelta(): Double {
        return (System.nanoTime() - previousTime).toDouble() / nanoToSecond
    }
}