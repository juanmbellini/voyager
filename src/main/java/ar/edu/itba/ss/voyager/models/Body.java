package ar.edu.itba.ss.voyager.models;

import ar.edu.itba.ss.g7.engine.simulation.State;
import ar.edu.itba.ss.g7.engine.simulation.StateHolder;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

/**
 * Represents a body in the system.
 */
public class Body implements StateHolder<Body.BodyState> {

    /**
     * The body's mass (in kilograms).
     */
    private final double mass;

    /**
     * The body's position (represented as a 2D vector).
     */
    private Vector2D position;

    /**
     * The body's velocity (represented as a 2D vector).
     */
    private Vector2D velocity;

    /**
     * The body's acceleration (represented as a 2D vector)
     */
    private Vector2D acceleration;

    //TODO: both previous variables may be removed according to the update strategy that we decide to use

    /**
     * The body's previous acceleration (represented as a 2D vector)
     */
    private Vector2D previousAcceleration;

    /**
     * The body's previous position (represented as a 2D vector)
     */
    private Vector2D previousPosition;

    /**
     * Constructor.
     *
     * @param mass         The body's mass (in kilograms).
     * @param position     The body's position.
     * @param velocity     The body's velocity.
     * @param acceleration The body's acceleration.
     */
    public Body(final double mass, final Vector2D position, final Vector2D velocity, final Vector2D acceleration) {
        this.mass = mass;
        this.position = new Vector2D(position.getX(), position.getY());
        this.velocity = new Vector2D(velocity.getX(), velocity.getY());
        previousAcceleration = acceleration;
        previousPosition = null;
    }

    /**
     * Calculates the gravitational force
     * that the given {@code other} {@link Body} applies to {@code this} {@link Body}.
     *
     * @param other The {@link Body} applying a gravitational force over {@code this} {@link Body}.
     * @return The force.
     */
    public Vector2D appliedGravitationalForce(final Body other) {
        //TODO: also this could be in System as a force provider
        final Vector2D difference = other.getPosition().subtract(this.position);
        final double factor = -(Constants.G * this.mass * other.getMass()) / Math.pow(difference.getNorm(), 3);
        return difference.scalarMultiply(factor);
    }

    /**
     * @return The body's mass.
     */
    public double getMass() {
        return mass;
    }

    /**
     * @return The body's position (represented as a 2D vector).
     */
    public Vector2D getPosition() {
        return position;
    }

    /**
     * @return The body's velocity (represented as a 2D vector).
     */
    public Vector2D getVelocity() {
        return velocity;
    }

    /**
     * @return The body's aceleration (represented as a 2D vector).
     */
    public Vector2D getAcceleration() {
        return acceleration;
    }

    /**
     * @return The body's kinetic energy.
     */
    public double getKineticEnergy() {
        return 0.5 * mass * velocity.getNormSq();
    }

    /**
     * Calculates the potential energy of a two {@link Body}'s system.
     *
     * @param other The other body.
     * @return The potential energy of the system.
     */
    public double getPotentialEnergy(final Body other) {
        return -Constants.G * mass * other.getMass() / position.subtract(other.getPosition()).getNorm();
    }

    @Override
    public BodyState outputState() {
        return new BodyState(this);
    }

    /**
     * Represents the state of a given body.
     */
    public static final class BodyState implements State {

        /**
         * The {@link Body}'s mass.
         */
        private final double mass;

        /**
         * The {@link Body}'s position (represented as a 2D vector).
         */
        private final Vector2D position;

        /**
         * The {@link Body}'s velocity (represented as a 2D vector).
         */
        private final Vector2D velocity;

        /**
         * The {@link Body}'s acceleration (represented as a 2D vector).
         */
        private final Vector2D acceleration;

        /**
         * Constructor.
         *
         * @param body The {@link Body}'s whose state will be represented.
         */
        /* default */ BodyState(final Body body) {
            mass = body.getMass();
            position = body.getPosition(); // The Vector2D class is unmodifiable.
            velocity = body.getVelocity(); // The Vector2D class is unmodifiable.
            acceleration = body.getAcceleration(); // The Vector2D class is unmodifiable.
        }

        /**
         * The {@link Body}'s mass.
         */
        public double getMass() {
            return mass;
        }

        /**
         * The {@link Body}'s position (represented as a 2D vector).
         */
        public Vector2D getPosition() {
            return position;
        }

        /**
         * The {@link Body}'s velocity (represented as a 2D vector).
         */
        public Vector2D getVelocity() {
            return velocity;
        }

        /**
         * The {@link Body}'s velocity (represented as a 2D vector).
         */
        public Vector2D getAcceleration() {
            return acceleration;
        }
    }
}