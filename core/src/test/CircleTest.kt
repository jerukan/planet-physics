package test

import io.github.jerukan.physics.Circle
import io.github.jerukan.physics.Rectangle
import io.github.jerukan.util.Position
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Test

class CircleTest {

    @Test
    fun circleCollisionRect() {
        //given
        val circ: Circle = Circle(1f, Position(0f, 0f), 5f)
        val rect: Rectangle = Rectangle(1f, Position(0f, 5f), 10f, 5f)
        //when
        val result = circ.collidesRect(rect)
        //then
        assertTrue(result)
    }

    @Test
    fun rectInCircle() {
        val circ = Circle(1f, Position(0f, 0f), 3f)
        val rect = Rectangle(1f, Position(0f, 0f), 1f, 1f)
        rect.setCenter(0f, 0f)

        val result = circ.collidesRect(rect)

        assertTrue(result)
    }
}