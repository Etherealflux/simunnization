package haus.steven.world;

import haus.steven.actors.Entity;
import haus.steven.spreading.State;
import haus.steven.world.setup.EntityTransformer;
import haus.steven.world.statistics.WorldLogger;
import org.jgrapht.Graph;
import haus.steven.spreading.Spreadable;

import java.util.ArrayList;

/**
 * The World contains all of the members of the simulation.
 */
public class World {
    public final Graph<Entity, Connection> network;
    public final Spreadable spreadable;
    private int tickCount = 0;

    private ArrayList<EntityTransformer> setupTransformers = new ArrayList<>();
    private ArrayList<EntityTransformer> tickTransformers = new ArrayList<>();

    private ArrayList<WorldLogger> loggers = new ArrayList<>();

    public World(Graph<Entity, Connection> network, Spreadable spreadable) {
        this.network = network;
        this.spreadable = spreadable;
    }


    /**
     * Prepares the world to run. Runs any setup transformers that have been added.
     */
    public void start() {
        for (EntityTransformer transformer :
                this.setupTransformers) {
            transformer.transform(this.network);
        }
    }

    /**
     * Advance the state of the world.
     * <p>
     * This picks a random vertex and calls the Spreadable
     */
    public void tick() {
        this.spreadable.doTick(network);
        for (EntityTransformer transformer :
                tickTransformers) {
            transformer.transform(network);
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

    public void RegisterSetupTransformer(EntityTransformer transformer) {
        this.setupTransformers.add(transformer);
    }

    public void RegisterTickTransformer(EntityTransformer transformer) {
        this.tickTransformers.add(transformer);
    }

    public void RegisterLogger(WorldLogger logger) {
        this.loggers.add(logger);
    }
}