import haus.steven.actors.Entity;
import haus.steven.actors.providers.IndividualProvider;
import haus.steven.actors.suppliers.NumberedIndividualSupplier;
import haus.steven.spreading.ThresholdSpreadable;
import haus.steven.world.connections.providers.ToggleConnectionProvider;
import haus.steven.spreading.State;
import haus.steven.world.*;
import haus.steven.world.connections.Connection;
import haus.steven.world.connections.suppliers.ToggleConnectionSupplier;
import haus.steven.world.transformers.*;
import haus.steven.world.statistics.ImmunizationLogger;
import haus.steven.world.statistics.InfectionLogger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jgrapht.Graph;
import org.jgrapht.generate.RandomRegularGraphGenerator;
import org.jgrapht.generate.ScaleFreeGraphGenerator;
import org.jgrapht.graph.DefaultUndirectedGraph;
import haus.steven.spreading.Spreadable;
import haus.steven.spreading.disease.Cold;
import org.jgrapht.io.CSVImporter;
import org.jgrapht.io.GraphImporter;
import org.jgrapht.io.ImportException;

import java.io.File;
import java.util.function.Supplier;

public class Simunnization {
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        Supplier<Entity> vertexSupplier = new NumberedIndividualSupplier();
        Supplier<Connection> edgeSupplier = new ToggleConnectionSupplier();

        Graph<Entity, Connection> network = new DefaultUndirectedGraph<>(vertexSupplier, edgeSupplier, false);

        RandomRegularGraphGenerator<Entity, Connection> generator = new RandomRegularGraphGenerator<>(1000, 3);

        generator.generateGraph(network);

        /*GraphImporter<Entity, Connection> importer = new CSVImporter<>(new IndividualProvider(), new ToggleConnectionProvider());

        try {
            importer.importGraph(network, new File(System.getenv("GRAPH_LOC")));
        } catch (ImportException e) {
            logger.error("Failed to import the graph!");
            System.exit(-1);
        }*/

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
