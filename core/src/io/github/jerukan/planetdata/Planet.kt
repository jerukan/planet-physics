package io.github.jerukan.planetdata

import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import io.github.jerukan.physics.PhysicsConstants
import io.github.jerukan.physics.PhysicsObject
import io.github.jerukan.rendering.Drawable
import io.github.jerukan.rendering.camera.OrthoCameraWrapper
import io.github.jerukan.util.shapes.Circle
import kotlin.math.abs

class Planet(var name: String, mass: Float, position: Vector2, var radius: Float): PhysicsObject(mass, position, Circle(position, radius)), Drawable {
    //    var parser: Parser = Parser()
//    var info: JsonObject = parser.parse("planetList.json") as JsonObject
//    var planetinfo: JsonObject = info[planetname] as JsonObject
//
//    val name: String = planetinfo["name"] as String
    private var prevPos = Vector2(position) //used to detect where a planet would be at moment of a collision
    private var trail = ArrayList<Vector2>()

    private lateinit var gravitiesFromPlanets: ArrayList<Planet> //the ArrayList containing all other planets to calculate gravitational forces

    private var lineEnd = Vector2()

    private var projectedVel: Vector2 = Vector2()
    private var projectedAccel: Vector2 = Vector2()

    private var collided = false
    private var canCollide = true

    private var texture: Texture

    init {
        val circlePixmap = Pixmap((radius * 2).toInt(), (radius * 2).toInt(), Pixmap.Format.RGBA8888)
        circlePixmap.setColor(1f, 1f, 1f, 1f)
        circlePixmap.fillCircle(radius.toInt(), radius.toInt(), radius.toInt())
        texture = Texture(circlePixmap)
        circlePixmap.dispose()
    }

    fun setGravitiesFromPlanets(planets: ArrayList<Planet>) {
        gravitiesFromPlanets = planets
    }

    fun setCircularOrbit(other: Planet) {
        val dist = position.dst(other.position)
        val g = PhysicsConstants.G * other.mass / (dist * dist)

        val netVel = Math.sqrt(g * dist)
        velocity.x = (netVel * (position.y - other.position.y) / dist).toFloat() + other.velocity.x
        velocity.y = (netVel * (position.x - other.position.x) / dist).toFloat() + other.velocity.y
    }

    override fun updateVectors(deltaTime: Float) {
        projectedAccel.set(0f, 0f)
        gravitiesFromPlanets
                .filter { it != this }
                .forEach { projectedAccel.add(accelFromGravity(it)) }
        acceleration.set(projectedAccel)

        lineEnd.set(position.x + acceleration.x * 1000, position.y + acceleration.y * 1000)

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

    fun collidesPlanet(other: Planet): Boolean {
        return hitbox.collides(other.hitbox)
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

        //in the case the planetList are inside each other, set them to where they would've been at instant of collision
//        prevPos.set(collideposx, collideposy)

        projectedVel.x = (velocity.x * (mass - other.mass) + (2 * other.mass * other.velocity.x)) / (mass + other.mass)
        projectedVel.y = (velocity.y * (mass - other.mass) + (2 * other.mass * other.velocity.y)) / (mass + other.mass)

        println("original pos: $position")
        println("start pos: ($startposx, $startposy)")
        println("collide pos: ($collideposx, $collideposy)")
        println("end velocity: $projectedVel")
        println("-------------------------")
    }

    override fun render(batch: SpriteBatch) {
        batch.draw(texture, position.x, position.y)
    }

    override fun inCameraWindow(camera: OrthoCameraWrapper): Boolean {
        return true
    }

    override fun dispose() {
        texture.dispose()
    }
}