package haus.steven.experiments;

import com.mxgraph.view.mxGraph;
import haus.steven.actors.Entity;
import haus.steven.actors.suppliers.NumberedIndividualSupplier;
import haus.steven.spreading.SIRSpreadable;
import haus.steven.spreading.Spreadable;
import haus.steven.spreading.ThresholdSpreadable;
import haus.steven.world.World;
import haus.steven.world.connections.Connection;
import haus.steven.world.connections.suppliers.ToggleConnectionSupplier;
import haus.steven.world.statistics.InfectionLogger;
import haus.steven.world.transformers.RandomInfector;
import haus.steven.world.visualize.NetworkViewer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jgrapht.Graph;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.generate.RandomRegularGraphGenerator;
import org.jgrapht.generate.ScaleFreeGraphGenerator;
import org.jgrapht.graph.DefaultUndirectedGraph;

import javax.swing.*;
import java.util.Map;
import java.util.function.Supplier;

public class VisualizationTest implements Experiment {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public void run(Map<String, String> args) {
        Supplier<Entity> vertexSupplier = new NumberedIndividualSupplier();
        Supplier<Connection> edgeSupplier = new ToggleConnectionSupplier();

        Graph<Entity, Connection> network = new DefaultUndirectedGraph<>(vertexSupplier, edgeSupplier, false);

        ScaleFreeGraphGenerator<Entity, Connection> generator = new ScaleFreeGraphGenerator<>(100);

        generator.generateGraph(network);

        Spreadable spreadable = new SIRSpreadable(0.3, 0.0) {

            @Override
            public String getName() {
                return "Test";
            }
        };

        World world = new World(network, spreadable);

        world.RegisterSetupTransformer(new RandomInfector(0.1));

        logger.info("Created world");

        world.start();

        logger.info("Set up world");
        for (int i = 0; i < 100000; i++) {
            world.tick();
        }

        System.out.println(world.summarize());

        world.report();

        mxGraph graph = new JGraphXAdapter<Entity, Connection>(network);

        NetworkViewer viewer = new NetworkViewer(graph);

        viewer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        viewer.setSize(400, 320);
        viewer.setVisible(true);

        logger.info("Shutting down...");
    }
}
