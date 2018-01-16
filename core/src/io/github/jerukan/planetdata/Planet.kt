package io.github.jerukan.planetdata

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import io.github.jerukan.Renderer
import io.github.jerukan.physics.Circle
import io.github.jerukan.physics.PhysicsConstants
import io.github.jerukan.util.Position
import io.github.jerukan.util.Utils

class Planet(planetname: String, mass: Float, position: Position, radius: Float): Circle(mass, position, radius) {
//    var parser: Parser = Parser()
//    var info: JsonObject = parser.parse("planets.json") as JsonObject
//    var planetinfo: JsonObject = info[planetname] as JsonObject
//
//    val name: String = planetinfo["name"] as String
    var prevPos = Position(position)
    var trail = ArrayList<Position>()

    var lineEnd = Position()

    var projectedXVel = 0f
    var projectedYVel = 0f

    fun setCircularOrbit(other: Planet) {
        val dist = position.distanceToPosition(other.position)
        val g = PhysicsConstants.G * other.mass / (dist * dist)

        val netVel = Math.sqrt(g * dist)
        xVelocity = (netVel * (position.y - other.position.y) / dist).toFloat() + other.xVelocity
        yVelocity = (netVel * (position.x - other.position.x) / dist).toFloat() + other.yVelocity
    }

    fun setAccelFromGravity(planets: Array<Planet>) {
        var xa = 0f
        var ya = 0f
        for (p:Planet in planets) {
            if(p != this) {
                val dist = position.distanceToPosition(p.position)
                val g = PhysicsConstants.G * p.mass / (dist * dist)

                xa += -(g * ((position.x - p.position.x) / dist)).toFloat()
                ya += -(g * ((position.y - p.position.y) / dist)).toFloat()

                collidesPlanet(p)
            }
        }
        xAccel = xa
        yAccel = ya

        lineEnd.set(position.x + xAccel * 1000, position.y + yAccel * 1000)
//        println("xaccel" + xAccel)
//        println("yaccel" + yAccel)
    }

    override fun updatePosition(time: Float) {
        prevPos.set(position)
        trail.add(prevPos)
        xVelocity += xAccel * time
        yVelocity += yAccel * time

        translate(xVelocity, yVelocity)
    }

    fun display(shapeRenderer: ShapeRenderer) {
        shapeRenderer.circle(position.x, position.y, radius)
        shapeRenderer.line(position.x, position.y, lineEnd.x, lineEnd.y)
//        shapeRenderer.setColor(1f, 0f, 0f, 1f)
//        for(p:Position in trail) {
//            shapeRenderer.point(p.x, p.y, 0f)
//        }
    }

    fun collidesPlanet(other: Planet) {
        if(collidesCircle(other)) {
            val xDist = position.x - other.position.x
            val yDist = position.y - other.position.y

            val dist = position.distanceToPosition(other.position)

            val unitx = xDist / dist
            val unity = yDist / dist

            position.set(unitx * (radius + other.radius) / dist, unity * (radius + other.radius) / dist)
//            val massratio = mass / other.mass
//
//            if(massratio > 1) {
//                projectedXVel = other.xVelocity / massratio
//                projectedYVel = other.yVelocity / massratio
//            }
//            else {
//                projectedXVel = other.xVelocity * massratio
//                projectedYVel = other.yVelocity * massratio
//            }
        }
    }
}