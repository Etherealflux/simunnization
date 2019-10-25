package haus.steven.simunnization.actors.suppliers;

import haus.steven.simunnization.actors.Entity;
import haus.steven.simunnization.actors.Individual;
import haus.steven.simunnization.spreading.Spreadable;

import java.util.Collection;
import java.util.function.Supplier;

/**
 * Generates unique Individuals with an incrementing name
 */
public class NumberedIndividualSupplier implements Supplier<Entity> {
    private int count = 0;
    private final Collection<Spreadable> spreadables;

    public NumberedIndividualSupplier(Collection<Spreadable> spreadables) {
        this.spreadables = spreadables;
    }

    @Override
    public Individual get() {
        Individual individual = new Individual(spreadables, "Individual " + count);
        count += 1;
        return individual;
    }
}
