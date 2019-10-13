package haus.steven.world.setup;

import haus.steven.actors.Entity;
import haus.steven.world.Connection;
import org.jgrapht.Graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Randomly chooses and immunizes entities
 */
public class RandomImmunizer implements TickTransformer {
    private final int period;
    private final float efficacy;
    private final int count;

    private Random random;

    public RandomImmunizer(int period, float efficacy, int count)
    {
        this.period = period;
        this.efficacy = efficacy;
        this.count = count;

        this.random = new Random();
    }

    @Override
    public void transform(Graph<Entity, Connection> network, int tick) {
        if (tick % period == 0)
            transform(network);
    }

    @Override
    public void transform(Graph<Entity, Connection> network) {
        List<Entity> entities = new ArrayList<>(network.vertexSet());

        for (int i = 0; i < count; i++) {
            int choice = random.nextInt(entities.size());
            entities.get(choice).immunize(efficacy);
        }
    }
}
