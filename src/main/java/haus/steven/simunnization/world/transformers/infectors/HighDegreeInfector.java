package haus.steven.simunnization.world.transformers.infectors;


import haus.steven.simunnization.actors.Entity;
import haus.steven.simunnization.spreading.Spreadable;
import haus.steven.simunnization.world.World;
import haus.steven.simunnization.world.connections.Connection;
import haus.steven.simunnization.world.transformers.Transformer;
import org.jgrapht.Graph;
import org.jgrapht.alg.util.VertexDegreeComparator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Infects nodes in order of decreasing degree
 */
public class HighDegreeInfector implements Transformer {
    private final Spreadable spreadable;
    private final int count;

    public HighDegreeInfector(Spreadable spreadable, int count) {

        this.spreadable = spreadable;
        this.count = count;
    }

    @Override
    public void transform(World world) {
        Graph<Entity, Connection> network = world.network;
        Comparator<Entity> comparator = new VertexDegreeComparator<>(network, VertexDegreeComparator.Order.DESCENDING);
        List<Entity> entities = new ArrayList<>(network.vertexSet());
        entities.sort(comparator);

        entities.stream().limit(count).forEach(o -> o.infect(spreadable, 1));
    }

    @Override
    public String toString() {
        return String.format("High degree infector - %d targets", count);
    }
}
