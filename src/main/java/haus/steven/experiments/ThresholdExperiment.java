package haus.steven.experiments;

import haus.steven.actors.Entity;
import haus.steven.actors.suppliers.NumberedIndividualSupplier;
import haus.steven.spreading.Spreadable;
import haus.steven.spreading.ThresholdSpreadable;
import haus.steven.world.World;
import haus.steven.world.connections.Connection;
import haus.steven.world.connections.suppliers.ToggleConnectionSupplier;
import haus.steven.world.statistics.InfectionLogger;
import haus.steven.world.transformers.RandomInfector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jgrapht.Graph;
import org.jgrapht.generate.RandomRegularGraphGenerator;
import org.jgrapht.graph.DefaultUndirectedGraph;

import java.util.Map;
import java.util.function.Supplier;

public class ThresholdExperiment implements Experiment {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public void run(Map<String, String> args) {
        Supplier<Entity> vertexSupplier = new NumberedIndividualSupplier();
        Supplier<Connection> edgeSupplier = new ToggleConnectionSupplier();

        Graph<Entity, Connection> network = new DefaultUndirectedGraph<>(vertexSupplier, edgeSupplier, false);

        RandomRegularGraphGenerator<Entity, Connection> generator = new RandomRegularGraphGenerator<>(1000, 3);

        generator.generateGraph(network);

        Spreadable spreadable = new ThresholdSpreadable(0.5, 0.05);

        World world = new World(network, spreadable);

        world.RegisterSetupTransformer(new RandomInfector(0.5));

        world.RegisterLogger(new InfectionLogger(100));

        logger.info("Created world");

        world.start();

        logger.info("Set up world");
        for (int i = 0; i < 150000; i++) {
            world.tick();
        }

        System.out.println(world.summarize());

        world.report();

        logger.info("Shutting down...");
    }
}
