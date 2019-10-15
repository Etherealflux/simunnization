package haus.steven.world.statistics;

import haus.steven.actors.Entity;
import haus.steven.world.World;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.internal.chartpart.Chart;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Tracks how many people are considered to be immune
 */
public class ImmunizationLogger implements WorldLogger {

    private final int period;
    private final double threshold;

    private final List<Integer> ticks = new ArrayList<>();
    private final List<Integer> immune = new ArrayList<>();

    public ImmunizationLogger(int period, double threshold) {
        this.period = period;
        this.threshold = threshold;
    }

    @Override
    public void tick(World world, int tick) {
        if (tick % period != 0)
            return;

        ticks.add(tick);

        int immuneCount = 0;
        for (Entity entity :
                world.network.vertexSet()) {
            if (entity.susceptibility() <= threshold) {
                immuneCount += 1;
            }
        }

        immune.add(immuneCount);
    }

    @Override
    public List<Chart> chart() {
        List<Chart> charts = new ArrayList<>();
        double[] tickData = ticks.stream().mapToDouble(Number::doubleValue).toArray();
        double[] immuneData = immune.stream().mapToDouble(Number::doubleValue).toArray();

        XYChart chart = new XYChartBuilder().xAxisTitle("Tick").yAxisTitle("Count").title("Population Counts").build();

        chart.getStyler().setLegendFont(new Font("Helvetica", Font.PLAIN, 24));
        chart.addSeries("Immune", tickData, immuneData);

        charts.add(chart);

        return charts;
    }
}
