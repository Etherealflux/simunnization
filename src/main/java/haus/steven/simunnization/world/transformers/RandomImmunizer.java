package haus.steven.simunnization.world.transformers;

import haus.steven.simunnization.actors.Entity;
import haus.steven.simunnization.world.World;
import haus.steven.simunnization.world.connections.Connection;
import org.jgrapht.Graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Randomly chooses and immunizes entities
 */
public class RandomImmunizer implements Transformer {
    private final float efficacy;
    private final int count;

    private final Random random;

    public RandomImmunizer(float efficacy, int count) {
        this.notify();
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
