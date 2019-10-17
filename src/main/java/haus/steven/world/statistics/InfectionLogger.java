package haus.steven.world.statistics;

import haus.steven.spreading.State;
import haus.steven.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.internal.chartpart.Chart;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Keeps track of how many people fall into each State category
 */
public class InfectionLogger implements WorldLogger {
    private static final Logger logger = LogManager.getLogger();

    private final List<Integer> ticks = new ArrayList<>();
    private final Map<State, List<Integer>> history = new HashMap<>();
    private final int period;

    public InfectionLogger(int period) {
        this.period = period;
        for (State state :
                State.values()) {
            history.put(state, new ArrayList<>());
        }
    }

    @Override
    public void tick(World world, int tick) {
        if (tick % period != 0)
            return;

        ticks.add(tick);

        for (State state :
                State.values()) {
            int count = world.count(state);

            history.get(state).add(count);
        }
    }

    @Override
    public List<Chart> chart() {
        List<Chart> charts = new ArrayList<>();
        double[] tickData = ticks.stream().mapToDouble(Number::doubleValue).toArray();
        XYChart chart = new XYChartBuilder().xAxisTitle("Tick").yAxisTitle("Count").title("Population Counts").build();
        chart.getStyler().setLegendFont(new Font("Helvetica", Font.PLAIN, 24));
        for (State state :
                State.values()) {
            double[] countData = history.get(state).stream().mapToDouble(Number::doubleValue).toArray();
            chart.addSeries(state.toString(), tickData, countData);

        }

        charts.add(chart);

        return charts;
    }
}
