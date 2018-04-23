package io.github.jerukan.planetdata

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import io.github.jerukan.physics.Circle
import io.github.jerukan.physics.PhysicsConstants
import kotlin.math.abs

class Planet(planetname: String, mass: Float, position: Vector2, radius: Float): Circle(mass, position, radius) {
//    var parser: Parser = Parser()
//    var info: JsonObject = parser.parse("planets.json") as JsonObject
//    var planetinfo: JsonObject = info[planetname] as JsonObject
//
//    val name: String = planetinfo["name"] as String
    private var prevPos = Vector2(position) //used to detect where a planet would be at moment of a collision
    private var trail = ArrayList<Vector2>()

    private var lineEnd = Vector2()

    private var projectedVel: Vector2 = Vector2()
    private var projectedAccel: Vector2 = Vector2()

    private var collided = false
    private var canCollide = true

    fun setCircularOrbit(other: Planet) {
        val dist = position.dst(other.position)
        val g = PhysicsConstants.G * other.mass / (dist * dist)

        val netVel = Math.sqrt(g * dist)
        velocity.x = (netVel * (position.y - other.position.y) / dist).toFloat() + other.velocity.x
        velocity.y = (netVel * (position.x - other.position.x) / dist).toFloat() + other.velocity.y
    }

    fun update(planets: Array<Planet>, time: Float) {
        projectedAccel.set(0f, 0f)
        planets
                .filter { it != this }
                .forEach { projectedAccel.add(accelFromGravity(it)) }
        acceleration.set(projectedAccel)

        lineEnd.set(position.x + acceleration.x * 1000, position.y + acceleration.y * 1000)

        updatePosition(time)
    }

    override fun updatePosition(time: Float) {
        if(collided) {
            print(projectedVel)
            velocity.set(projectedVel)

//            position.set(prevPos)
            projectedVel.set(0f, 0f)
            collided = false
        }
        else {
            velocity.add(acceleration)
        }

        position.add(velocity)
    }

    private fun accelFromGravity(p:Planet): Vector2 {
        val dist = position.dst(p.position)
        val g = PhysicsConstants.G * p.mass / (dist * dist)

        val xa = -(g * ((position.x - p.position.x) / dist)).toFloat()
        val ya = -(g * ((position.y - p.position.y) / dist)).toFloat()

        return Vector2(xa, ya)
    }



    fun display(shapeRenderer: ShapeRenderer) {
        shapeRenderer.circle(position.x, position.y, radius)
        shapeRenderer.line(position.x, position.y, lineEnd.x, lineEnd.y)
    }

    fun collidesPlanet(other: Planet): Boolean {
        return collidesCircle(other)
    }

    fun onCollision(other: Planet) {
        collided = true

        val startposx: Float = position.x - velocity.x
        val startposy: Float = position.y - velocity.y

        val otherstartposx: Float = other.position.x - other.velocity.x
        val otherstartposy: Float = other.position.y - other.velocity.y

        println("startposx: $startposx\n" +
                "velocity: $velocity\n" +
                "otherstartpos: $otherstartposx\n" +
                "othervel: ${other.velocity}\n")
        val collideposx: Float = if(velocity.x - other.velocity.x != 0f) {
            startposx + velocity.x * ((otherstartposx - startposx) / (velocity.x - other.velocity.x)) - radius * (abs(velocity.x) / velocity.x)
        }
        else {
            position.x
        }

        val collideposy: Float = if(velocity.y - other.velocity.y != 0f) {
            startposy + velocity.y * ((otherstartposy - startposy) / (velocity.y - other.velocity.y)) - radius * (abs(velocity.y) / velocity.y)
        }
        else {
            position.y
        }

        //in the case the planets are inside each other, set them to where they would've been at instant of collision
//        prevPos.set(collideposx, collideposy)

        projectedVel.x = (velocity.x * (mass - other.mass) + (2 * other.mass * other.velocity.x)) / (mass + other.mass)
        projectedVel.y = (velocity.y * (mass - other.mass) + (2 * other.mass * other.velocity.y)) / (mass + other.mass)

        println("original pos: $position")
        println("start pos: ($startposx, $startposy)")
        println("collide pos: ($collideposx, $collideposy)")
        println("end velocity: $projectedVel")
        println("-------------------------")
    }
}