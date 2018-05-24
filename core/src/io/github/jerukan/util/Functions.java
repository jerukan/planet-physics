package io.github.jerukan.util;

import io.github.jerukan.physics.PhysicsConstants;

public class Functions {
    public static double wrapRadians(double theta) {
        double num = (theta - PhysicsConstants.PI) % PhysicsConstants.PI2;

        if (num < 0) {
            num += PhysicsConstants.PI;
        } else {
            num -= PhysicsConstants.PI;
        }
        return num;
    }

    public static boolean withinThreshold(float num, float target, float threshold) {
        return Math.abs(target - num) < Math.abs(threshold);
    }

    /**
     * Insertion sort from least to greatest.
     * Sorts the section of the array from [start, end).
     * A useless function just for a computer science project requirement :^)
     */
    public static void insertionSort(int[] array, int start, int end) {
        for (int i = start + 1; i < end; i++) {
            if (array[i] < array[i - 1]) {
                for (int j = start; j < i; j++){
                    if (array[i] < array[j]) {
                        swap(array, i, j);
                    }
                }
            }
        }
    }

    private static void swap(int[] array, int first, int second) {
        int temp = array[first];
        array[first] = array[second];
        array[second] = temp;
    }
}