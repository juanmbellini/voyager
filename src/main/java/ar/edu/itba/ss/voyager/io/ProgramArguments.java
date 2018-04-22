package ar.edu.itba.ss.voyager.io;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Execution arguments.
 */
@Component
public class ProgramArguments {

    /**
     * The time step (i.e how much time elapses between two update events).
     */
    private final double timeStep;

    /**
     * The initial position of the Earth.
     */
    private final Vector2D earthPosition;
    /**
     * The initial velocity of the Earth.
     */
    private final Vector2D earthVelocity;
    /**
     * The initial acceleration of the Earth.
     */
    private final Vector2D earthAcceleration;
    /**
     * The initial position of Jupiter.
     */
    private final Vector2D jupiterPosition;
    /**
     * The initial velocity of Jupiter.
     */
    private final Vector2D jupiterVelocity;
    /**
     * The initial acceleration of Jupiter.
     */
    private final Vector2D jupiterAcceleration;
    /**
     * The initial position of Saturn.
     */
    private final Vector2D saturnPosition;
    /**
     * The initial velocity of Saturn.
     */
    private final Vector2D saturnVelocity;
    /**
     * The initial acceleration of Saturn.
     */
    private final Vector2D saturnAcceleration;

    /**
     * Constructor.
     *
     * @param timeStep             The time step (i.e how much time elapses between two update events).
     * @param earthXPosition       The 'x' component of the initial position of the Earth.
     * @param earthYPosition       The 'y' component of the initial position of the Earth.
     * @param earthXVelocity       The 'x' component of the initial velocity of the Earth.
     * @param earthYVelocity       The 'y' component of the initial velocity of the Earth.
     * @param earthXAcceleration   The 'x' component of the initial acceleration of the Earth.
     * @param earthYAcceleration   The 'y' component of the initial acceleration of the Earth.
     * @param jupiterXPosition     The 'x' component of the initial position of Jupiter.
     * @param jupiterYPosition     The 'y' component of the initial position of Jupiter.
     * @param jupiterXVelocity     The 'x' component of the initial velocity of Jupiter.
     * @param jupiterYVelocity     The 'y' component of the initial velocity of Jupiter.
     * @param jupiterXAcceleration The 'x' component of the initial acceleration of Jupiter.
     * @param jupiterYAcceleration The 'y' component of the initial acceleration of Jupiter.
     * @param saturnXPosition      The 'x' component of the initial position of Saturn.
     * @param saturnYPosition      The 'y' component of the initial position of Saturn.
     * @param saturnXVelocity      The 'x' component of the initial velocity of Saturn.
     * @param saturnYVelocity      The 'y' component of the initial velocity of Saturn.
     * @param saturnXAcceleration  The 'x' component of the initial acceleration of Saturn.
     * @param saturnYAcceleration  The 'y' component of the initial acceleration of Saturn.
     */
    public ProgramArguments(@Value("${custom.simulation.time-step}") final double timeStep,
                            // Earth
                            @Value("${custom.system.earth.position.x}") final double earthXPosition,
                            @Value("${custom.system.earth.position.y}") final double earthYPosition,
                            @Value("${custom.system.earth.velocity.x}") final double earthXVelocity,
                            @Value("${custom.system.earth.velocity.y}") final double earthYVelocity,
                            @Value("${custom.system.earth.acceleration.x}") final double earthXAcceleration,
                            @Value("${custom.system.earth.acceleration.y}") final double earthYAcceleration,
                            // Jupiter
                            @Value("${custom.system.jupiter.position.x}") final double jupiterXPosition,
                            @Value("${custom.system.jupiter.position.y}") final double jupiterYPosition,
                            @Value("${custom.system.jupiter.velocity.x}") final double jupiterXVelocity,
                            @Value("${custom.system.jupiter.velocity.y}") final double jupiterYVelocity,
                            @Value("${custom.system.jupiter.acceleration.x}") final double jupiterXAcceleration,
                            @Value("${custom.system.jupiter.acceleration.y}") final double jupiterYAcceleration,
                            // Saturn
                            @Value("${custom.system.saturn.position.x}") final double saturnXPosition,
                            @Value("${custom.system.saturn.position.y}") final double saturnYPosition,
                            @Value("${custom.system.saturn.velocity.x}") final double saturnXVelocity,
                            @Value("${custom.system.saturn.velocity.y}") final double saturnYVelocity,
                            @Value("${custom.system.saturn.acceleration.x}") final double saturnXAcceleration,
                            @Value("${custom.system.saturn.acceleration.y}") final double saturnYAcceleration) {
        this.timeStep = timeStep;
        this.earthPosition = new Vector2D(earthXPosition, earthYPosition);
        this.earthVelocity = new Vector2D(earthXVelocity, earthYVelocity);
        this.earthAcceleration = new Vector2D(earthXAcceleration, earthYAcceleration);
        this.jupiterPosition = new Vector2D(jupiterXPosition, jupiterYPosition);
        this.jupiterVelocity = new Vector2D(jupiterXVelocity, jupiterYVelocity);
        this.jupiterAcceleration = new Vector2D(jupiterXAcceleration, jupiterYAcceleration);
        this.saturnPosition = new Vector2D(saturnXPosition, saturnYPosition);
        this.saturnVelocity = new Vector2D(saturnXVelocity, saturnYVelocity);
        this.saturnAcceleration = new Vector2D(saturnXAcceleration, saturnYAcceleration);
    }

    /**
     * @return The time step (i.e how much time elapses between two update events).
     */
    public double getTimeStep() {
        return timeStep;
    }

    /**
     * @return The initial position of the Earth.
     */
    public Vector2D getEarthPosition() {
        return earthPosition;
    }

    /**
     * @return The initial velocity of the Earth.
     */
    public Vector2D getEarthVelocity() {
        return earthVelocity;
    }

    /**
     * @return The initial acceleration of the Earth.
     */
    public Vector2D getEarthAcceleration() {
        return earthAcceleration;
    }

    /**
     * @return The initial position of Jupiter.
     */
    public Vector2D getJupiterPosition() {
        return jupiterPosition;
    }

    /**
     * @return The initial velocity of Jupiter.
     */
    public Vector2D getJupiterVelocity() {
        return jupiterVelocity;
    }

    /**
     * @return The initial acceleration of Jupiter.
     */
    public Vector2D getJupiterAcceleration() {
        return jupiterAcceleration;
    }

    /**
     * @return The initial position of the Saturn.
     */
    public Vector2D getSaturnPosition() {
        return saturnPosition;
    }

    /**
     * @return The initial velocity of the Saturn.
     */
    public Vector2D getSaturnVelocity() {
        return saturnVelocity;
    }

    /**
     * @return The initial acceleration of the Saturn.
     */
    public Vector2D getSaturnAcceleration() {
        return saturnAcceleration;
    }
}
