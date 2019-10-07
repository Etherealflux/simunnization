package haus.steven.actors;

import haus.steven.world.State;

/**
 * An Entity represents a node in the graph.
 * It could be a single person, or a collection of people, or so forth.
 */
public interface Entity {
    public int count(State state);

    public void infect(int count);

    public void recover(int count);

    public String getLabel();
}
