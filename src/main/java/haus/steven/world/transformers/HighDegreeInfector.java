package haus.steven.world.transformers;


import haus.steven.actors.Entity;
import haus.steven.world.Connection;
import haus.steven.world.World;
import org.jgrapht.Graph;
import org.jgrapht.alg.util.VertexDegreeComparator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Infects nodes in order of decreasing degree
 */
public class HighDegreeInfector implements EntityTransformer {

    private final int count;

    public HighDegreeInfector(int count) {
        this.count = count;
    }

    @Override
    public void transform(World world) {
        Graph<Entity, Connection> network = world.network;
        Comparator<Entity> comparator = new VertexDegreeComparator<>(network, VertexDegreeComparator.Order.DESCENDING);
        List<Entity> entities = new ArrayList<>(network.vertexSet());
        entities.sort(comparator);

        entities.stream().limit(count).forEach(o -> o.infect(1));
    }

    @Override
    public String toString() {
        return String.format("High degree infector - %d targets", count);
    }
}
