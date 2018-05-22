package io.github.jerukan.planetdata

import com.badlogic.gdx.math.Vector2
import io.github.jerukan.physics.PhysicsState

class PlanetState: PhysicsState() {
//    var mercury: Planet = Planet("mercury")
//    var venus: Planet = Planet("venus")
//    var earth: Planet = Planet("earth")
//    var mars: Planet = Planet("mars")
//    var jupiter: Planet = Planet("jupiter")
//    var saturn: Planet = Planet("saturn")
//    var uranus: Planet = Planet("uranus")
//    var neptune: Planet = Planet("neptune")
//    var pluto: Planet = Planet("pluto")
//
//    var planets: Array<Planet> = arrayOf(mercury, venus, earth, mars, jupiter, saturn, uranus, neptune, pluto)
    var p1 = Planet("p1", 500f, Vector2(-200f, 0f), 50f)
    var p2 = Planet("p2", 500f, Vector2(200f, 0f), 50f)
    var p3 = Planet("p3", 200f, Vector2(500f, 100f), 30f)

    var planets: ArrayList<Planet> = ArrayList()

    fun init() {
        add(p1, p2)
        p2.setCircularOrbit(p1)
    }

    override fun update() {
        for(i in planets.indices) {
            for(j in IntRange(i + 1, planets.size - 1)) {
                if(planets[i].collidesPlanet(planets[j])) {
                    planets[i].onCollision(planets[j])
                    planets[j].onCollision(planets[i])
                }
            }
            planets[i].updateVectors(timer.getDelta().toFloat())
        }
        timer.update()
    }

    fun add(planet: Planet) {
        planets.add(planet)
        planet.setGravitiesFromPlanets(planets)
    }

    fun add(vararg allPlanets: Planet) {
        for(planet in allPlanets) {
            add(planet)
        }
    }
}