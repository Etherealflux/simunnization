package haus.steven.world.connections.providers;

import haus.steven.actors.Entity;
import haus.steven.world.connections.Connection;
import haus.steven.world.connections.ToggleConnection;
import org.jgrapht.io.Attribute;
import org.jgrapht.io.EdgeProvider;

import java.util.Map;

public class ToggleConnectionProvider implements EdgeProvider<Entity, Connection> {
    @Override
    public Connection buildEdge(Entity from, Entity to, String label, Map<String, Attribute> attributes) {
        return new ToggleConnection();
    }
}