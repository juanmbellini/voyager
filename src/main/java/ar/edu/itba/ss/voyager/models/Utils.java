package ar.edu.itba.ss.voyager.models;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

/**
 * Class containing several util methods to be reused by the application.
 */
/* package */ class Utils {


    /**
     * Returns the gravitational force of two bodies with the given positions and masses.
     *
     * @param affectedMass     The mass of the first body.
     * @param influencerMass     The mass of the second body.
     * @param affectedPosition The position of the first body.
     * @param influencerPosition The position of the second body.
     * @return The gravitational force.
     */
    /* package */
    static Vector2D gravitationalForce(double affectedMass, double influencerMass,
                                       Vector2D affectedPosition, Vector2D influencerPosition) {
        final Vector2D difference = affectedPosition.subtract(influencerPosition);
        final double factor = (-Constants.G * affectedMass * influencerMass) / Math.pow(difference.getNorm(), 3);
        return difference.scalarMultiply(factor);
    }
}
