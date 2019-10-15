import haus.steven.actors.Entity;
import haus.steven.actors.generators.IndividualProvider;
import haus.steven.actors.generators.NumberedIndividualSupplier;
import haus.steven.actors.generators.StaticConnectionProvider;
import haus.steven.world.*;
import haus.steven.world.generators.StaticConnectionGenerator;
import haus.steven.world.transformers.IntervalProxy;
import haus.steven.world.transformers.RandomImmunizer;
import haus.steven.world.transformers.RandomInfector;
import haus.steven.world.statistics.ImmunizationLogger;
import haus.steven.world.statistics.InfectionLogger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jgrapht.Graph;
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
        Supplier<Connection> edgeSupplier = new StaticConnectionGenerator();

        Graph<Entity, Connection> network = new DefaultUndirectedGraph<>(vertexSupplier, edgeSupplier, false);

        ScaleFreeGraphGenerator<Entity, Connection> generator = new ScaleFreeGraphGenerator<>(1000);


        GraphImporter<Entity, Connection> importer = new CSVImporter<Entity, Connection>(new IndividualProvider(), new StaticConnectionProvider());

        try {
            importer.importGraph(network, new File(System.getenv("GRAPH_LOC")));
        } catch (ImportException e) {
            logger.error("Failed to import the graph!");
            System.exit(-1);
        }


        Spreadable spreadable = new Cold();

        World world = new World(network, spreadable);

        world.RegisterSetupTransformer(new RandomInfector(0.05));

        world.RegisterTickTransformer(new IntervalProxy(new RandomImmunizer(1, 1), 100));

        world.RegisterLogger(new InfectionLogger(10));
        world.RegisterLogger(new ImmunizationLogger(10, 0));

        logger.info("Created world");

        world.start();

        logger.info("Set up world");
        for (int i = 0; i < 50000; i++) {
            world.tick();
        }

        System.out.println(world.summarize());

        world.report();

        logger.info("Shutting down...");
    }
}
