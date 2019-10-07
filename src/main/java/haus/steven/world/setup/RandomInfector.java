package haus.steven.world.setup;

import haus.steven.actors.Entity;
import haus.steven.world.Connection;
import org.jgrapht.Graph;

public class RandomInfector implements EntityTransformer {
    private final double probability;

    public RandomInfector(double probability) {
        this.probability = probability;
    }

    @Override
    public void transform(Graph<Entity, Connection> network) {
        for (Entity entity :
                network.vertexSet()) {
            if (Math.random() < this.probability) {
                entity.infect(1);
            }
        }
    }
}
