package haus.steven.actors;

import haus.steven.spreading.State;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * An Individual represents a single actor - a person, a computer, etc.
 */
public class Individual implements Entity {
    private static final Logger logger = LogManager.getLogger();
    private final String name;
    private final Set<String> labels;
    private State state;

    public Individual(String name) {
        this.name = name;
        this.state = State.SUSCEPTIBLE;
        labels = new HashSet<>();
    }

    @Override
    public int count(State state) {
        return this.state == state ? 1 : 0;
    }

    @Override
    public void infect(int count) {
        if (this.state == State.SUSCEPTIBLE) {
            if (count > 0) {
                this.state = State.INFECTED;
            }
        }

    }

    @Override
    public void recover(int count) {
        if (this.state == State.INFECTED) {
            if (count > 0) {
                this.state = State.RECOVERED;
            }
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
    public String toString() {
        return this.name + " (" + this.state + ")";
    }
}
