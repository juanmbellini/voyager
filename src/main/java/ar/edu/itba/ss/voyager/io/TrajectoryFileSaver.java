package ar.edu.itba.ss.voyager.io;

import ar.edu.itba.ss.g7.engine.io.TextFileSaver;
import ar.edu.itba.ss.voyager.models.Body;
import ar.edu.itba.ss.voyager.models.SolarSystem;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import java.io.IOException;
import java.io.Writer;
import java.util.Queue;
import java.util.stream.Collectors;

/**
 * A {@link TextFileSaver} that will output the trajectory of the ship.
 */
public class TrajectoryFileSaver extends TextFileSaver<SolarSystem.SolarSystemState> {

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
    public TrajectoryFileSaver(String filePath, double timeStep, double totalTime) {
        super(filePath);
        this.timeStep = timeStep;
        this.totalTime = totalTime;
    }

    @Override
    public void doSave(Writer writer, Queue<SolarSystem.SolarSystemState> queue) throws IOException {
        // Save ship's 'x' component of the position into the 'x' variable.
        final String x = "x = [" + queue.stream()
                .map(SolarSystem.SolarSystemState::getShip)
                .map(Body.BodyState::getPosition)
                .map(Vector2D::getX)
                .map(Object::toString)
                .collect(Collectors.joining(", ")) + "];";
        // Save shi`'s 'y' component of the position into the 'y' variable.
        final String y = "y = [" + queue.stream()
                .map(SolarSystem.SolarSystemState::getShip)
                .map(Body.BodyState::getPosition)
                .map(Vector2D::getY)
                .map(Object::toString)
                .collect(Collectors.joining(", ")) + "];";
        // Append results into the Writer
        writer.append(x)
                .append("\n")
                .append(y)
                .append("\n")
                .append("dt = ").append(String.valueOf(timeStep)).append(";")
                .append("\n")
                .append("totalTime = ").append(String.valueOf(totalTime)).append(";")
                .append("\n");

    }
}
