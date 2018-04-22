package ar.edu.itba.ss.voyager;

import ar.edu.itba.ss.g7.engine.simulation.SimulationEngine;
import ar.edu.itba.ss.voyager.models.SolarSystem;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main class.
 */
@SpringBootApplication
public class Voyager implements CommandLineRunner, InitializingBean {

    /**
     * The {@link Logger} object.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(Voyager.class);

    /**
     * The {@link SolarSystem} to be simulated.
     */
    private final SimulationEngine<SolarSystem.SolarSystemState, SolarSystem> engine;

    @Autowired
    public Voyager() {
        // TODO: initialize with correct values
        final SolarSystem solarSystem = new SolarSystem(0,
                Vector2D.ZERO, null, null,
                null, null, null,
                null, null, null,
                null, null, null,
                null, null, null
        );
        this.engine = new SimulationEngine<>(solarSystem);
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        this.engine.initialize();
    }

    @Override
    public void run(String... args) throws Exception {
        LOGGER.info("Hello, Voyager!");
        // First, simulate
        simulate();
        // Then, save
        save();
        LOGGER.info("Bye-bye!");
        System.exit(0);
    }

    /**
     * Performs the simulation phase of the program.
     */
    private void simulate() {
        LOGGER.info("Starting simulation...");
        this.engine.simulate(SolarSystem::reachedSaturnOrbit);
        LOGGER.info("Finished simulation");
    }

    /**
     * Performs the save phase of the program.
     */
    private void save() {
        LOGGER.info("Saving outputs...");
        // TODO: save here
        LOGGER.info("Finished saving output in all formats.");
    }

    /**
     * Entry point.
     *
     * @param args Program Arguments.
     */
    public static void main(String[] args) {
        SpringApplication.run(Voyager.class, args);
    }
}
