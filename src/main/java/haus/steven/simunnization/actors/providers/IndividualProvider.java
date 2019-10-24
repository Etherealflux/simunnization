package haus.steven.simunnization.actors.providers;

import haus.steven.simunnization.actors.Entity;
import haus.steven.simunnization.actors.Individual;
import org.jgrapht.io.VertexProvider;

import java.util.Map;

public class IndividualProvider implements VertexProvider<Entity> {
    @Override
    public Entity buildVertex(String id, Map attributes) {
        return new Individual(id);
    }
}
