package io.github.jerukan.physics;

import io.github.jerukan.util.Timer;

import java.util.ArrayList;

public class PhysicsState {

    public int warp = 1;
    protected Timer timer = new Timer();
    protected ArrayList<PhysicsObject> physicsObjects = new ArrayList<PhysicsObject>();

    public void resetTimer() {
        timer.reset();
    }

    public void update() {
        for(PhysicsObject obj : physicsObjects) {
            obj.updateVectors((float) timer.getDelta());
        }
        timer.update();
    }

    public void add(PhysicsObject physicsObject) {
        physicsObjects.add(physicsObject);
    }
}
