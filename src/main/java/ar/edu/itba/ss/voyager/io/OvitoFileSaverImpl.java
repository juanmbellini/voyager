package ar.edu.itba.ss.voyager.io;

import ar.edu.itba.ss.g7.engine.io.OvitoFileSaver;
import ar.edu.itba.ss.voyager.models.Body;
import ar.edu.itba.ss.voyager.models.SolarSystem;

import java.io.IOException;
import java.io.Writer;

/**
 * {@link OvitoFileSaver} for the {@link SolarSystem}.
 */
public class OvitoFileSaverImpl extends OvitoFileSaver<SolarSystem.SolarSystemState> {

    /**
     * Constructor.
     *
     * @param filePath Path to the file to be saved.
     */
    public OvitoFileSaverImpl(String filePath) {
        super(filePath);
    }

    @Override
    public void saveState(Writer writer, SolarSystem.SolarSystemState solarSystemState, int frame) throws IOException {
        final StringBuilder data = new StringBuilder()
                // First, headers
                .append(5)
                .append("\n")
                .append(frame)
                .append("\n");
        // Save the Sun's state
        saveBody(data, solarSystemState.getSun(), 255, 255, 0); // The Sun is yellow
        // Save the Earth's state
        saveBody(data, solarSystemState.getEarth(), 0, 128, 0); // The Earth is green
        // Save Jupiter's state
        saveBody(data, solarSystemState.getJupiter(), 210, 105, 30); // Jupiter is orange
        // Save Saturn's state
        saveBody(data, solarSystemState.getSaturn(), 218, 165, 32); // Saturn is brown
        // Save the ship's state
        saveBody(data, solarSystemState.getSaturn(), 192, 192, 192); // The ship is gray
        // Append data into the Writer
        writer.append(data);
    }

    /**
     * Saves a {@link ar.edu.itba.ss.voyager.models.Body.BodyState} into the {@code data} {@link StringBuilder}.
     *
     * @param data The {@link StringBuilder} that is collecting data.
     * @param body The {@link ar.edu.itba.ss.voyager.models.Body.BodyState} with the data.
     * @param r    Red component for color.
     * @param g    Green component for color.
     * @param b    Blue component for color.
     */
    private static void saveBody(StringBuilder data, Body.BodyState body, int r, int g, int b) {
        data.append("")
                .append(body.getPosition().getX())
                .append(" ")
                .append(body.getPosition().getY())
                .append(" ")
                .append(body.getVelocity().getX())
                .append(" ")
                .append(body.getVelocity().getY())
                .append(" ")
                .append(r)
                .append(" ")
                .append(g)
                .append(" ")
                .append(b)
                .append("\n");
    }
}
