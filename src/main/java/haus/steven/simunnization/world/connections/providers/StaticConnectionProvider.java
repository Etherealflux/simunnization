package haus.steven.simunnization.world.connections.providers;

import haus.steven.simunnization.actors.Entity;
import haus.steven.simunnization.world.connections.Connection;
import haus.steven.simunnization.world.connections.StaticConnection;
import org.jgrapht.io.Attribute;
import org.jgrapht.io.EdgeProvider;

import java.util.Map;

public class StaticConnectionProvider implements EdgeProvider<Entity, Connection> {
    @Override
    public Connection buildEdge(Entity from, Entity to, String label, Map<String, Attribute> attributes) {
        return new StaticConnection();
    }
}
