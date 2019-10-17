package haus.steven.world.connections.suppliers;

import haus.steven.world.connections.Connection;
import haus.steven.world.connections.StaticConnection;

import java.util.function.Supplier;

/**
 * Generates completely unremarkable static connections
 */
public class StaticConnectionSupplier implements Supplier<Connection> {
    @Override
    public StaticConnection get() {
        return new StaticConnection();
    }
}
