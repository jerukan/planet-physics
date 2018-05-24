package io.github.jerukan.planetdata;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import io.github.jerukan.physics.PhysicsConstants;
import io.github.jerukan.physics.PhysicsObject;
import io.github.jerukan.rendering.Drawable;
import io.github.jerukan.rendering.camera.OrthoCameraWrapper;
import io.github.jerukan.util.shapes.Circle;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Planet extends PhysicsObject implements Drawable {

    public String name;
    public float radius;
    private ArrayList<Planet> gravitiesFromPlanets;
    private Vector2 projectedVel = new Vector2();
    private Vector2 projectedAccel = new Vector2();
    private boolean collided = false;
    private Texture texture;

    public Planet(String name, float mass, Vector2 position, float radius) {
        super(mass, position, new Circle(position, radius));
        this.name = name;
        this.radius = radius;
        Pixmap circlePixmap = new Pixmap((int)(radius * 2), (int)(radius * 2), Pixmap.Format.RGBA8888);
        circlePixmap.setColor(1f, 1f, 1f, 1f);
        circlePixmap.fillCircle((int)radius, (int)radius, (int)radius);
        texture = new Texture(circlePixmap);
        circlePixmap.dispose();
    }

    public void setGravitiesFromPlanets(ArrayList<Planet> planets) {
        gravitiesFromPlanets = planets;
    }

    public void setCircularOrbit(Planet other) {
        double dist = position.dst(other.position);
        double g = PhysicsConstants.G * other.mass / (dist * dist);

        double netVel = Math.sqrt(g * dist);
        velocity.x = (float)(netVel * (position.y - other.position.y) / dist) + other.velocity.x;
        velocity.y = (float)(netVel * (position.x - other.position.x) / dist) + other.velocity.y;
    }

    public void updateVectors(float deltaTime) {
        projectedAccel.set(0f, 0f);
        for(Planet it : gravitiesFromPlanets) {
            if(it != this) {
                projectedAccel.add(accelFromGravity(it));
            }
        }
        acceleration.set(projectedAccel);

        if(collided) {
            velocity.set(projectedVel);

//            position.set(prevPos)
            projectedVel.set(0f, 0f);
            collided = false;
        }
        else {
            velocity.add(acceleration);
        }

        position.add(velocity);
    }

    private Vector2 accelFromGravity(Planet p) {
        double dist = position.dst(p.position);
        double g = PhysicsConstants.G * p.mass / (dist * dist);

        float xa = (float)-(g * ((position.x - p.position.x) / dist));
        float ya = (float)-(g * ((position.y - p.position.y) / dist));

        return new Vector2(xa, ya);
    }

    public boolean collidesPlanet(Planet other){
        return hitbox.collides(other.hitbox);
    }

    public void onCollision(Planet other) {
        collided = true;

        float startposx = position.x - velocity.x;
        float startposy = position.y - velocity.y;

        float otherstartposx = other.position.x - other.velocity.x;
        float otherstartposy = other.position.y - other.velocity.y;

        System.out.println("startposx: $startposx\n" +
                "velocity: $velocity\n" +
                "otherstartpos: $otherstartposx\n" +
                "othervel: ${other.velocity}\n");
        float collideposx;
        if(velocity.x - other.velocity.x != 0f) {
            collideposx = startposx + velocity.x * ((otherstartposx - startposx) / (velocity.x - other.velocity.x)) - radius * (Math.abs(velocity.x) / velocity.x);
        }
        else {
            collideposx = position.x;
        }

        float collideposy;
        if(velocity.y - other.velocity.y != 0f) {
            collideposy = startposy + velocity.y * ((otherstartposy - startposy) / (velocity.y - other.velocity.y)) - radius * (Math.abs(velocity.y) / velocity.y);
        }
        else {
            collideposy = position.y;
        }

        //in the case the planetList are inside each other, set them to where they would've been at instant of collision
//        prevPos.set(collideposx, collideposy)

        projectedVel.x = (velocity.x * (mass - other.mass) + (2 * other.mass * other.velocity.x)) / (mass + other.mass);
        projectedVel.y = (velocity.y * (mass - other.mass) + (2 * other.mass * other.velocity.y)) / (mass + other.mass);

        System.out.println("original pos: $position");
        System.out.println("start pos: ($startposx, $startposy)");
        System.out.println("collide pos: ($collideposx, $collideposy)");
        System.out.println("end velocity: $projectedVel");
        System.out.println("-------------------------");
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x, position.y);
    }

    @Override
    public boolean inCameraWindow(OrthoCameraWrapper camera) {
        return true;
    }

    @Override
    public void dispose() {
        texture.dispose();
    }
}
