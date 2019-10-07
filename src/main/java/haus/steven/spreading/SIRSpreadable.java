package haus.steven.spreading;

import haus.steven.actors.Entity;
import haus.steven.world.Connection;
import org.jgrapht.Graph;
import org.jgrapht.Graphs;

import java.util.Collection;
import java.util.List;

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
    public void doTick(Graph<Entity, Connection> network) {
        Entity[] entities = network.vertexSet().toArray(new Entity[0]);
        Entity host = entities[(int) (Math.random() * entities.length)];
        List<Entity> neighbors = Graphs.neighborListOf(network, host);

        doTickFor(host, host);
        for (Entity target :
                neighbors) {
            doTickFor(host, target);
        }
    }

    private void doTickFor(Entity source, Entity target) {
        int infected = roundRandom(source.count(State.INFECTED) * this.infectionRate);
        int recovered = roundRandom(target.count(State.INFECTED) * this.recoveryRate);

        target.infect(infected);
        target.recover(recovered);
    }
}
