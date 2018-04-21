package ar.edu.itba.ss.voyager.models;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

/**
 * Describes all types of bodies that could exist in this universe.
 */
public enum BodyType {
    /**
     * The sun.
     */
    SUN {
        @Override
        Body provide(final Vector2D initialPosition,
                     final Vector2D initialVelocity,
                     final Vector2D initialAcceleration) {
            return new Body(Constants.SUN_MASS, initialPosition, initialVelocity, initialAcceleration);
        }
    },
    /**
     * THe Earth.
     */
    EARTH {
        @Override
        Body provide(final Vector2D initialPosition,
                     final Vector2D initialVelocity,
                     final Vector2D initialAcceleration) {
            return new Body(Constants.EARTH_MASS, initialPosition, initialVelocity, initialAcceleration);
        }
    },
    /**
     * Jupiter.
     */
    JUPITER {
        @Override
        Body provide(final Vector2D initialPosition,
                     final Vector2D initialVelocity,
                     final Vector2D initialAcceleration) {
            return new Body(Constants.JUPITER_MASS, initialPosition, initialVelocity, initialAcceleration);
        }
    },
    /**
     * Saturn.
     */
    SATURN {
        @Override
        Body provide(final Vector2D initialPosition,
                     final Vector2D initialVelocity,
                     final Vector2D initialAcceleration) {
            return new Body(Constants.SATURN_MASS, initialPosition, initialVelocity, initialAcceleration);
        }
    },
    /**
     * The ship.
     */
    SHIP {
        @Override
        Body provide(final Vector2D initialPosition,
                     final Vector2D initialVelocity,
                     final Vector2D initialAcceleration) {
            return new Body(Constants.SHIP_MASS, initialPosition, initialVelocity, initialAcceleration);
        }
    };

    /**
     * Builds a {@link Body} according to the value.
     *
     * @param initialPosition     The Body's initial position.
     * @param initialVelocity     The Body's initial velocity.
     * @param initialAcceleration The Body's initial acceleration.
     * @return The built {@link Body}.
     */
    /* package */
    abstract Body provide(final Vector2D initialPosition,
                          final Vector2D initialVelocity,
                          final Vector2D initialAcceleration);
}
