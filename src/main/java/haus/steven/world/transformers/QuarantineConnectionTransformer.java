package haus.steven.world.transformers;

import haus.steven.world.Connection;
import haus.steven.world.ToggleConnection;
import haus.steven.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * Closes all connections, if possible
 */
public class QuarantineConnectionTransformer implements EntityTransformer {
    private static final Logger logger = LogManager.getLogger();

    private boolean triggered = false;
    @Override
    public void transform(World world) {
        if (triggered)
            return;
        logger.info("Quarantining...");
        triggered = true;
        for (Connection conn : world.network.edgeSet()) {
            if (conn instanceof ToggleConnection) {
                ((ToggleConnection) conn).close();
            }
        }
    }
}
