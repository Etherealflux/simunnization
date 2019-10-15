package haus.steven.actors.generators;

import haus.steven.actors.Entity;
import haus.steven.world.Connection;
import haus.steven.world.ToggleConnection;
import org.jgrapht.io.Attribute;
import org.jgrapht.io.EdgeProvider;

import java.util.Map;

public class ToggleConnectionProvider implements EdgeProvider<Entity, Connection> {
    @Override
    public Connection buildEdge(Entity from, Entity to, String label, Map<String, Attribute> attributes) {
        return new ToggleConnection();
    }
}