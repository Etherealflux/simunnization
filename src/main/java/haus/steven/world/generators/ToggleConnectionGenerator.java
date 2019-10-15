package haus.steven.world.generators;

import haus.steven.world.Connection;
import haus.steven.world.ToggleConnection;

import java.util.function.Supplier;

public class ToggleConnectionGenerator implements Supplier<Connection> {
    @Override
    public Connection get() {
        return new ToggleConnection();
    }
}