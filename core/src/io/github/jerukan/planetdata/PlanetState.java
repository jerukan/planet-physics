package io.github.jerukan.planetdata;

import com.badlogic.gdx.math.Vector2;
import io.github.jerukan.physics.PhysicsObject;
import io.github.jerukan.physics.PhysicsState;
import io.github.jerukan.rendering.Drawable;

import java.util.ArrayList;

public class PlanetState extends PhysicsState {

    private ArrayList<Planet> planetList;
    private ArrayList<Drawable> drawables;

    public PlanetState(ArrayList<Planet> planetList, ArrayList<Drawable> drawables) {
        this.planetList = planetList;
        this.drawables = drawables;
    }

    @Override
    public void update() {
        for(int loops = 0; loops < warp; loops++) {
            for (int i = 0; i < planetList.size(); i++) {
                for (int j = i + 1; j < planetList.size(); j++) {
                    if (planetList.get(i).collidesPlanet(planetList.get(j))) {
                        planetList.get(i).onCollision(planetList.get(j));
                        planetList.get(j).onCollision(planetList.get(i));
                    }
                }
                planetList.get(i).updateVectors((float)timer.getDelta());
            }
            timer.update();
        }
    }

    public void add(Planet planet) {
        planetList.add(planet);
        planet.setGravitiesFromPlanets(planetList);
        drawables.add(planet);
    }

    public void add(Planet ... allPlanets) {
        for(Planet planet : allPlanets) {
            add(planet);
        }
    }

    @Override
    public void add(PhysicsObject physicsObject) {
        if(physicsObject instanceof Planet) {
            add((Planet) physicsObject);
        } else {
            super.add(physicsObject);
        }
    }
}