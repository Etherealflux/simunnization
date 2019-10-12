package haus.steven.world.statistics;

import haus.steven.world.World;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.internal.chartpart.Chart;

import java.util.List;

/**
 * A WorldLogger is offered the opportunity to collect information about the world
 * every tick. This information is then requested at the end.
 *
 * I haven't decided yet how to request the information, though! :)
 */
public interface WorldLogger {
    public void tick(World world, int tick);

    public List<Chart> chart();
    default void show() {
        List<Chart> charts = chart();

        for (Chart chart :
                charts) {
            new SwingWrapper(chart).displayChart();
        }
    }
}
