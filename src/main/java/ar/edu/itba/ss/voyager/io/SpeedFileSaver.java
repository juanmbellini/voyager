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
 * A {@link TextFileSaver} that will output the ship's speed (i.e velocity module).
 */
public class SpeedFileSaver extends TextFileSaver<SolarSystem.SolarSystemState> {

    /**
     * Constructor.
     *
     * @param filePath Path to the file to be saved.
     */
    public SpeedFileSaver(String filePath) {
        super(filePath);
    }

    @Override
    public void doSave(Writer writer, Queue<SolarSystem.SolarSystemState> queue) throws IOException {
        final String speed = "shipSpeed = [" + queue.stream()
                .map(SolarSystem.SolarSystemState::getShip)
                .map(Body.BodyState::getVelocity)
                .map(Vector2D::getNorm)
                .map(Object::toString)
                .collect(Collectors.joining(", ")) + "];";
        // Append results into the Writer
        writer.append(speed)
                .append("\n");
    }
}
