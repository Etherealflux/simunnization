package spreading;

import haus.steven.world.Entity;
import haus.steven.world.State;

import java.util.Collection;

/**
 * A SIRSpreadable is any Spreadable that follows the standard SIR model
 */
public abstract class SIRSpreadable implements Spreadable {
    private double infectionRate;
    private double recoveryRate;

    public SIRSpreadable(double infectionRate, double recoveryRate) {
        this.infectionRate = infectionRate;
        this.recoveryRate = recoveryRate;
    }

    /**
     * Rounds a positive number to an int, rounding up with probability (1 - (value % 1))
     * @param value
     * @return
     */
    private int roundRandom(double value) {
        double probability = value - Math.floor(value);
        if (Math.random() < probability) {
            return (int) Math.floor(value);
        } else {
            return (int) Math.ceil(value);
        }
    }

    @Override
    public void doTick(Entity host, Collection<Entity> neighbors) {
        System.out.println(host.getLabel());
        doTickFor(host, host);
        for (Entity target :
                neighbors) {
            doTickFor(host, target);
        }
    }

    private void doTickFor(Entity source, Entity target) {
        System.out.println("Infected: " + source.count(State.INFECTED));
        int infected = roundRandom(source.count(State.INFECTED) * this.infectionRate);
        int recovered = roundRandom(target.count(State.INFECTED) * this.recoveryRate);

        System.out.println(infected);
        target.infect(infected);
        target.recover(recovered);
    }
}
