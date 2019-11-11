package haus.steven.simunnization.world.selectors;

import haus.steven.simunnization.actors.Entity;
import haus.steven.simunnization.world.World;

import java.util.*;

/**
 * Picks a random set of entities from the network. Each entity can only be picked once.
 */
public class RandomEntitySelector implements EntitySelector {

    private List<Entity> entities;
    private int count;

    public RandomEntitySelector(int count) {
        this.count = count;
    }

    private void initWorld(World world) {
        this.entities = new ArrayList<>(world.network.vertexSet());
    }

    @Override
    public Collection<Entity> select(World world) {
        if (entities == null) {
            initWorld(world);
        }
        Set<Entity> result = new HashSet<>();
        while (result.size() < count) {
            int index = (int) (Math.random() * entities.size());
            result.add(entities.get(index));
        }

        return result;
    }
}
