package haus.steven.spreading;

import haus.steven.actors.Entity;
import haus.steven.world.Connection;
import org.jgrapht.Graph;


/**
 * A Spreadable has rules for how readily it spreads, as well as how quickly it dissipates.
 * Spreadables are shown both their hosts and their neighbors.
 */

public interface Spreadable {
    /**
     * Invoked by the world every tick.
     *
     */
    void doTick(Graph<Entity, Connection> network);


    /**
     * @return The spreadable's name
     */
    String getName();
}
