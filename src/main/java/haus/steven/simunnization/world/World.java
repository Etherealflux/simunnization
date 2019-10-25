package haus.steven.simunnization.world;

import haus.steven.simunnization.actors.Entity;
import haus.steven.simunnization.spreading.Spreadable;
import haus.steven.simunnization.spreading.State;
import haus.steven.simunnization.world.connections.Connection;
import haus.steven.simunnization.world.statistics.WorldLogger;
import haus.steven.simunnization.world.transformers.Transformer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jgrapht.Graph;

import java.util.ArrayList;
import java.util.Collection;

/**
 * The World contains all of the members of the simulation.
 */
public class World {
    public final Graph<Entity, Connection> network;
    public final Collection<Spreadable> spreadables;
    private final Logger logger = LogManager.getLogger();
    private final ArrayList<Transformer> setupTransformers = new ArrayList<>();
    private final ArrayList<Transformer> tickTransformers = new ArrayList<>();
    private final ArrayList<WorldLogger> loggers = new ArrayList<>();
    private int tickCount = 0;

    public World(Graph<Entity, Connection> network, Collection<Spreadable> spreadables) {
        this.network = network;
        this.spreadables = spreadables;
    }


    /**
     * Prepares the world to run. Runs any setup transformers that have been added.
     */
    public void start() {
        logger.info("Starting world");

        for (Transformer transformer :
                this.setupTransformers) {
            logger.info("Executing startup transformer: " + transformer);
            transformer.transform(this);
        }

        logger.info("Setup transformation complete");

        for (Transformer transformer : tickTransformers) {
            logger.info("Using tick transformer: " + transformer);
        }
    }

    /**
     * Advance the state of the world.
     * <p>
     * This picks a random vertex and calls the Spreadable
     */
    public void tick() {
        logger.trace("Ticking");

        for (Spreadable spreadable : spreadables) {
            spreadable.doTick(this);
        }

        for (Transformer transformer :
                tickTransformers) {
            transformer.transform(this);
        }
        for (WorldLogger logger :
                loggers) {
            logger.tick(this, tickCount);
        }
        this.tickCount += 1;
    }

    public int count(State state) {
        return this.network.vertexSet().stream().mapToInt(o -> o.count(state)).sum();
    }

    public void report() {
        for (WorldLogger logger :
                loggers) {
            logger.show();
        }
    }

    /**
     * Describes the overall state of the world
     * Includes things like the number of infected people
     *
     * @return A summary of the world's state
     */
    public String summarize() {
        StringBuilder result = new StringBuilder();

        result.append("World summary: \n\n");
        result.append("Total population: ");
        result.append(this.network.vertexSet().size());
        result.append("\n");
        for (State state :
                State.values()) {
            result.append(state);
            result.append(": ");
            result.append(this.count(state));
            result.append("\n");
        }
        result.append("\n");


        return result.toString();
    }

    public void RegisterSetupTransformer(Transformer transformer) {
        this.setupTransformers.add(transformer);
    }

    public void RegisterTickTransformer(Transformer transformer) {
        this.tickTransformers.add(transformer);
    }

    public void RegisterLogger(WorldLogger logger) {
        this.loggers.add(logger);
    }

    public int getTick() {
        return tickCount;
    }
}