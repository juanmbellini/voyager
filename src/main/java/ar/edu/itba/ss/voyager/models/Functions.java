package ar.edu.itba.ss.voyager.models;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import java.util.function.BiFunction;

/**
 * Class containing several functions to be reused by the application.
 */
/* package */ class Functions {


    /**
     * Returns the gravitational force {@link BiFunction}.
     *
     * @param mass1 The mass of the first body.
     * @param mass2 The mass of the second body.
     * @return The {@link BiFunction} that takes two positions and
     * @apiNote This returns A {@link BiFunction} that takes two positions (of two bodies)
     * and returns the gravitational force the second is applying over the first one.
     */
    /* package */
    static BiFunction<Vector2D, Vector2D, Vector2D> gravitationalForce(double mass1, double mass2) {
        return (r1, r2) -> {
            final Vector2D difference = r2.subtract(r1);
            final double factor = -(Constants.G * mass1 * mass2) / Math.pow(difference.getNorm(), 3);
            return difference.scalarMultiply(factor);
        };
    }
}
