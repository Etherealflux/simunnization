package haus.steven.world.generators;

import haus.steven.world.Connection;
import haus.steven.world.StaticConnection;

import java.util.function.Supplier;

/**
 * Generates completely unremarkable static connections
 */
public class StaticConnectionGenerator implements Supplier<Connection> {
    @Override
    public StaticConnection get() {
        return new StaticConnection();
    }
}
