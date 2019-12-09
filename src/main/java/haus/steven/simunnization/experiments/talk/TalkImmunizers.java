package haus.steven.simunnization.experiments.talk;

import haus.steven.simunnization.actors.Entity;
import haus.steven.simunnization.actors.suppliers.NumberedIndividualSupplier;
import haus.steven.simunnization.experiments.Experiment;
import haus.steven.simunnization.spreading.SIRSpreadable;
import haus.steven.simunnization.spreading.Spreadable;
import haus.steven.simunnization.world.World;
import haus.steven.simunnization.world.connections.Connection;
import haus.steven.simunnization.world.connections.suppliers.ToggleConnectionSupplier;
import haus.steven.simunnization.world.selectors.CyclicEntitySelector;
import haus.steven.simunnization.world.selectors.HighDegreeEntitySelector;
import haus.steven.simunnization.world.selectors.RandomEntitySelector;
import haus.steven.simunnization.world.selectors.RandomFriendSelector;
import haus.steven.simunnization.world.statistics.InfectionLogger;
import haus.steven.simunnization.world.statistics.WorldLogger;
import haus.steven.simunnization.world.transformers.Transformer;
import haus.steven.simunnization.world.transformers.immunizers.SelectorImmunizer;
import haus.steven.simunnization.world.transformers.infectors.SelectorInfector;
import org.jgrapht.Graph;
import org.jgrapht.generate.GraphGenerator;
import org.jgrapht.generate.RandomRegularGraphGenerator;
import org.jgrapht.generate.ScaleFreeGraphGenerator;
import org.jgrapht.graph.DefaultUndirectedGraph;

import java.util.*;
import java.util.function.Supplier;

public class TalkImmunizers implements Experiment {



    @java.lang.Override
    public void run(Map<String, String> args) {
        Spreadable spreadable = new SIRSpreadable(0.1, 0.2, new CyclicEntitySelector(10000));
        GraphGenerator<Entity, Connection, Entity> generator = new RandomRegularGraphGenerator<>(10000, 3);

        int counts[] = {1000, 2000, 4000, 8000};

        for (int i : counts) {
            makeGraph(new SIRSpreadable(0.2,
                            0.1,
                            new CyclicEntitySelector(10000),
                            SIRSpreadable.Mode.SIS),
                    new SelectorImmunizer(new RandomEntitySelector(i), 1.0f),
                    args.get("fig-dir") + "talk-immunize-random-random-" + i + ".png", generator);
        }

        generator = new ScaleFreeGraphGenerator<>(10000);

        for (int i : counts) {
            makeGraph(new SIRSpreadable(0.2,
                            0.1,
                            new CyclicEntitySelector(10000),
                            SIRSpreadable.Mode.SIS),
                    new SelectorImmunizer(new RandomEntitySelector(i), 1.0f),
                    args.get("fig-dir") + "talk-immunize-scalefree-random-" + i + ".png", generator);
        }

        int scalefreeCounts[] = {500, 1000, 1500, 2000};

        for (int i : scalefreeCounts) {
            makeGraph(new SIRSpreadable(0.2,
                            0.1,
                            new CyclicEntitySelector(10000),
                            SIRSpreadable.Mode.SIS),
                    new SelectorImmunizer(new HighDegreeEntitySelector(i), 1.0f),
                    args.get("fig-dir") + "talk-immunize-scalefree-highk-" + i + ".png", generator);
        }

        for (int i : scalefreeCounts) {
            makeGraph(new SIRSpreadable(0.2,
                            0.1,
                            new CyclicEntitySelector(10000),
                            SIRSpreadable.Mode.SIS),
                    new SelectorImmunizer(new RandomFriendSelector(i), 1.0f),
                    args.get("fig-dir") + "talk-immunize-scalefree-friend-" + i + ".png", generator);
        }

    }

    private void makeGraph(Spreadable spreadable, Transformer immunizer, String fileName, GraphGenerator<Entity, Connection, Entity> generator) {
        Collection<Spreadable> diseases = new ArrayList<Spreadable>();
        diseases.add(spreadable);

        Supplier<Entity> vertexSupplier = new NumberedIndividualSupplier(diseases);
        Supplier<Connection> edgeSupplier = new ToggleConnectionSupplier();

        Graph<Entity, Connection> network = new DefaultUndirectedGraph<>(vertexSupplier, edgeSupplier, false);

        generator.generateGraph(network);

        World world = new World(network, diseases);

        WorldLogger infectionLogger = new InfectionLogger(1);
        world.RegisterLogger(infectionLogger);
        world.RegisterSetupTransformer(new SelectorInfector(new RandomEntitySelector(100), spreadable));
        world.RegisterSetupTransformer(immunizer);
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
