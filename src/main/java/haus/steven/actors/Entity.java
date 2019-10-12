package haus.steven.actors;

import haus.steven.spreading.State;

import java.util.Collection;

/**
 * An Entity represents a node in the graph.
 * It could be a single person, or a collection of people, or so forth.
 */
public interface Entity {
    int count(State state);

    void changeState(State state, int count);

    default void infect(int count) {
        changeState(State.INFECTED, count);
    }

    default void recover(int count) {
        changeState(State.RECOVERED, count);
    }

    String getName();

    Collection<String> getLabels();
}
