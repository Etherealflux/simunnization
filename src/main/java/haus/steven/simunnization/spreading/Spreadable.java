package haus.steven.simunnization.spreading;

import com.github.ajalt.colormath.ConvertibleColor;
import com.github.ajalt.colormath.RGB;
import com.sun.tools.javac.util.Convert;
import haus.steven.simunnization.world.World;


/**
 * A Spreadable has rules for how readily it spreads, as well as how quickly it dissipates.
 * Spreadables are shown both their hosts and their neighbors.
 */

public interface Spreadable {
    /**
     * Invoked by the world every tick.
     *
     * @param world
     */
    void doTick(World world);

    default ConvertibleColor colorFor(State state) {
        switch(state) {
            case SUSCEPTIBLE: return new RGB(0, 255, 0);
            case INFECTED: return new RGB(255, 0, 0);
            case RECOVERED: return new RGB(0, 0, 255);
        }

        return new RGB(0,0,0);
    }
}
