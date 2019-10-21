package haus.steven.world.connections;

/**
 * A StaticConnection never changes and has no interesting properties
 */
public class StaticConnection implements Connection {
    @Override
    public double access() {
        return 1;
    }

    public String toString() {
        return "";
    }
}
