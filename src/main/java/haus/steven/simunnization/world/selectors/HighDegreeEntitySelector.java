package haus.steven.simunnization.world.selectors;

import haus.steven.simunnization.actors.Entity;
import haus.steven.simunnization.world.World;

import java.lang.reflect.Array;
import java.util.*;

/**
 * Selects entities cyclically, in order of decreasing k
 */
public class HighDegreeEntitySelector implements EntitySelector {
    private List<Entity> entities;

    private final int count;
    private int currentIndex = 0;

    public HighDegreeEntitySelector(int count) {
        this.count = count;
    }

    private void initDegrees(World world) {
        entities = new ArrayList<>(world.network.vertexSet());

        entities.sort(new Comparator<Entity>() {
            @Override
            public int compare(Entity o1, Entity o2) {
                return world.network.degreeOf(o2) - world.network.degreeOf(o1);
            }
        });

        for (Entity entity: entities) {
            System.out.println(world.network.degreeOf(entity));
        }
    }

    @Override
    public Collection<Entity> select(World world) {
        if (entities == null) {
            initDegrees(world);
        }

        ArrayList<Entity> results = new ArrayList<>();
        for (int i=0; i<count; i++) {
            results.add(entities.get(currentIndex));
            currentIndex++;
            if (currentIndex >= entities.size()) {
                currentIndex = 0;
            }
        }

        return results;
    }
}
