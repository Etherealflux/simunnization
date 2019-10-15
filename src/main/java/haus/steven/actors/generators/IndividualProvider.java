package haus.steven.actors.generators;

import haus.steven.actors.Entity;
import haus.steven.actors.Individual;
import org.jgrapht.io.VertexProvider;

import java.util.Map;

public class IndividualProvider implements VertexProvider<Entity> {
    @Override
    public Entity buildVertex(String id, Map attributes) {
        return new Individual(id);
    }
}
