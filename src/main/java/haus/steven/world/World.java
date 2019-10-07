package haus.steven.world;
import haus.steven.actors.Entity;
import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import haus.steven.spreading.Spreadable;

import java.util.List;

/**
 * The World contains all of the members of the simulation.
 */
public class World {
    private Graph<Entity, Connection> network;
    private Spreadable spreadable;

    public World(Graph<Entity, Connection> network, Spreadable spreadable) {
        this.network = network;
        this.spreadable = spreadable;
    }
    /**
     * Advance the state of the world.
     *
     * This picks a random vertex and calls the Spreadable
     */
    public void tick() {
        // This is going to be slow. Should probably just save a list.
        Entity[] entities = this.network.vertexSet().toArray(new Entity[0]);
        Entity picked = entities[(int) (Math.random() * entities.length)];

        List<Entity> neighbors = Graphs.neighborListOf(this.network, picked);
        this.spreadable.doTick(picked, neighbors);
    }

}
