package io.github.jerukan.physics;

import com.badlogic.gdx.math.Vector2;
import io.github.jerukan.util.shapes.Shape;

public class PhysicsObject {

    public float mass;
    public Vector2 position;
    public Vector2 velocity = new Vector2();
    public Vector2 acceleration = new Vector2();
    public Shape hitbox;

    public PhysicsObject(float mass, Vector2 position, Shape hitbox) {
        this.mass = mass;
        this.position = position;
        this.hitbox = hitbox;
    }

    public void applyForce(Vector2 force) {
        acceleration.add(force.scl(1 / mass, 1 / mass));
    }

    public void updateVectors(float deltaTime) {
        velocity.add(acceleration.scl(deltaTime, deltaTime));
        position.add(velocity);
    }
}
