package haus.steven.world;

import haus.steven.actors.Entity;
import haus.steven.spreading.State;
import haus.steven.world.setup.EntityTransformer;
import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import haus.steven.spreading.Spreadable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The World contains all of the members of the simulation.
 */
public class World {
    private final Graph<Entity, Connection> network;
    private final Spreadable spreadable;

    private ArrayList<EntityTransformer> setupTransformers = new ArrayList<>();
    private ArrayList<EntityTransformer> tickTransformers = new ArrayList<>();

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
    }

    public int count(State state) {
        return this.network.vertexSet().stream().mapToInt(o -> o.count(state)).sum();
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
    public void RegisterTickTransformer(EntityTransformer transformer) { this.tickTransformers.add(transformer); }
}