package haus.steven.world.setup;

import haus.steven.actors.Entity;
import haus.steven.world.Connection;
import haus.steven.world.World;
import org.jgrapht.Graph;

public class RandomInfector implements EntityTransformer {
    private final double probability;

    public RandomInfector(double probability) {
        this.probability = probability;
        System.out.println(this);
    }

    @Override
    public void transform(World world) {
        Graph<Entity, Connection> network = world.network;
        for (Entity entity :
                network.vertexSet()) {
            if (Math.random() < this.probability) {
                entity.infect(1);
            }
        }
    }

    @Override
    public String toString() {
        return String.format("Random Infector - %.2f%% infection probability", this.probability * 100);
    }
}
