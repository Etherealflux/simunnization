package haus.steven.world.statistics;

import haus.steven.spreading.State;
import haus.steven.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Keeps track of how many people fall into each State category
 */
public class InfectionLogger implements WorldLogger {
    private static final Logger logger = LogManager.getLogger();
    private final int period;

    public InfectionLogger(int period) {
        this.period = period;
    }

    @Override
    public void tick(World world, int tick) {
        if (tick % period != 0)
            return;

        for (State state :
                State.values()) {
            int count = world.count(state);

            logger.info(state.toString());
            logger.info(count);
        }
    }
}
