package haus.steven.world;

/**
 * A ToggleConnection can be switched on and off
 */
public class ToggleConnection implements Connection {
    public boolean open = true;

    @Override
    public double access() {
        return open ? 1 : 0;
    }

    public void open() {
        open = true;
    }

    public void close() {
        open = false;
    }
}
