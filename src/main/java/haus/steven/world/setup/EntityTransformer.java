package haus.steven.world.setup;

import haus.steven.actors.Entity;
import haus.steven.world.Connection;
import haus.steven.world.World;
import org.jgrapht.Graph;

/**
 * Transforms entities in some manner.
 * This could be used to infect people at the start,
 * or to immunize people at regular intervals.
 */
public interface EntityTransformer {
    /**
     * Does something to the world.
     *
     * @param world The network to work with
     */
    public void transform(World world);
}
