package haus.steven.simunnization.spreading;

import haus.steven.simunnization.actors.Entity;
import haus.steven.simunnization.world.connections.Connection;
import org.jgrapht.Graph;
import org.jgrapht.Graphs;

import java.util.List;

/**
 * A SIRSpreadable is any Spreadable that follows the standard SIR model
 */
public abstract class SIRSpreadable implements Spreadable {
    private final double infectionRate;
    private final double recoveryRate;

    public SIRSpreadable(double infectionRate, double recoveryRate) {
        this.infectionRate = infectionRate;
        this.recoveryRate = recoveryRate;
    }

    /**
     * Rounds a positive number to an int, rounding up with probability (1 - (value % 1))
     *
     * @param value A number to round
     * @return A rounded value
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

        int hostInfected = infectCount(host, host);
        int hostRecovered = recoverCount(host);

        for (Entity target :
                neighbors) {
            Connection conn = network.getEdge(host, target);
            target.infect(infectCount(host, target, conn));
        }

        host.infect(hostInfected);
        host.recover(hostRecovered);
    }
    private int infectCount(Entity source, Entity target) {
        int sourceInfected = source.count(State.INFECTED);
        int targetSusceptible = target.count(State.SUSCEPTIBLE);

        return roundRandom(infectionRateFor(source, target) * sourceInfected * targetSusceptible);
    }


    private int infectCount(Entity source, Entity target, Connection conn) {
        int sourceInfected = source.count(State.INFECTED);
        int targetSusceptible = target.count(State.SUSCEPTIBLE);

        return roundRandom(infectionRateFor(source, target, conn) * sourceInfected * targetSusceptible);
    }

    private int recoverCount(Entity source) {
        int sourceInfected = source.count(State.INFECTED);

        return roundRandom(this.recoveryRate * sourceInfected);
    }

    private double infectionRateFor(Entity source, Entity target) {
        return infectionRate * target.susceptibility();
    }

    private double infectionRateFor(Entity source, Entity target, Connection conn) {
        return infectionRateFor(source, target) * conn.access();
    }
}
