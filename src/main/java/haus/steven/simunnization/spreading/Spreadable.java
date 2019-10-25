package haus.steven.simunnization.spreading;

import haus.steven.simunnization.world.World;


/**
 * A Spreadable has rules for how readily it spreads, as well as how quickly it dissipates.
 * Spreadables are shown both their hosts and their neighbors.
 */

public interface Spreadable {
    /**
     * Invoked by the world every tick.
     *
     * @param world
     */
    void doTick(World world);
}
