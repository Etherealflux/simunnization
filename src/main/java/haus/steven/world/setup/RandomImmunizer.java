package haus.steven.world.setup;

import haus.steven.actors.Entity;
import haus.steven.world.Connection;
import haus.steven.world.World;
import org.jgrapht.Graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Randomly chooses and immunizes entities
 */
public class RandomImmunizer implements EntityTransformer {
    private final float efficacy;
    private final int count;

    private Random random;

    public RandomImmunizer(float efficacy, int count)
    {
        this.efficacy = efficacy;
        this.count = count;

        this.random = new Random();
    }

    @Override
    public void transform(World world) {
        Graph<Entity, Connection> network = world.network;

        List<Entity> entities = new ArrayList<>(network.vertexSet());

        for (int i = 0; i < count; i++) {
            int choice = random.nextInt(entities.size());
            entities.get(choice).immunize(efficacy);
        }
    }

    @Override
    public String toString() {
        return String.format("Random immunizer - %.2f efficacy, %d targets", efficacy, count);
    }
}
