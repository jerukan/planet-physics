package io.github.jerukan.util

import io.github.jerukan.physics.PhysicsConstants
import kotlin.math.abs

class Utils {

    object Constants {
        //i know it's not to scale i don't care
        val km_per_pixel: Float = 2000000f   //how much the kilometers are divided by
        val planet_size_multipler: Float = 2000f  //make planets more visible(?)
        val sun_radius: Float = 695700f
        val sun_draw_radius: Float = sun_radius / km_per_pixel * 50 //to make sun not a tiny dot
        val t_step: Double = 0.0003
    }

    object Functions {
        fun wrapRadians(theta: Double): Double {
            var num = (theta - PhysicsConstants.PI) % PhysicsConstants.PI2

            if (num < 0) {
                num += PhysicsConstants.PI
            } else {
                num -= PhysicsConstants.PI
            }
            return num
        }

        fun withinThreshold(num: Float, target: Float, threshold: Float): Boolean {
            return abs(target - num) < abs(threshold)
        }
    }
}