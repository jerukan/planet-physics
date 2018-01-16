package io.github.jerukan.util

class Position {

    var x: Float = 0f
    var y: Float = 0f

    val pos: FloatArray
        get() = floatArrayOf(x, y)

    constructor() {
        x = 0f
        y = 0f
    }

    constructor(x: Float, y: Float) {
        this.x = x
        this.y = y
    }

    constructor(pos: FloatArray) {
        if (pos.size != 2) {
            throw IllegalArgumentException("A position only has two numbers")
        }
        x = pos[0]
        y = pos[1]
    }

    constructor(pos: Position) {
        x = pos.x
        y = pos.y
    }

    /** @return (x, y) */
    override fun toString(): String = "($x, $y)"

    fun equals(pos: Position): Boolean = pos.x == x && pos.y == y

    /** @param x the x position of the other point
     * @param y the y position of the other point
     * @return distance to given point */
    fun distanceToPosition(x: Float, y: Float): Float =
            Math.sqrt(Math.pow((this.x - x).toDouble(), 2.0) + Math.pow((this.y - y).toDouble(), 2.0)).toFloat()

    /** @param other the other position
     * @return distance to given point */
    fun distanceToPosition(other: Position): Float =
            Math.sqrt(Math.pow((x - other.x).toDouble(), 2.0) + Math.pow((y - other.y).toDouble(), 2.0)).toFloat()

    fun set(x: Float, y: Float) {
        this.x = x
        this.y = y
    }

    fun set(pos: Position) {
        x = pos.x
        y = pos.y
    }

    fun reset() {
        set(0f, 0f)
    }

    fun translate(x: Float, y: Float) {
        this.x += x
        this.y += y
    }
}
