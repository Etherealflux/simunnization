package haus.steven.simunnization.world.selectors;

import haus.steven.simunnization.actors.Entity;
import haus.steven.simunnization.world.World;

import java.util.*;

/**
 * Selects entities at random, with a weight of (1+k)
 */
public class HighDegreeEntitySelector implements EntitySelector {
    private final Random random = new Random();

    private NavigableMap<Integer, Entity> degrees;
    private int totalWeights = 0;

    private void initDegrees(World world) {
        degrees = new TreeMap<Integer, Entity>();
        int degreeTotal = 0;

        for (Entity entity : world.network.vertexSet()) {
            degreeTotal += 1 + world.network.degreeOf(entity);
            degrees.put(degreeTotal, entity);
        }

        totalWeights = degreeTotal;
    }

    @Override
    public Collection<Entity> select(World world) {
        if (degrees == null) {
            initDegrees(world);
        }

        int index = random.nextInt(totalWeights);

        Entity chosen = degrees.higherEntry(index).getValue();

        return new ArrayList<>(Arrays.asList(chosen));
    }
}
