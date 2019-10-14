package haus.steven.world.transformers;

import haus.steven.world.World;

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
