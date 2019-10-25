package haus.steven.simunnization.spreading.selectors;

import haus.steven.simunnization.actors.Entity;
import haus.steven.simunnization.world.World;

import java.util.*;

/**
 * Selects entities in an order that is randomized after each full traversal of the set of entities
 */
public class CyclicEntitySelector implements EntitySelector {

    private List<Entity> entities;
    private int size;
    private final int count;

    private int index = 0;


    public CyclicEntitySelector(int count) {
        this.count = count;
    }

    private void initWorld(World world) {
        this.entities = new ArrayList<>(world.network.vertexSet());
        this.size = entities.size();
        Collections.shuffle(entities);
    }

    @Override
    public Collection<Entity> select(World world) {
        if (entities == null) {
            initWorld(world);
        }

        Set<Entity> result = new HashSet<>();

        for (int i = 0; i < count; i++) {
            result.add(entities.get(0));
            index += 1;


            if (index == size) {
                Collections.shuffle(entities);
                index = 0;
            }
        }

        return result;
    }
}
