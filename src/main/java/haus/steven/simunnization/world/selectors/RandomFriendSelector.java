package haus.steven.simunnization.world.selectors;

import haus.steven.simunnization.actors.Entity;
import haus.steven.simunnization.world.World;
import org.jgrapht.Graphs;

import java.util.*;

public class RandomFriendSelector implements EntitySelector {

    private final Random random = new Random();
    private List<Entity> entities;
    private int count;

    public RandomFriendSelector(int count) {
        this.count = count;
    }

    private void initWorld(World world) {
        this.entities = new ArrayList<>(world.network.vertexSet());
    }

    @Override
    public Collection<Entity> select(World world) {
        if (entities == null) {
            initWorld(world);
        }

        Set<Entity> group0 = new HashSet<>();
        while (group0.size() < count) {
            int index = (int) (Math.random() * entities.size());
            group0.add(entities.get(index));
        }

        Set<Entity> group1 = new HashSet<>();

        for (Entity entity : group0) {
            List<Entity> neighbors = Graphs.neighborListOf(world.network, entity);
            group1.add(neighbors.get(random.nextInt(neighbors.size())));
        }

        System.out.println(group1.size());


        return group1;
    }
}