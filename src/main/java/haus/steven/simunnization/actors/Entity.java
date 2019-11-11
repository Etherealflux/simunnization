package haus.steven.simunnization.actors;

import haus.steven.simunnization.spreading.Spreadable;
import haus.steven.simunnization.spreading.State;

import java.util.Collection;

/**
 * An Entity represents a node in the graph.
 * It could be a single person, or a collection of people, or so forth.
 */
public interface Entity extends Immunizable {
    /**
     * Counts the total number of people in a certain state. An entity may be in multiple states if there are
     * multiple spreadables in play.
     *
     * @param state Which state to count
     * @return The number of entities in that state
     */
    int count(State state);

    /**
     * Counts the total number of people in a certain state for a given Spreadable. An entity will only ever be
     * in one state per spreadable.
     *
     * @param state      Which state to count
     * @param spreadable Which spreadable to consider
     * @return The number of entities in that state for the given spreadable
     */
    int count(State state, Spreadable spreadable);

    /**
     * @return The total number of individuals that this Entity represents
     */
    int population();

    /**
     * Try to change entities from one state to another. If there aren't enough entities with the old state,
     * then only that many will be changed to the new state.
     *
     * @param spreadable
     * @param from
     * @param to
     * @param count
     */
    void changeState(Spreadable spreadable, State from, State to, int count);

    /**
     * Try to infect one or more entities with a specific Spreadable. This will not infect more people than are
     * susceptible, and may also be affected by rules for coexistence of spreadables.
     *
     * @param spreadable
     * @param count
     */
    default void infect(Spreadable spreadable, int count) {
        changeState(spreadable, State.SUSCEPTIBLE, State.INFECTED, count);
    }


    /**
     * Try to recover one or more entities with a specific Spreadable. This will not recover more entities than are
     * infected.
     *
     * @param spreadable
     * @param count
     */
    default void recover(Spreadable spreadable, int count) {
        changeState(spreadable, State.INFECTED, State.RECOVERED, count);
    }

    /**
     * Try to return one or more entities from the infected state to the susceptible state for a given Spreadable.
     * This will not affect more entities than are infected.
     *
     * @param spreadable
     * @param count
     */
    default void suscept(Spreadable spreadable, int count) {
        changeState(spreadable, State.INFECTED, State.SUSCEPTIBLE, count);
    }

    String getName();

    Collection<String> getLabels();

    Collection<Spreadable> getSpreadables();
}
