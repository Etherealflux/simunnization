package haus.steven.simunnization.actors;

import haus.steven.simunnization.spreading.Spreadable;
import haus.steven.simunnization.spreading.State;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

/**
 * An Individual represents a single actor - a person, a computer, etc.
 */
public class Individual implements Entity {
    private static final Logger logger = LogManager.getLogger();
    private final String name;
    private final Set<String> labels;

    private Map<Spreadable, State> states;

    private float susceptibility = 1f;

    public Individual(Collection<Spreadable> spreadables, String name) {
        this.name = name;
        this.states = new HashMap<>();

        for (Spreadable spreadable: spreadables) {
            states.put(spreadable, State.SUSCEPTIBLE);
        }
        labels = new HashSet<>();
    }

    @Override
    public int count(State state) {
        // if we have more than INT_MAX spreadables, I quit
        return (int) states.values().stream().filter(o -> o == state).count();
    }

    @Override
    public int count(State state, Spreadable spreadable) {
        return state == states.get(spreadable) ? 1 : 0;
    }

    @Override
    public int population() {
        return 1;
    }

    @Override
    public void changeState(Spreadable spreadable, State from, State to, int count) {
        if (!states.containsKey(spreadable)) {
            return;
        }

        State current = states.get(spreadable);

        if (current == from) {
            states.put(spreadable, to);
        }
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Collection<String> getLabels() {
        return Collections.unmodifiableSet(labels);
    }

    @Override
    public Collection<Spreadable> getSpreadables() {
        return states.keySet();
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        if (states.size() == 0) {
            return "Untouched";
        } else {
            for (Spreadable spreadable : states.keySet()) {
                result.append(spreadable + ": " + states.get(spreadable) + " - ");
            }

            return result.toString();
        }
    }

    @Override
    public void immunize(float efficacy) {
        susceptibility *= (1 - efficacy);
    }

    @Override
    public float susceptibility() {
        return susceptibility;
    }
}
