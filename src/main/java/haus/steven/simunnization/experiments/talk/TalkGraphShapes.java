package haus.steven.simunnization.experiments.talk;

import com.mxgraph.util.mxCellRenderer;
import com.mxgraph.view.mxGraph;
import haus.steven.simunnization.actors.Entity;
import haus.steven.simunnization.actors.suppliers.NumberedIndividualSupplier;
import haus.steven.simunnization.experiments.Experiment;
import haus.steven.simunnization.spreading.Spreadable;
import haus.steven.simunnization.spreading.ThresholdSpreadable;
import haus.steven.simunnization.world.World;
import haus.steven.simunnization.world.connections.Connection;
import haus.steven.simunnization.world.connections.suppliers.ToggleConnectionSupplier;
import haus.steven.simunnization.world.selectors.RandomEntitySelector;
import haus.steven.simunnization.world.visualize.NetworkViewer;
import org.jgrapht.Graph;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.generate.*;
import org.jgrapht.graph.DefaultUndirectedGraph;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.function.Supplier;

public class TalkGraphShapes implements Experiment {

    @java.lang.Override
    public void run(Map<String, String> args) {
        String prefix = args.get("fig-dir");
        makeGraph(new RandomRegularGraphGenerator<>(25, 2), prefix + "graph-random.png");
        makeGraph(new ScaleFreeGraphGenerator<Entity, Connection>(25), prefix + "graph-scalefree.png");
    }

    private void makeGraph(GraphGenerator<Entity, Connection, Entity> generator, String fileName) {
        Collection<Spreadable> diseases = new ArrayList<Spreadable>();
        diseases.add(new ThresholdSpreadable(0.5, 0.05, new RandomEntitySelector(1)));

        Supplier<Entity> vertexSupplier = new NumberedIndividualSupplier(diseases);
        Supplier<Connection> edgeSupplier = new ToggleConnectionSupplier();

        Graph<Entity, Connection> network = new DefaultUndirectedGraph<>(vertexSupplier, edgeSupplier, false);

        generator.generateGraph(network);

        mxGraph graph = new JGraphXAdapter<>(network);
        mxGraph adapted = new NetworkViewer(graph).graph;

        BufferedImage image = mxCellRenderer.createBufferedImage(graph, null, 1, Color.WHITE, true, null);
        try {
            ImageIO.write(image, "PNG", new File(fileName));
        } catch (IOException e) {
            System.exit(1);
        }
    }
}
