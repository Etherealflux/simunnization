import haus.steven.actors.Entity;
import haus.steven.actors.generators.NumberedIndividualSupplier;
import haus.steven.world.*;
import haus.steven.world.generators.StaticConnectionGenerator;
import haus.steven.world.setup.HighDegreeInfector;
import haus.steven.world.setup.RandomInfector;
import haus.steven.world.statistics.InfectionLogger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jgrapht.Graph;
import org.jgrapht.generate.ScaleFreeGraphGenerator;
import org.jgrapht.graph.DefaultUndirectedGraph;
import haus.steven.spreading.Spreadable;
import haus.steven.spreading.disease.Cold;

import java.util.function.Supplier;

public class Simunnization {
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        Supplier<Entity> vertexSupplier = new NumberedIndividualSupplier();
        Supplier<Connection> edgeSupplier = new StaticConnectionGenerator();

        Graph<Entity, Connection> network = new DefaultUndirectedGraph<>(vertexSupplier, edgeSupplier, false);

        ScaleFreeGraphGenerator<Entity, Connection> generator = new ScaleFreeGraphGenerator<>(10000);

        generator.generateGraph(network);

        Spreadable spreadable = new Cold();

        World world = new World(network, spreadable);
        world.RegisterSetupTransformer(new RandomInfector(0.01));

        world.RegisterLogger(new InfectionLogger(1000));
        logger.info("Created world");

        world.start();

        logger.info("Set up world");
        for (int i = 0; i < 100000; i++) {
            world.tick();
        }

        System.out.println(world.summarize());

        world.report();

        logger.info("Shutting down...");
    }
}
