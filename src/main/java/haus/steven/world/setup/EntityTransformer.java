package haus.steven.world.setup;

import haus.steven.actors.Entity;
import haus.steven.world.Connection;
import org.jgrapht.Graph;

/**
 * Transforms entities in some manner.
 * This could be used to infect people at the start,
 * or to immunize people at regular intervals.
 */
public interface EntityTransformer {
    /**
     * Does something to the entities in the network.
     * @param network The network to work with
     */
    public void transform(Graph<Entity, Connection> network);
}
