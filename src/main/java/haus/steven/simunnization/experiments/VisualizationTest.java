package haus.steven.simunnization.experiments;

import com.github.ajalt.colormath.RGB;
import com.mxgraph.view.mxGraph;
import haus.steven.simunnization.actors.Entity;
import haus.steven.simunnization.actors.suppliers.NumberedIndividualSupplier;
import haus.steven.simunnization.spreading.Spreadable;
import haus.steven.simunnization.spreading.State;
import haus.steven.simunnization.spreading.ThresholdSpreadable;
import haus.steven.simunnization.spreading.selectors.RandomEntitySelector;
import haus.steven.simunnization.world.World;
import haus.steven.simunnization.world.connections.Connection;
import haus.steven.simunnization.world.connections.suppliers.ToggleConnectionSupplier;
import haus.steven.simunnization.world.transformers.RandomInfector;
import haus.steven.simunnization.world.visualize.NetworkViewer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jgrapht.Graph;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.generate.ScaleFreeGraphGenerator;
import org.jgrapht.graph.DefaultUndirectedGraph;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public class VisualizationTest implements Experiment {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public void run(Map<String, String> args) {
        Spreadable spreadableA = new ThresholdSpreadable(0.4f, 0.2f, new RandomEntitySelector(1));
        Spreadable spreadableB = new ThresholdSpreadable(0.4f, 0.2f, new RandomEntitySelector(1));

        spreadableA.setColor(State.SUSCEPTIBLE, new RGB(250, 250, 250));
        spreadableB.setColor(State.SUSCEPTIBLE, new RGB(250, 250, 250));
        spreadableA.setColor(State.INFECTED, new RGB(255, 0, 0));
        spreadableB.setColor(State.INFECTED, new RGB(0, 0, 255));
        spreadableA.setColor(State.RECOVERED, new RGB(0, 0, 0));
        spreadableB.setColor(State.RECOVERED, new RGB(0, 0, 0));

        List<Spreadable> spreadables = new ArrayList<>(Arrays.asList(spreadableA, spreadableB));

        Supplier<Entity> vertexSupplier = new NumberedIndividualSupplier(spreadables);
        Supplier<Connection> edgeSupplier = new ToggleConnectionSupplier();

        Graph<Entity, Connection> network = new DefaultUndirectedGraph<>(vertexSupplier, edgeSupplier, false);

        ScaleFreeGraphGenerator<Entity, Connection> generator = new ScaleFreeGraphGenerator<>(100);

        generator.generateGraph(network);

        World world = new World(network, spreadables);

        world.RegisterSetupTransformer(new RandomInfector(spreadableA, 0.3));
        world.RegisterSetupTransformer(new RandomInfector(spreadableB, 0.3));

        logger.info("Created world");

        world.start();

        logger.info("Set up world");

        mxGraph graph = new JGraphXAdapter<>(network);

        NetworkViewer viewer = new NetworkViewer(graph);

        viewer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        viewer.setSize(400, 320);
        viewer.setVisible(true);

        for (int i = 0; i < 100; i++) {
            world.tick();
            if (i % 10 == 0) {
                viewer.update();
                try {
                    TimeUnit.MILLISECONDS.sleep(1000);
                } catch (InterruptedException e) {

                }
            }

        }

        System.out.println(world.summarize());

        world.report();

        logger.info("Shutting down...");
    }
}
