import haus.steven.actors.Entity;
import haus.steven.actors.Individual;
import haus.steven.world.*;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultUndirectedGraph;
import haus.steven.spreading.Spreadable;
import haus.steven.spreading.disease.Cold;

import java.util.ArrayList;
import java.util.List;

public class Simunnization {
    public static void main(String[] args) {
        Graph<Entity, Connection> network = new DefaultUndirectedGraph<>(Connection.class);

        List<Entity> entities = new ArrayList<Entity>();

        for (int i = 0; i < 10; i++) {
            Entity ent = new Individual("Entity" + i);
            entities.add(ent);
            network.addVertex(ent);
        }

        entities.get(1).infect(1);

        for (int i = 0; i < 9; i++) {
            Connection conn = new StaticConnection();
            network.addEdge(entities.get(i), entities.get(i+1), conn);
        }

        System.out.println("Set up world");

        Spreadable spreadable = new Cold();

        World world = new World(network, spreadable);

        for (int i = 0; i < 1000; i++) {
            world.tick();
        }
    }
}
