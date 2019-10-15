package haus.steven.world;

/**
 * A StaticConnection never changes and has no interesting properties
 */
public class StaticConnection implements Connection {
    @Override
    public double access() {
        return 1;
    }
}
