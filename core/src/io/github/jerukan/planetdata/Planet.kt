package io.github.jerukan.planetdata

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import io.github.jerukan.physics.Circle
import io.github.jerukan.physics.PhysicsConstants

class Planet(planetname: String, mass: Float, position: Vector2, radius: Float): Circle(mass, position, radius) {
//    var parser: Parser = Parser()
//    var info: JsonObject = parser.parse("planets.json") as JsonObject
//    var planetinfo: JsonObject = info[planetname] as JsonObject
//
//    val name: String = planetinfo["name"] as String
    var prevPos = Vector2(position)
    var trail = ArrayList<Vector2>()

    var lineEnd = Vector2()

    var projectedXVel = 0f
    var projectedYVel = 0f

    fun setCircularOrbit(other: Planet) {
        val dist = position.dst(other.position)
        val g = PhysicsConstants.G * other.mass / (dist * dist)

        val netVel = Math.sqrt(g * dist)
        xVelocity = (netVel * (position.y - other.position.y) / dist).toFloat() + other.xVelocity
        yVelocity = (netVel * (position.x - other.position.x) / dist).toFloat() + other.yVelocity
    }

    fun update(planets: Array<Planet>, time: Float) {
        var accel = Vector2()
        for(p:Planet in planets) {
            accel = accel.add(accelFromGravity(p))
        }
        xAccel = accel.x
        yAccel = accel.y

        lineEnd.set(position.x + xAccel * 1000, position.y + yAccel * 1000)

        updatePosition(time)
    }

    fun accelFromGravity(p:Planet): Vector2 {
        var xa = 0f
        var ya = 0f
        if(p != this) {
            val dist = position.dst(p.position)
            val g = PhysicsConstants.G * p.mass / (dist * dist)

            xa = -(g * ((position.x - p.position.x) / dist)).toFloat()
            ya = -(g * ((position.y - p.position.y) / dist)).toFloat()

            collidesPlanet(p)
        }

        return Vector2(xa, ya)
    }

    override fun updatePosition(time: Float) {
        prevPos.set(position)
        //trail.add(prevPos)
        xVelocity += xAccel
        yVelocity += yAccel

        translate(xVelocity, yVelocity)
    }

    fun display(shapeRenderer: ShapeRenderer) {
        shapeRenderer.circle(position.x, position.y, radius)
        shapeRenderer.line(position.x, position.y, lineEnd.x, lineEnd.y)
//        shapeRenderer.setColor(1f, 0f, 0f, 1f)
//        for(p:Vector2 in trail) {
//            shapeRenderer.point(p.x, p.y, 0f)
//        }
    }

    fun collidesPlanet(other: Planet): Boolean {
        if(collidesCircle(other)) {
            val xDist = position.x - other.position.x
            val yDist = position.y - other.position.y

            val dist = position.dst(other.position)

            val unitx = xDist / dist
            val unity = yDist / dist


            return true
        }
        return false
    }
}