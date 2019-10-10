package haus.steven.world.setup;


import haus.steven.actors.Entity;
import haus.steven.world.Connection;
import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.util.VertexDegreeComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Infects nodes in order of decreasing degree
 */
public class HighDegreeInfector implements EntityTransformer {

    private final int count;

    public HighDegreeInfector(int count) {
        this.count = count;
    }

    @Override
    public void transform(Graph<Entity, Connection> network) {
        Comparator<Entity> comparator = new VertexDegreeComparator<Entity, Connection>(network, VertexDegreeComparator.Order.DESCENDING);
        List<Entity> entities = new ArrayList<Entity>(network.vertexSet());
        Collections.sort(entities, comparator);

        entities.stream().limit(count).forEach(o -> o.infect(1));
    }
}
