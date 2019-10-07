import haus.steven.actors.Entity;
import haus.steven.actors.Individual;
import haus.steven.world.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultUndirectedGraph;
import haus.steven.spreading.Spreadable;
import haus.steven.spreading.disease.Cold;

import java.util.ArrayList;
import java.util.List;

public class Simunnization {
    private static final Logger logger = LogManager.getLogger();
    public static void main(String[] args) {
        Graph<Entity, Connection> network = new DefaultUndirectedGraph<>(Connection.class);

        List<Entity> entities = new ArrayList<Entity>();

        for (int i = 0; i < 10; i++) {
            Entity ent = new Individual("Entity" + i);
            entities.add(ent);
            network.addVertex(ent);
        }

        entities.get(1).infect(1);
        System.out.println(entities.get(1));
        for (int i = 0; i < 9; i++) {
            Connection conn = new StaticConnection();
            network.addEdge(entities.get(i), entities.get(i+1), conn);
        }

        Spreadable spreadable = new Cold();

        World world = new World(network, spreadable);

        logger.info("Set up world");

        for (int i = 0; i < 1000; i++) {
            world.tick();
        }

        logger.info("Shutting down...");
    }
}
