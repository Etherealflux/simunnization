package haus.steven.world.statistics;

import haus.steven.world.World;

/**
 * A WorldLogger is offered the opportunity to collect information about the world
 * every tick. This information is then requested at the end.
 *
 * I haven't decided yet how to request the information, though! :)
 */
public interface WorldLogger {
    public void tick(World world, int tick);

    public void show();
}
