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
     * Amount of Saturnian years (i.e amount of time the simulation will last).
     */
    private final int amountOfYears;

    /**
     * The initial position of the Sun.
     */
    private final Vector2D sunPosition;
    /**
     * The initial velocity of the Sun.
     */
    private final Vector2D sunVelocity;
    /**
     * The initial position of the Earth.
     */
    private final Vector2D earthPosition;
    /**
     * The initial velocity of the Earth.
     */
    private final Vector2D earthVelocity;
    /**
     * The initial position of Jupiter.
     */
    private final Vector2D jupiterPosition;
    /**
     * The initial velocity of Jupiter.
     */
    private final Vector2D jupiterVelocity;
    /**
     * The initial position of Saturn.
     */
    private final Vector2D saturnPosition;
    /**
     * The initial velocity of Saturn.
     */
    private final Vector2D saturnVelocity;

    /**
     * Path for Ovito file.
     */
    private final String ovitoFilePath;

    /**
     * Constructor.
     *
     * @param timeStep         The time step (i.e how much time elapses between two update events).
     * @param amountOfYears    Amount of Saturnian years (i.e amount of time the simulation will last).
     * @param earthXPosition   The 'x' component of the initial position of the Earth.
     * @param earthYPosition   The 'y' component of the initial position of the Earth.
     * @param earthXVelocity   The 'x' component of the initial velocity of the Earth.
     * @param earthYVelocity   The 'y' component of the initial velocity of the Earth.
     * @param jupiterXPosition The 'x' component of the initial position of Jupiter.
     * @param jupiterYPosition The 'y' component of the initial position of Jupiter.
     * @param jupiterXVelocity The 'x' component of the initial velocity of Jupiter.
     * @param jupiterYVelocity The 'y' component of the initial velocity of Jupiter.
     * @param saturnXPosition  The 'x' component of the initial position of Saturn.
     * @param saturnYPosition  The 'y' component of the initial position of Saturn.
     * @param saturnXVelocity  The 'x' component of the initial velocity of Saturn.
     * @param saturnYVelocity  The 'y' component of the initial velocity of Saturn.
     * @param ovitoFilePath
     */
    public ProgramArguments(@Value("${custom.simulation.time-step}") final double timeStep,
                            // Sun
                            @Value("${custom.simulation.years}") int amountOfYears,
                            @Value("${custom.system.sun.position.x}") final double sunXPosition,
                            @Value("${custom.system.sun.position.y}") final double sunYPosition,
                            @Value("${custom.system.sun.velocity.x}") final double sunXVelocity,
                            @Value("${custom.system.sun.velocity.y}") final double sunYVelocity,
                            // Earth
                            @Value("${custom.system.earth.position.x}") final double earthXPosition,
                            @Value("${custom.system.earth.position.y}") final double earthYPosition,
                            @Value("${custom.system.earth.velocity.x}") final double earthXVelocity,
                            @Value("${custom.system.earth.velocity.y}") final double earthYVelocity,
                            // Jupiter
                            @Value("${custom.system.jupiter.position.x}") final double jupiterXPosition,
                            @Value("${custom.system.jupiter.position.y}") final double jupiterYPosition,
                            @Value("${custom.system.jupiter.velocity.x}") final double jupiterXVelocity,
                            @Value("${custom.system.jupiter.velocity.y}") final double jupiterYVelocity,
                            // Saturn
                            @Value("${custom.system.saturn.position.x}") final double saturnXPosition,
                            @Value("${custom.system.saturn.position.y}") final double saturnYPosition,
                            @Value("${custom.system.saturn.velocity.x}") final double saturnXVelocity,
                            @Value("${custom.system.saturn.velocity.y}") final double saturnYVelocity,
                            // File paths
                            @Value("${custom.output.ovito}") String ovitoFilePath) {
        this.timeStep = timeStep;
        this.amountOfYears = amountOfYears;
        this.sunPosition = new Vector2D(sunXPosition, sunYPosition);
        this.sunVelocity = new Vector2D(sunXVelocity, sunYVelocity);
        this.earthPosition = new Vector2D(earthXPosition, earthYPosition);
        this.earthVelocity = new Vector2D(earthXVelocity, earthYVelocity);
        this.jupiterPosition = new Vector2D(jupiterXPosition, jupiterYPosition);
        this.jupiterVelocity = new Vector2D(jupiterXVelocity, jupiterYVelocity);
        this.saturnPosition = new Vector2D(saturnXPosition, saturnYPosition);
        this.saturnVelocity = new Vector2D(saturnXVelocity, saturnYVelocity);
        this.ovitoFilePath = ovitoFilePath;
    }

    /**
     * @return The time step (i.e how much time elapses between two update events).
     */
    public double getTimeStep() {
        return timeStep;
    }

    /**
     * @return Amount of Saturnian years (i.e amount of time the simulation will last).
     */
    public int getAmountOfYears() {
        return amountOfYears;
    }

    /**
     * @return The initial position of the Sun.
     */
    public Vector2D getSunPosition() {
        return sunPosition;
    }

    /**
     * @return The initial velocity of the Sun.
     */
    public Vector2D getSunVelocity() {
        return sunVelocity;
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
     * @return Path for Ovito file.
     */
    public String getOvitoFilePath() {
        return ovitoFilePath;
    }
}
