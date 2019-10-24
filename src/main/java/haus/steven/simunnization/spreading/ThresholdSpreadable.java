package haus.steven.simunnization.spreading;

import haus.steven.simunnization.actors.Entity;
import haus.steven.simunnization.world.connections.Connection;
import org.jgrapht.Graph;
import org.jgrapht.Graphs;

import java.util.List;

public class ThresholdSpreadable implements Spreadable {
    private final double infectThreshold;
    private final double recoverChance;

    public ThresholdSpreadable(double infectThreshold, double recoverChance) {
        this.infectThreshold = infectThreshold;
        this.recoverChance = recoverChance;
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

        int totalPop = 0;
        int infectedPop = 0;

        for (Entity target :
                neighbors) {
            Connection conn = network.getEdge(host, target);
            totalPop += target.population() * conn.access();
            infectedPop += target.count(State.INFECTED) * conn.access();
        }

        if (((double) infectedPop) / totalPop > infectThreshold) {
            host.infect(host.count(State.SUSCEPTIBLE));
        }

        if (Math.random() < recoverChance) {
            host.recover(host.population());
        }
    }

    public String getName() {
        return "Threshold";
    }
}
