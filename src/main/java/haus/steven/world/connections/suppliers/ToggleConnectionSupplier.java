package haus.steven.world.connections.suppliers;

import haus.steven.world.connections.Connection;
import haus.steven.world.connections.ToggleConnection;

import java.util.function.Supplier;

public class ToggleConnectionSupplier implements Supplier<Connection> {
    @Override
    public Connection get() {
        return new ToggleConnection();
    }
}