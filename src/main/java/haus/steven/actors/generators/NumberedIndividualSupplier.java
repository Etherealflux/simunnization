package haus.steven.actors.generators;

import haus.steven.actors.Entity;
import haus.steven.actors.Individual;

import java.util.function.Supplier;

/**
 * Generates unique Individuals with an incrementing name
 */
public class NumberedIndividualSupplier implements Supplier<Entity> {
    private int count = 0;

    @Override
    public Individual get() {
        Individual individual = new Individual("Individual " + count);
        count += 1;
        return individual;
    }
}
