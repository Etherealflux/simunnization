import haus.steven.actors.Entity;
import haus.steven.actors.Individual;
import haus.steven.actors.generators.NumberedIndividualSupplier;
import haus.steven.world.*;
import haus.steven.world.generators.StaticConnectionGenerator;
import haus.steven.world.setup.RandomInfector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jgrapht.Graph;
import org.jgrapht.generate.GraphGenerator;
import org.jgrapht.generate.ScaleFreeGraphGenerator;
import org.jgrapht.graph.DefaultUndirectedGraph;
import haus.steven.spreading.Spreadable;
import haus.steven.spreading.disease.Cold;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class Simunnization {
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        Supplier<Entity> vertexSupplier = new NumberedIndividualSupplier();
        Supplier<Connection> edgeSupplier = new StaticConnectionGenerator();

        Graph<Entity, Connection> network = new DefaultUndirectedGraph<Entity, Connection>(vertexSupplier, edgeSupplier, false);

        ScaleFreeGraphGenerator<Entity, Connection> generator = new ScaleFreeGraphGenerator<Entity, Connection>(1000);

        generator.generateGraph(network);

        Spreadable spreadable = new Cold();

        World world = new World(network, spreadable);
        world.AddSetupTransformer(new RandomInfector(0.1));
        logger.info("Created world");

        world.start();

        logger.info("Set up world");
        for (int i = 0; i < 1000; i++) {
            world.tick();
        }

        System.out.println(world.summarize());

        logger.info("Shutting down...");
    }
}
