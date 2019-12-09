package haus.steven.simunnization.experiments.talk;

import com.mxgraph.util.mxCellRenderer;
import com.mxgraph.view.mxGraph;
import haus.steven.simunnization.actors.Entity;
import haus.steven.simunnization.actors.suppliers.NumberedIndividualSupplier;
import haus.steven.simunnization.experiments.Experiment;
import haus.steven.simunnization.spreading.SIRSpreadable;
import haus.steven.simunnization.spreading.Spreadable;
import haus.steven.simunnization.spreading.ThresholdSpreadable;
import haus.steven.simunnization.world.World;
import haus.steven.simunnization.world.connections.Connection;
import haus.steven.simunnization.world.connections.suppliers.ToggleConnectionSupplier;
import haus.steven.simunnization.world.selectors.CyclicEntitySelector;
import haus.steven.simunnization.world.selectors.RandomEntitySelector;
import haus.steven.simunnization.world.statistics.InfectionLogger;
import haus.steven.simunnization.world.statistics.WorldLogger;
import haus.steven.simunnization.world.transformers.infectors.SelectorInfector;
import haus.steven.simunnization.world.visualize.NetworkViewer;
import org.jgrapht.Graph;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.generate.*;
import org.jgrapht.graph.DefaultUndirectedGraph;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Random;
import java.util.function.Supplier;

public class TalkSpreadingModels implements Experiment {


    @java.lang.Override
    public void run(Map<String, String> args) {
        for (SIRSpreadable.Mode mode : SIRSpreadable.Mode.values()) {
            makeGraph(new SIRSpreadable(0.2,
                            0.1,
                            new CyclicEntitySelector(10000),
                            mode),
                    args.get("fig-dir") + "talk-model-" + mode.toString().toLowerCase() + ".png", 3);
        }

        double options[][] = new double[3][2];

        options[0][0] = 0.2;
        options[0][1] = 0.1;
        options[1][0] = 0.2;
        options[1][1] = 0.2;
        options[2][0] = 0.1;
        options[2][1] = 0.2;
        for (SIRSpreadable.Mode mode : SIRSpreadable.Mode.values()) {
            for (int i = 0; i < 3; i++ ){
                makeGraph(new SIRSpreadable(options[i][0],
                                options[i][1],

                                new CyclicEntitySelector(10000),
                                mode),
                        args.get("fig-dir") + "talk-model-" + mode.toString().toLowerCase() + "-" + options[i][0] + "-" + options[i][1] + ".png", 3);
            }

        }

        for (int i = 0; i < 4; i++ ){
            makeGraph(new SIRSpreadable(0.1,
                            0.2,
                            new CyclicEntitySelector(10000),
                            SIRSpreadable.Mode.SIS),
                    args.get("fig-dir") + "talk-model-k-" + (1 << i) + ".png", 1 << i);
        }
    }

    private void makeGraph(Spreadable spreadable, String fileName, int k) {
        Collection<Spreadable> diseases = new ArrayList<Spreadable>();
        diseases.add(spreadable);

        Supplier<Entity> vertexSupplier = new NumberedIndividualSupplier(diseases);
        Supplier<Connection> edgeSupplier = new ToggleConnectionSupplier();

        Graph<Entity, Connection> network = new DefaultUndirectedGraph<>(vertexSupplier, edgeSupplier, false);

        GraphGenerator<Entity, Connection, Entity> generator = new RandomRegularGraphGenerator<>(10000, k);
        generator.generateGraph(network);

        World world = new World(network, diseases);

        WorldLogger infectionLogger = new InfectionLogger(1);
        world.RegisterLogger(infectionLogger);
        world.RegisterSetupTransformer(new SelectorInfector(new RandomEntitySelector(100), spreadable));
        world.start();

        for (int i=0; i<100; i++) {
            world.tick();
        }

        double totalDegree = 0;

        for (Entity entity : network.vertexSet()) {
            totalDegree += network.degreeOf(entity);
        }

        System.out.println("Average degree: " + (totalDegree / network.vertexSet().size()));
        infectionLogger.save(fileName);
    }
}
