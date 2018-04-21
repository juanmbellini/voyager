package ar.edu.itba.ss.voyager.models;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

/**
 * Class containing several util methods to be reused by the application.
 */
/* package */ class Utils {


    /**
     * Returns the gravitational force of two bodies with the given positions and masses.
     *
     * @param mass1     The mass of the first body.
     * @param mass2     The mass of the second body.
     * @param position1 The position of the first body.
     * @param position2 The position of the second body.
     * @return The gravitational force.
     */
    /* package */
    static Vector2D gravitationalForce(double mass1, double mass2, Vector2D position1, Vector2D position2) {
        final Vector2D difference = position2.subtract(position1);
        final double factor = -(Constants.G * mass1 * mass2) / Math.pow(difference.getNorm(), 3);
        return difference.scalarMultiply(factor);
    }
}
