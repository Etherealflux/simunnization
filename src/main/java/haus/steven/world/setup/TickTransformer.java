package haus.steven.world.setup;

import haus.steven.actors.Entity;
import haus.steven.world.Connection;
import org.jgrapht.Graph;

public interface TickTransformer extends EntityTransformer {
    public void transform(Graph<Entity, Connection> network, int tick);
}
