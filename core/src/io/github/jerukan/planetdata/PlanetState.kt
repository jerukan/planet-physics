package io.github.jerukan.planetdata

import com.badlogic.gdx.math.Vector2
import io.github.jerukan.physics.PhysicsState

class PlanetState(private val planetList: ArrayList<Planet>): PhysicsState() {
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
//    var planetList: Array<Planet> = arrayOf(mercury, venus, earth, mars, jupiter, saturn, uranus, neptune, pluto)
//    var p1 = Planet("p1", 500f, Vector2(-200f, 0f), 50f)
//    var p2 = Planet("p2", 500f, Vector2(200f, 0f), 50f)
//    var p3 = Planet("p3", 200f, Vector2(500f, 100f), 30f)

    override fun update() {
        for(i in planetList.indices) {
            for(j in IntRange(i + 1, planetList.size - 1)) {
                if(planetList[i].collidesPlanet(planetList[j])) {
                    planetList[i].onCollision(planetList[j])
                    planetList[j].onCollision(planetList[i])
                }
            }
            planetList[i].updateVectors(timer.getDelta().toFloat())
        }
        timer.update()
    }

    fun add(planet: Planet) {
        planetList.add(planet)
        planet.setGravitiesFromPlanets(planetList)
    }

    fun add(vararg allPlanets: Planet) {
        for(planet in allPlanets) {
            add(planet)
        }
    }
}