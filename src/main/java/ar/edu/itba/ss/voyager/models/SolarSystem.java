package ar.edu.itba.ss.voyager.models;

import ar.edu.itba.ss.g7.engine.models.System;
import ar.edu.itba.ss.g7.engine.simulation.State;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Represents the Solar System to be simulated.
 */
public class SolarSystem implements System<SolarSystem.SolarSystemState> {

    /**
     * Indicates the ship's altitude in regards to the Earth's surface in meters.
     */
    private static final double SHIP_ALTITUDE = 1500 * 1000;

    /**
     * The Earth's radius.
     */
    private static final double EARTH_RADIUS = 6371 * 1000;

    /**
     * The ship's initial speed (velocity module) in meters over seconds
     */
    private static final double SHIP_INITIAL_SPEED = 14000;

    // ================================================================================================================
    // System stuff
    // ================================================================================================================

    /**
     * The Sun.
     */
    private final Body sun;

    /**
     * The Earth.
     */
    private final Body earth;

    /**
     * Jupiter.
     */
    private final Body jupiter;

    /**
     * Saturn.
     */
    private final Body saturn;

    /**
     * The ship.
     */
    private final Body ship;

    // ================================================================================================================
    // Updating stuff
    // ================================================================================================================

    /**
     * The time step (i.e how much time elapses between two update events).
     */
    private final double timeStep;

    /**
     * Amount of Saturnian years (i.e amount of time the simulation will last).
     */
    private final int saturnianYears;

    /**
     * The amount of time the system has existed.
     */
    private double actualTime;

    /**
     * {@link Map} holding, for each, body, the {@link List} of {@link Body} that gravitationally influence them.
     */
    private final Map<Body, List<Body>> influencers;

    /**
     * A {@link Map} containing the previous accelerations for the the bodies in this solar system.
     */
    private final Map<Body, Vector2D> previousAccelerations;

    // ================================================================================================================
    // Restarting stuff
    // ================================================================================================================

    /**
     * The initial position of the Sun (i.e for restarting stuff).
     */
    private final Vector2D sunInitialPosition;
    /**
     * The initial velocity of the Sun (i.e for restarting stuff).
     */
    private final Vector2D sunInitialVelocity;
    /**
     * The initial acceleration of the Sun (i.e for restarting stuff).
     */
    private final Vector2D sunInitialAcceleration;
    /**
     * The initial position of the Earth (i.e for restarting stuff).
     */
    private final Vector2D earthInitialPosition;
    /**
     * The initial velocity of the Earth (i.e for restarting stuff).
     */
    private final Vector2D earthInitialVelocity;
    /**
     * The initial acceleration of the Earth (i.e for restarting stuff).
     */
    private final Vector2D earthInitialAcceleration;
    /**
     * The initial position of Jupiter (i.e for restarting stuff).
     */
    private final Vector2D jupiterInitialPosition;
    /**
     * The initial velocity of Jupiter (i.e for restarting stuff).
     */
    private final Vector2D jupiterInitialVelocity;
    /**
     * The initial acceleration of Jupiter (i.e for restarting stuff).
     */
    private final Vector2D jupiterInitialAcceleration;
    /**
     * The initial position of Saturn (i.e for restarting stuff).
     */
    private final Vector2D saturnInitialPosition;
    /**
     * The initial velocity of Saturn (i.e for restarting stuff).
     */
    private final Vector2D saturnInitialVelocity;
    /**
     * The initial acceleration of Saturn (i.e for restarting stuff).
     */
    private final Vector2D saturnInitialAcceleration;
    /**
     * The initial position of the ship (i.e for restarting stuff).
     */
    private final Vector2D shipInitialPosition;
    /**
     * The initial velocity of the ship (i.e for restarting stuff).
     */
    private final Vector2D shipInitialVelocity;
    /**
     * The initial acceleration of the ship (i.e for restarting stuff).
     */
    private final Vector2D shipInitialAcceleration;

    /**
     * Constructor.
     *
     * @param timeStep               The time step (i.e how much time elapses between two update events).
     * @param saturnianYears         Amount of Saturnian years (i.e amount of time the simulation will last).
     * @param sunInitialPosition     The Sun's initial position.
     * @param sunInitialVelocity     The Sun's initial velocity.
     * @param earthInitialPosition   The Earth's initial position.
     * @param earthInitialVelocity   The Earth's initial velocity.
     * @param jupiterInitialPosition Jupiter's initial position.
     * @param jupiterInitialVelocity Jupiter's initial velocity.
     * @param saturnInitialPosition  Saturn's initial position.
     * @param saturnInitialVelocity  Saturn's initial velocity.
     */
    public SolarSystem(double timeStep, int saturnianYears,
                       final Vector2D sunInitialPosition, final Vector2D sunInitialVelocity,
                       final Vector2D earthInitialPosition, final Vector2D earthInitialVelocity,
                       final Vector2D jupiterInitialPosition, final Vector2D jupiterInitialVelocity,
                       final Vector2D saturnInitialPosition, final Vector2D saturnInitialVelocity) {
        // Initialize positions and velocities
        this.sunInitialPosition = sunInitialPosition;
        this.sunInitialVelocity = sunInitialVelocity;
        this.earthInitialPosition = earthInitialPosition;
        this.earthInitialVelocity = earthInitialVelocity;
        this.jupiterInitialPosition = jupiterInitialPosition;
        this.jupiterInitialVelocity = jupiterInitialVelocity;
        this.saturnInitialPosition = saturnInitialPosition;
        this.saturnInitialVelocity = saturnInitialVelocity;
        this.shipInitialPosition = calculateShipInitialPosition(sunInitialPosition, earthInitialPosition);
        this.shipInitialVelocity = calculateShipInitialVelocity(earthInitialVelocity);

        // Calculate initial accelerations according to initial positions
        this.sunInitialAcceleration = getAcceleration(sunInitialPosition,
                earthInitialPosition, jupiterInitialPosition, saturnInitialPosition, shipInitialPosition,
                Constants.SUN_MASS,
                Constants.EARTH_MASS, Constants.JUPITER_MASS, Constants.SATURN_MASS, Constants.SHIP_MASS);
        this.earthInitialAcceleration = getAcceleration(earthInitialPosition,
                sunInitialPosition, jupiterInitialPosition, saturnInitialPosition, shipInitialPosition,
                Constants.EARTH_MASS,
                Constants.SUN_MASS, Constants.JUPITER_MASS, Constants.SATURN_MASS, Constants.SHIP_MASS);
        this.jupiterInitialAcceleration = getAcceleration(jupiterInitialPosition,
                sunInitialPosition, earthInitialPosition, saturnInitialPosition, shipInitialPosition,
                Constants.JUPITER_MASS,
                Constants.SUN_MASS, Constants.EARTH_MASS, Constants.SATURN_MASS, Constants.SHIP_MASS);
        this.saturnInitialAcceleration = getAcceleration(saturnInitialPosition,
                sunInitialPosition, earthInitialPosition, jupiterInitialPosition, shipInitialPosition,
                Constants.SATURN_MASS,
                Constants.SUN_MASS, Constants.EARTH_MASS, Constants.JUPITER_MASS, Constants.SHIP_MASS);
        this.shipInitialAcceleration = getAcceleration(shipInitialPosition,
                sunInitialPosition, earthInitialPosition, jupiterInitialPosition, saturnInitialPosition,
                Constants.SHIP_MASS,
                Constants.SUN_MASS, Constants.EARTH_MASS, Constants.JUPITER_MASS, Constants.SATURN_MASS);

        // Initialize bodies
        this.sun = BodyType.SUN.provide(sunInitialPosition, sunInitialVelocity, sunInitialAcceleration);
        this.earth = BodyType.EARTH.provide(earthInitialPosition, earthInitialVelocity, earthInitialAcceleration);
        this.jupiter = BodyType.JUPITER.provide(jupiterInitialPosition, jupiterInitialVelocity, jupiterInitialAcceleration);
        this.saturn = BodyType.SATURN.provide(saturnInitialPosition, saturnInitialVelocity, saturnInitialAcceleration);
        this.ship = BodyType.SHIP.provide(shipInitialPosition, shipInitialVelocity, shipInitialAcceleration);

        // Initialize the influencers maps
        this.influencers = new HashMap<>();
        this.influencers.put(sun, Stream.of(earth, jupiter, saturn, ship).collect(Collectors.toList()));
        this.influencers.put(earth, Stream.of(sun, jupiter, saturn, ship).collect(Collectors.toList()));
        this.influencers.put(jupiter, Stream.of(sun, earth, saturn, ship).collect(Collectors.toList()));
        this.influencers.put(saturn, Stream.of(sun, earth, jupiter, ship).collect(Collectors.toList()));
        this.influencers.put(ship, Stream.of(sun, earth, jupiter, saturn).collect(Collectors.toList()));

        // Initialize integration mechanism stuff
        this.timeStep = timeStep;
        this.saturnianYears = saturnianYears;
        this.actualTime = 0;
        this.previousAccelerations = new HashMap<>();
        initializePreviousAccelerations();
    }

    /**
     * Initializes the previous accelerations {@link Map} (i.e to have the previous accelerations in the first step).
     */
    private void initializePreviousAccelerations() {
        if (this.previousAccelerations == null) {
            throw new IllegalStateException("This method should be called" +
                    " once the previous accelerations map is initialized");
        }

        // Initialize The Sun's velocity and position at -deltaT
        final Vector2D sunActualPosition = sun.getPosition();
        final double sunMass = sun.getMass();
        final Vector2D sunForce = getAppliedForce(sun);
        final Vector2D sunPreviousVelocity = sun.getVelocity()
                .subtract(sunForce.scalarMultiply(timeStep / sunMass));
        final Vector2D sunPreviousPosition = sunActualPosition
                .subtract(sunPreviousVelocity.scalarMultiply(timeStep))
                .add(sunForce.scalarMultiply((timeStep * timeStep) / (2 * sunMass)));

        // Initialize The Earth's velocity and position at -deltaT
        final Vector2D earthActualPosition = earth.getPosition();
        final double earthMass = earth.getMass();
        final Vector2D earthForce = getAppliedForce(earth);
        final Vector2D earthPreviousVelocity = earth.getVelocity()
                .subtract(earthForce.scalarMultiply(timeStep / earthMass));
        final Vector2D earthPreviousPosition = earthActualPosition
                .subtract(earthPreviousVelocity.scalarMultiply(timeStep))
                .add(earthForce.scalarMultiply((timeStep * timeStep) / (2 * earthMass)));

        // Initialize Jupiter's velocity and position at -deltaT
        final Vector2D jupiterActualPosition = jupiter.getPosition();
        final double jupiterMass = jupiter.getMass();
        final Vector2D jupiterForce = getAppliedForce(jupiter);
        final Vector2D jupiterPreviousVelocity = jupiter.getVelocity()
                .subtract(jupiterForce.scalarMultiply(timeStep / jupiterMass));
        final Vector2D jupiterPreviousPosition = jupiterActualPosition
                .subtract(jupiterPreviousVelocity.scalarMultiply(timeStep))
                .add(jupiterForce.scalarMultiply((timeStep * timeStep) / (2 * jupiterMass)));

        // Initialize Saturn's velocity and position at -deltaT
        final Vector2D saturnActualPosition = saturn.getPosition();
        final double saturnMass = saturn.getMass();
        final Vector2D saturnForce = getAppliedForce(saturn);
        final Vector2D saturnPreviousVelocity = saturn.getVelocity()
                .subtract(saturnForce.scalarMultiply(timeStep / saturnMass));
        final Vector2D saturnPreviousPosition = saturnActualPosition
                .subtract(saturnPreviousVelocity.scalarMultiply(timeStep))
                .add(saturnForce.scalarMultiply((timeStep * timeStep) / (2 * saturnMass)));

        // Initialize The ship's velocity and position at -deltaT
        final Vector2D shipActualPosition = ship.getPosition();
        final double shipMass = ship.getMass();
        final Vector2D shipForce = getAppliedForce(ship);
        final Vector2D shipPreviousVelocity = ship.getVelocity()
                .subtract(shipForce.scalarMultiply(timeStep / shipMass));
        final Vector2D shipPreviousPosition = shipActualPosition
                .subtract(shipPreviousVelocity.scalarMultiply(timeStep))
                .add(shipForce.scalarMultiply((timeStep * timeStep) / (2 * shipMass)));

        // Calculate accelerations
        final Vector2D sunPreviousAcceleration = getAcceleration(sunPreviousPosition,
                earthPreviousPosition, jupiterPreviousPosition, saturnPreviousPosition, shipActualPosition,
                sunMass, earthMass, jupiterMass, saturnMass, shipMass);

        final Vector2D earthPreviousAcceleration = getAcceleration(earthPreviousPosition,
                sunPreviousPosition, jupiterPreviousPosition, saturnPreviousPosition, shipActualPosition,
                earthMass, sunMass, jupiterMass, saturnMass, shipMass);

        final Vector2D jupiterPreviousAcceleration = getAcceleration(jupiterPreviousPosition,
                sunPreviousPosition, earthPreviousPosition, saturnPreviousPosition, shipActualPosition,
                jupiterMass, sunMass, earthMass, saturnMass, shipMass);

        final Vector2D saturnPreviousAcceleration = getAcceleration(saturnPreviousPosition,
                sunPreviousPosition, earthPreviousPosition, jupiterPreviousPosition, shipActualPosition,
                saturnMass, sunMass, earthMass, jupiterMass, shipMass);

        final Vector2D shipPreviousAcceleration = getAcceleration(shipPreviousPosition,
                sunPreviousPosition, earthPreviousPosition, jupiterPreviousPosition, saturnPreviousPosition,
                shipMass, sunMass, earthMass, jupiterMass, saturnMass);

        // Save in map
        this.previousAccelerations.put(sun, sunPreviousAcceleration);
        this.previousAccelerations.put(earth, earthPreviousAcceleration);
        this.previousAccelerations.put(jupiter, jupiterPreviousAcceleration);
        this.previousAccelerations.put(saturn, saturnPreviousAcceleration);
        this.previousAccelerations.put(ship, shipPreviousAcceleration);
    }


    /**
     * @return The Sun.
     */
    private Body getSun() {
        return sun;
    }

    /**
     * @return The Earth.
     */
    private Body getEarth() {
        return earth;
    }

    /**
     * @return Jupiter.
     */
    private Body getJupiter() {
        return jupiter;
    }

    /**
     * @return Saturn.
     */
    private Body getSaturn() {
        return saturn;
    }

    /**
     * @return The ship.
     */
    private Body getShip() {
        return ship;
    }

    /**
     * @return The amount of time the system has existed.
     */
    public double getActualTime() {
        return actualTime;
    }

    /**
     * Indicates whether the ship reached Saturn's orbit.
     *
     * @return {@code true} if the ship already wen't through Saturn's orbit, or {@code false} otherwise.
     */
    public boolean finishMovement() {
        return actualTime >= saturnianYears * Constants.SATURNIAN_YEAR_SECONDS;
    }

    @Override
    public void update() {
        // First calculate positions
        final Vector2D sunNextPosition = getNextPosition(sun);
        final Vector2D earthNextPosition = getNextPosition(earth);
        final Vector2D jupiterNextPosition = getNextPosition(jupiter);
        final Vector2D saturnNextPosition = getNextPosition(saturn);
        final Vector2D shipNextPosition = getNextPosition(ship);
        // Then calculate accelerations
        final double sunMass = sun.getMass();
        final double earthMass = earth.getMass();
        final double jupiterMass = jupiter.getMass();
        final double saturnMass = saturn.getMass();
        final double shipMass = ship.getMass();
        final Vector2D sunNextAcceleration = getAcceleration(sunNextPosition,
                earthNextPosition, jupiterNextPosition, saturnNextPosition, shipNextPosition,
                sunMass, earthMass, jupiterMass, saturnMass, shipMass);
        final Vector2D earthNextAcceleration = getAcceleration(earthNextPosition,
                sunNextPosition, jupiterNextPosition, saturnNextPosition, shipNextPosition,
                earthMass, sunMass, jupiterMass, saturnMass, shipMass);
        final Vector2D jupiterNextAcceleration = getAcceleration(jupiterNextPosition,
                sunNextPosition, earthNextPosition, saturnNextPosition, shipNextPosition,
                jupiterMass, sunMass, earthMass, saturnMass, shipMass);
        final Vector2D saturnNextAcceleration = getAcceleration(saturnNextPosition,
                sunNextPosition, earthNextPosition, jupiterNextPosition, shipNextPosition,
                saturnMass, sunMass, earthMass, jupiterMass, shipMass);
        final Vector2D shipNextAcceleration = getAcceleration(shipNextPosition,
                sunNextPosition, earthNextPosition, jupiterNextPosition, saturnNextPosition,
                shipMass, sunMass, earthMass, jupiterMass, saturnMass);
        // Finally, calculate velocities
        final Vector2D sunNextVelocity = getNextVelocity(sun, sunNextAcceleration);
        final Vector2D earthNextVelocity = getNextVelocity(earth, earthNextAcceleration);
        final Vector2D jupiterNextVelocity = getNextVelocity(jupiter, jupiterNextAcceleration);
        final Vector2D saturnNextVelocity = getNextVelocity(saturn, saturnNextAcceleration);
        final Vector2D shipNextVelocity = getNextVelocity(ship, shipNextAcceleration);

        // Store accelerations
        previousAccelerations.put(sun, sun.getAcceleration());
        previousAccelerations.put(earth, earth.getAcceleration());
        previousAccelerations.put(jupiter, jupiter.getAcceleration());
        previousAccelerations.put(saturn, saturn.getAcceleration());
        previousAccelerations.put(ship, ship.getAcceleration());

        // Set new values
        sun.setPosition(sunNextPosition);
        sun.setVelocity(sunNextVelocity);
        sun.setAcceleration(sunNextAcceleration);
        earth.setPosition(earthNextPosition);
        earth.setVelocity(earthNextVelocity);
        earth.setAcceleration(earthNextAcceleration);
        jupiter.setPosition(jupiterNextPosition);
        jupiter.setVelocity(jupiterNextVelocity);
        jupiter.setAcceleration(jupiterNextAcceleration);
        saturn.setPosition(saturnNextPosition);
        saturn.setVelocity(saturnNextVelocity);
        saturn.setAcceleration(saturnNextAcceleration);
        ship.setPosition(shipNextPosition);
        ship.setVelocity(shipNextVelocity);
        ship.setAcceleration(shipNextAcceleration);

        // Update time
        actualTime += timeStep;
    }


    @Override
    public void restart() {
        sun.setPosition(sunInitialPosition);
        sun.setVelocity(sunInitialVelocity);
        sun.setAcceleration(sunInitialAcceleration);
        earth.setPosition(earthInitialPosition);
        earth.setVelocity(earthInitialVelocity);
        earth.setAcceleration(earthInitialAcceleration);
        jupiter.setPosition(jupiterInitialPosition);
        jupiter.setVelocity(jupiterInitialVelocity);
        jupiter.setAcceleration(jupiterInitialAcceleration);
        saturn.setPosition(saturnInitialPosition);
        saturn.setVelocity(saturnInitialVelocity);
        saturn.setAcceleration(saturnInitialAcceleration);
        ship.setPosition(shipInitialPosition);
        ship.setVelocity(shipInitialVelocity);
        ship.setAcceleration(shipInitialAcceleration);
        actualTime = 0;
    }

    @Override
    public SolarSystemState outputState() {
        return new SolarSystemState(this);
    }

    /**
     * Calculates the next position to the given {@code body}.
     *
     * @param body The {@link Body} suffering the position change.
     * @return The next position.
     */
    private Vector2D getNextPosition(Body body) {
        return body.getPosition()
                .add(body.getVelocity().scalarMultiply(timeStep))
                .add(body.getAcceleration().scalarMultiply((2d / 3d) * timeStep * timeStep))
                .subtract(previousAccelerations.get(body).scalarMultiply((1d / 6d) * timeStep * timeStep));
    }

    /**
     * Calculates the next velocity to the given {@code body}.
     *
     * @param body             The {@link Body} suffering the position change.
     * @param nextAcceleration The next acceleration (i.e used for Beeman integration).
     * @return The next position.
     */
    private Vector2D getNextVelocity(Body body, Vector2D nextAcceleration) {

        return body.getVelocity()
                .add(nextAcceleration.scalarMultiply((1d / 3d) * timeStep))
                .add(body.getAcceleration().scalarMultiply((5d / 6d) * timeStep))
                .subtract(previousAccelerations.get(body).scalarMultiply((1d / 6d) * timeStep));
    }

    /**
     * Returns the total applied force to the given {@link Body}.
     *
     * @param body The {@link Body} suffering the returned force.
     * @return A {@link Vector2D} representing the force.
     */
    private Vector2D getAppliedForce(Body body) {
        return influencers.get(body)
                .stream()
                .map(body::appliedGravitationalForce)
                .reduce(Vector2D.ZERO, Vector2D::add);
    }

    /**
     * Calculates the accelerations according to the given positions and masses.
     *
     * @param affectedPosition    The position of the affected body.
     * @param influencer1Position The position of the first influencer.
     * @param influencer2Position The position of the second influencer.
     * @param influencer3Position The position of the third influencer.
     * @param influencer4Position The position of the fourth influencer.
     * @param affectedMass        The mass of the affected body.
     * @param influencer1Mass     The mass of the first influencer.
     * @param influencer2Mass     The mass of the second influencer.
     * @param influencer3Mass     The mass of the third influencer.
     * @param influencer4Mass     The mass of the fourth influencer.
     * @return The calculated acceleration.
     */
    private static Vector2D getAcceleration(Vector2D affectedPosition,
                                            Vector2D influencer1Position, Vector2D influencer2Position,
                                            Vector2D influencer3Position, Vector2D influencer4Position,
                                            double affectedMass,
                                            double influencer1Mass, double influencer2Mass,
                                            double influencer3Mass, double influencer4Mass) {
        final Vector2D influencer1Force = Utils
                .gravitationalForce(affectedMass, influencer1Mass, affectedPosition, influencer1Position);
        final Vector2D influencer2Force = Utils
                .gravitationalForce(affectedMass, influencer2Mass, affectedPosition, influencer2Position);
        final Vector2D influencer3Force = Utils
                .gravitationalForce(affectedMass, influencer3Mass, affectedPosition, influencer3Position);
        final Vector2D influencer4Force = Utils
                .gravitationalForce(affectedMass, influencer4Mass, affectedPosition, influencer4Position);
        return Stream.of(influencer1Force, influencer2Force, influencer3Force, influencer4Force)
                .reduce(Vector2D.ZERO, Vector2D::add).scalarMultiply(1 / affectedMass);
    }

    /**
     * Calculates the ship's initial position, which depends on the Earth's.
     *
     * @param sunInitialPosition   The Sun's initial position.
     * @param earthInitialPosition The Earth's initial position.
     * @return The ship's initial position.
     * @implNote This method takes the Sun to Earth vector, and calculates the norm of it.
     * Then it adds to the norm the ship's altitude (given by {@link #SHIP_ALTITUDE}), to calculate a stretching factor.
     * This stretching factor is multiplied to a unit factor generated from the Earth's position).
     */
    private static Vector2D calculateShipInitialPosition(Vector2D sunInitialPosition, Vector2D earthInitialPosition) {
        final double factor = earthInitialPosition.distance(sunInitialPosition) + SHIP_ALTITUDE + EARTH_RADIUS;
        return earthInitialPosition.subtract(sunInitialPosition)
                .normalize()
                .scalarMultiply(factor)
                .add(sunInitialPosition);
    }

    /**
     * Calculates the ship's initial velocity, which depends on the Earth's initial position.
     *
     * @param earthInitialVelocity The Earth's initial velocity.
     * @return The ship's initial velocity.
     * @implNote This method takes the Earth's velocity, calculates the module, adds the {@link #SHIP_INITIAL_SPEED},
     * and creates a new vector (with the Earth's initial velocity direction, but with the new speed).
     */
    private static Vector2D calculateShipInitialVelocity(Vector2D earthInitialVelocity) {
        final double factor = earthInitialVelocity.getNorm() + SHIP_INITIAL_SPEED;
        return earthInitialVelocity.normalize()
                .scalarMultiply(factor);
    }

    /**
     * A {@link SolarSystem} {@link State}.
     */
    public static final class SolarSystemState implements State {

        /**
         * The Sun's state.
         */
        private final Body.BodyState sun;
        /**
         * The Earth's state.
         */
        private final Body.BodyState earth;
        /**
         * Jupiter's state.
         */
        private final Body.BodyState jupiter;
        /**
         * Saturn's state.
         */
        private final Body.BodyState saturn;
        /**
         * The Ship's state.
         */
        private final Body.BodyState ship;

        /**
         * Constructor.
         *
         * @param solarSystem The {@link SolarSystem} whose state will be saved.
         */
        /* package */ SolarSystemState(SolarSystem solarSystem) {
            this.sun = solarSystem.getSun().outputState();
            this.earth = solarSystem.getEarth().outputState();
            this.jupiter = solarSystem.getJupiter().outputState();
            this.saturn = solarSystem.getSaturn().outputState();
            this.ship = solarSystem.getShip().outputState();
        }

        /**
         * @return The Sun's state.
         */
        public Body.BodyState getSun() {
            return sun;
        }

        /**
         * @return The Earth's state.
         */
        public Body.BodyState getEarth() {
            return earth;
        }

        /**
         * @return Jupiter's state.
         */
        public Body.BodyState getJupiter() {
            return jupiter;
        }

        /**
         * @return Saturn's state.
         */
        public Body.BodyState getSaturn() {
            return saturn;
        }

        /**
         * @return The Ship's state.
         */
        public Body.BodyState getShip() {
            return ship;
        }
    }
}
