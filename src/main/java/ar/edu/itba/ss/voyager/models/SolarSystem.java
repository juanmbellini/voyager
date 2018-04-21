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

    /**
     * {@link Map} holding, for each, body, the {@link List} of {@link Body} that gravitationally influence them.
     */
    private final Map<Body, List<Body>> influencers;

    /**
     * Constructor.
     *
     * @param sunInitialPosition         The Sun's initial position.
     * @param sunInitialVelocity         The Sun's initial velocity.
     * @param sunInitialAcceleration     The Sun's initial acceleration.
     * @param earthInitialPosition       The Earth's initial position.
     * @param earthInitialVelocity       The Earth's initial velocity.
     * @param earthInitialAcceleration   The Earth's initial acceleration.
     * @param jupiterInitialPosition     Jupiter's initial position.
     * @param jupiterInitialVelocity     Jupiter's initial velocity.
     * @param jupiterInitialAcceleration Jupiter's initial acceleration.
     * @param saturnInitialPosition      Saturn's initial position.
     * @param saturnInitialVelocity      Saturn's initial velocity.
     * @param saturnInitialAcceleration  Saturn's initial acceleration.
     * @param shipInitialPosition        The ship's initial position.
     * @param shipInitialVelocity        The ship's initial velocity.
     * @param shipInitialAcceleration    The ship's initial acceleration.
     */
    public SolarSystem(final Vector2D sunInitialPosition, final Vector2D sunInitialVelocity,
                       final Vector2D sunInitialAcceleration,
                       final Vector2D earthInitialPosition, final Vector2D earthInitialVelocity,
                       final Vector2D earthInitialAcceleration,
                       final Vector2D jupiterInitialPosition, final Vector2D jupiterInitialVelocity,
                       final Vector2D jupiterInitialAcceleration,
                       final Vector2D saturnInitialPosition, final Vector2D saturnInitialVelocity,
                       final Vector2D saturnInitialAcceleration,
                       final Vector2D shipInitialPosition, final Vector2D shipInitialVelocity,
                       final Vector2D shipInitialAcceleration) {
        this.sun = BodyType.SUN.provide(sunInitialPosition, sunInitialVelocity, sunInitialAcceleration);
        this.earth = BodyType.EARTH.provide(earthInitialPosition, earthInitialVelocity, earthInitialAcceleration);
        this.jupiter = BodyType.JUPITER.provide(jupiterInitialPosition, jupiterInitialVelocity, jupiterInitialAcceleration);
        this.saturn = BodyType.SATURN.provide(saturnInitialPosition, saturnInitialVelocity, saturnInitialAcceleration);
        this.ship = BodyType.SHIP.provide(shipInitialPosition, shipInitialVelocity, shipInitialAcceleration);
        this.influencers = new HashMap<>();
        this.influencers.put(sun, Stream.of(earth, jupiter, saturn, ship).collect(Collectors.toList()));
        this.influencers.put(earth, Stream.of(sun, jupiter, saturn, ship).collect(Collectors.toList()));
        this.influencers.put(jupiter, Stream.of(sun, earth, saturn, ship).collect(Collectors.toList()));
        this.influencers.put(saturn, Stream.of(sun, earth, jupiter, ship).collect(Collectors.toList()));
        this.influencers.put(ship, Stream.of(sun, earth, jupiter, saturn).collect(Collectors.toList()));
    }

    /**
     * @return The Sun.
     */
    public Body getSun() {
        return sun;
    }

    /**
     * @return The Earth.
     */
    public Body getEarth() {
        return earth;
    }

    /**
     * @return Jupiter.
     */
    public Body getJupiter() {
        return jupiter;
    }

    /**
     * @return Saturn.
     */
    public Body getSaturn() {
        return saturn;
    }

    /**
     * @return The ship.
     */
    public Body getShip() {
        return ship;
    }

    @Override
    public void update() {
        // TODO: integrate equations

        // TODO: set new values to bodies
    }

    @Override
    public void restart() {
        // TODO: restart
    }

    @Override
    public SolarSystemState outputState() {
        return new SolarSystemState(this);
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
        public SolarSystemState(SolarSystem solarSystem) {
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
