package io.github.jerukan.planetdata

import com.badlogic.gdx.math.Vector2

object Planets {
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
//    var planetlist: Array<Planet> = arrayOf(mercury, venus, earth, mars, jupiter, saturn, uranus, neptune, pluto)
    var p1 = Planet("p1", 5000f, Vector2(-200f, 0f), 50f)
    var p2 = Planet("p2", 5000f, Vector2(200f, 0f), 50f)
    var p3 = Planet("p3", 2f, Vector2(2100f, 0f), 3f)

    var planetlist: Array<Planet> = arrayOf(p1, p2)

    fun init() {
//        p2.setCircularOrbit(p1)
    }

    fun updatePositions(time: Float) {
        for(i in planetlist.indices) {
            for(j in IntRange(i + 1, planetlist.size - 1)) {
                if(planetlist[i].collidesPlanet(planetlist[j])) {
                    planetlist[i].onCollision(planetlist[j])
                    planetlist[j].onCollision(planetlist[i])
                }
            }
            planetlist[i].update(planetlist, time)
        }
    }
}