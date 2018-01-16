package io.github.jerukan.physics

import io.github.jerukan.util.Position

/** Generic rectangle shape with a mass
 * Position is bottom left vertex */

class Rectangle(mass: Float, position: Position, var width: Float, var height: Float): PhysicsObject(mass, position) {

    var left: Float = position.x
    var right: Float = position.x + width
    var bottom: Float = position.y
    var top: Float = position.y + height

    var center: Position = Position(position.x + width / 2, position.y + height/2)

    var leftbottom: Position = Position(left, bottom)
    var lefttop: Position = Position(left, top)
    var rightbottom: Position = Position(right, bottom)
    var righttop: Position = Position(right, top)

    override fun collidesPoint(other: Position): Boolean {
        return other.x >= position.x && other.x <= position.x + width && other.y >= position.y && other.y <= position.y + height
    }

    override fun collidesRect(other: Rectangle): Boolean {
        return other.collidesPoint(leftbottom) || other.collidesPoint(lefttop)
                || other.collidesPoint(rightbottom) || other.collidesPoint(righttop)
    }

    override fun collidesCircle(other: Circle): Boolean {
        return (Math.abs(other.position.x - left) <= other.radius || Math.abs(other.position.x - right) <= other.radius)
                && (Math.abs(other.position.y - bottom) <= other.radius || Math.abs(other.position.y - top) <= other.radius)
    }

    override fun translate(x: Float, y: Float) {
        setPosition(position.x + x, position.y + y)
    }

    override fun scale(magnitude: Float) {
        val scale = Math.sqrt(magnitude.toDouble()).toFloat()
        width *= scale
        height *= scale
    }

    override fun getArea(): Float {
        return width * height
    }

    override fun getPerimeter(): Float {
        return (2 * width) + (2 * height)
    }

    private fun updateVertices() {
        leftbottom.set(left, bottom)
        lefttop.set(left, top)
        rightbottom.set(right, bottom)
        righttop.set(right, top)
    }

    /** Just sets the bottom left position */
    fun setPosition(x: Float, y: Float) {
        position.set(x, y)
        left = x
        right = x + width
        bottom = y
        top = y + width
        center.set(x + width / 2, y + height/2)
    }

    fun setCenter(x: Float, y: Float) {
        center.set(x, y)
        left = x - width / 2
        right = x + width / 2
        bottom = y - height / 2
        top = y + width / 2
        position.set(left, bottom)
        updateVertices()
    }
}