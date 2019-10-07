package spreading;

import haus.steven.world.Entity;

import java.util.Collection;

/**
 * A Spreadable has rules for how readily it spreads, as well as how quickly it dissipates.
 * Spreadables are shown both their hosts and their neighbors.
 */

public interface Spreadable {
    /**
     * Invoked by the world every tick. This should handle both spreading and recovery (if applicable)
     *
     * @param host      The Entity that is infected by this Spreadable
     * @param neighbors All Entities connected to the host
     */
    public void doTick(Entity host, Collection<Entity> neighbors);


    /**
     * @return The entity's name
     */
    public String getName();
}
