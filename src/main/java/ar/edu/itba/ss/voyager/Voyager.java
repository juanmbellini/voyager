package ar.edu.itba.ss.voyager;

import ar.edu.itba.ss.g7.engine.io.DataSaver;
import ar.edu.itba.ss.g7.engine.simulation.SimulationEngine;
import ar.edu.itba.ss.voyager.io.OvitoFileSaverImpl;
import ar.edu.itba.ss.voyager.io.ProgramArguments;
import ar.edu.itba.ss.voyager.models.SolarSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.LinkedList;

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

    private final DataSaver<SolarSystem.SolarSystemState> ovitoFileSaver;

    @Autowired
    public Voyager(ProgramArguments arguments) {
        final SolarSystem solarSystem = new SolarSystem(arguments.getTimeStep(), arguments.getAmountOfYears(),
                arguments.getSunPosition(), arguments.getSunVelocity(),
                arguments.getEarthPosition(), arguments.getEarthVelocity(),
                arguments.getJupiterPosition(), arguments.getJupiterVelocity(),
                arguments.getSaturnPosition(), arguments.getSaturnVelocity());
        this.engine = new SimulationEngine<>(solarSystem);
        this.ovitoFileSaver = new OvitoFileSaverImpl(arguments.getOvitoFilePath());
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
        this.engine.simulate(SolarSystem::finishMovement);
        LOGGER.info("Finished simulation");
    }

    /**
     * Performs the save phase of the program.
     */
    private void save() {
        LOGGER.info("Saving outputs...");
        this.ovitoFileSaver.save(new LinkedList<>(this.engine.getResults()));
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
