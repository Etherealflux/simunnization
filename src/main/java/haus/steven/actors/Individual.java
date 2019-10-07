package haus.steven.actors;

import haus.steven.world.State;

/**
 * An Individual represents a single actor - a person, a computer, etc.
 */
public class Individual implements Entity {
    private final String name;
    private State state;

    public Individual(String name) {
        this.name = name;
    }

    @Override
    public int count(State state) {
        return this.state == state ? 1 : 0;
    }

    @Override
    public void infect(int count) {
        if (count > 0) {
            System.out.println(this.name + " got sick");
            this.state = State.INFECTED;
        }
    }

    @Override
    public void recover(int count) {
        if (count > 0) {
            System.out.println(this.name + " got better");
            this.state = State.RECOVERED;
        }
    }

    @Override
    public String getLabel() {
        return this.name;
    }
}
