package io.github.jerukan.util;

public class Timer {

    public static final int NANO_TO_SECOND = 1000000000;

    private long startTime = 0;
    private long previousTime = 0;

    public Timer() {
        reset();
    }

    public void reset() {
        startTime = System.nanoTime();
        previousTime = System.nanoTime();
    }

    /**
     * Preferably called at the end of a loop to update the previous time.
     */
    public void update() {
        previousTime = System.nanoTime();
    }

    /**
     * Gets total time elapsed in seconds from last reset.
     */
    public double getElapsed() {
        return (double)(System.nanoTime() - startTime) / NANO_TO_SECOND;
    }

    /**
     * Gets the delta time from the previous time it was updated.
     */
    public double getDelta() {
        return (double)(System.nanoTime() - previousTime) / NANO_TO_SECOND;
    }
}