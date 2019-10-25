package haus.steven.simunnization.actors.providers;

import haus.steven.simunnization.actors.Entity;
import haus.steven.simunnization.actors.Individual;
import haus.steven.simunnization.spreading.Spreadable;
import org.jgrapht.io.VertexProvider;

import java.util.Collection;
import java.util.Map;

public class IndividualProvider implements VertexProvider<Entity> {

    private final Collection<Spreadable> spreadables;

    public IndividualProvider(Collection<Spreadable> spreadables) {
        this.spreadables = spreadables;
    }

    @Override
    public Entity buildVertex(String id, Map attributes) {
        return new Individual(spreadables, id);
    }
}
