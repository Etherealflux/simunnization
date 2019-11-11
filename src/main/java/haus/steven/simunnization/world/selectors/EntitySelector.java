package haus.steven.simunnization.world.selectors;

import haus.steven.simunnization.actors.Entity;
import haus.steven.simunnization.world.World;

import java.util.Collection;

/**
 * Selects zero or more entities from the world.
 */
public interface EntitySelector {
    Collection<Entity> select(World world);
}
