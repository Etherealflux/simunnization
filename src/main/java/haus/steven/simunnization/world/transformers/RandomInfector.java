package haus.steven.simunnization.world.transformers;

import haus.steven.simunnization.actors.Entity;
import haus.steven.simunnization.spreading.Spreadable;
import haus.steven.simunnization.world.World;
import haus.steven.simunnization.world.connections.Connection;
import org.jgrapht.Graph;

public class RandomInfector implements Transformer {
    private final Spreadable spreadable;
    private final double probability;

    public RandomInfector(Spreadable spreadable, double probability) {
        this.spreadable = spreadable;
        this.probability = probability;
        System.out.println(this);
    }

    @Override
    public void transform(World world) {
        Graph<Entity, Connection> network = world.network;
        for (Entity entity :
                network.vertexSet()) {
            if (Math.random() < this.probability) {
                entity.infect(spreadable, 1);
            }
        }
    }

    @Override
    public String toString() {
        return String.format("Random Infector - %.2f%% infection probability", this.probability * 100);
    }
}
