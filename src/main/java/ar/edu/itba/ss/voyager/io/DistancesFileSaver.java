package ar.edu.itba.ss.voyager.io;

import ar.edu.itba.ss.g7.engine.io.TextFileSaver;
import ar.edu.itba.ss.voyager.models.SolarSystem;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import java.io.IOException;
import java.io.Writer;
import java.util.Queue;
import java.util.stream.Collectors;

/**
 * A {@link TextFileSaver} that will output the distances from the ship to each planet
 */
public class DistancesFileSaver extends TextFileSaver<SolarSystem.SolarSystemState> {

    /**
     * The time step.
     */
    private final double timeStep;

    /**
     * The total time.
     */
    private final double totalTime;

    /**
     * Constructor.
     *
     * @param filePath  Path to the file to be saved.
     * @param timeStep  The time step.
     * @param totalTime The total time.
     */
    public DistancesFileSaver(String filePath, double timeStep, double totalTime) {
        super(filePath);
        this.timeStep = timeStep;
        this.totalTime = totalTime;
    }

    @Override
    public void doSave(Writer writer, Queue<SolarSystem.SolarSystemState> queue) throws IOException {
        final String distanceToSun = "distanceToSun = [" + queue.stream()
                .map(state -> Vector2D.distance(state.getShip().getPosition(), state.getSun().getPosition()))
                .map(Object::toString)
                .collect(Collectors.joining(", ")) + "];";
        final String distanceToEarth = "distanceToEarth = [" + queue.stream()
                .map(state -> Vector2D.distance(state.getShip().getPosition(), state.getEarth().getPosition()))
                .map(Object::toString)
                .collect(Collectors.joining(", ")) + "];";
        final String distanceToJupiter = "distanceToJupiter = [" + queue.stream()
                .map(state -> Vector2D.distance(state.getShip().getPosition(), state.getJupiter().getPosition()))
                .map(Object::toString)
                .collect(Collectors.joining(", ")) + "];";
        final String distanceToSaturn = "distanceToSaturn = [" + queue.stream()
                .map(state -> Vector2D.distance(state.getShip().getPosition(), state.getSaturn().getPosition()))
                .map(Object::toString)
                .collect(Collectors.joining(", ")) + "];";
        // Append results into the Writer
        writer.append(distanceToSun)
                .append("\n")
                .append(distanceToEarth)
                .append("\n")
                .append(distanceToJupiter)
                .append("\n")
                .append(distanceToSaturn)
                .append("\n")
                .append("dt = ").append(String.valueOf(timeStep)).append(";")
                .append("\n")
                .append("totalTime = ").append(String.valueOf(totalTime)).append(";")
                .append("\n");
    }
}
