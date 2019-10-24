package haus.steven.simunnization.world.connections.suppliers;

import haus.steven.simunnization.world.connections.Connection;
import haus.steven.simunnization.world.connections.ToggleConnection;

import java.util.function.Supplier;

public class ToggleConnectionSupplier implements Supplier<Connection> {
    @Override
    public Connection get() {
        return new ToggleConnection();
    }
}