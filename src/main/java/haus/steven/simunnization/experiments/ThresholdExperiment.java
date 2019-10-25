package haus.steven.simunnization.experiments;

import haus.steven.simunnization.actors.Entity;
import haus.steven.simunnization.actors.suppliers.NumberedIndividualSupplier;
import haus.steven.simunnization.spreading.Spreadable;
import haus.steven.simunnization.spreading.ThresholdSpreadable;
import haus.steven.simunnization.spreading.selectors.RandomEntitySelector;
import haus.steven.simunnization.world.World;
import haus.steven.simunnization.world.connections.Connection;
import haus.steven.simunnization.world.connections.suppliers.ToggleConnectionSupplier;
import haus.steven.simunnization.world.statistics.InfectionLogger;
import haus.steven.simunnization.world.transformers.RandomInfector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jgrapht.Graph;
import org.jgrapht.generate.ScaleFreeGraphGenerator;
import org.jgrapht.graph.DefaultUndirectedGraph;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Supplier;

public class ThresholdExperiment implements Experiment {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public void run(Map<String, String> args) {
        Spreadable spreadable = new ThresholdSpreadable(0.5, 0.05, new RandomEntitySelector(1));

        Supplier<Entity> vertexSupplier = new NumberedIndividualSupplier(Arrays.asList(spreadable));
        Supplier<Connection> edgeSupplier = new ToggleConnectionSupplier();

        Graph<Entity, Connection> network = new DefaultUndirectedGraph<>(vertexSupplier, edgeSupplier, false);

        ScaleFreeGraphGenerator<Entity, Connection> generator = new ScaleFreeGraphGenerator<>(1000);

        generator.generateGraph(network);


        World world = new World(network, spreadable);

        world.RegisterSetupTransformer(new RandomInfector(spreadable, 0.5));

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
