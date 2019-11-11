package haus.steven.simunnization.spreading;

import com.github.ajalt.colormath.ConvertibleColor;
import com.github.ajalt.colormath.RGB;
import com.sun.tools.javac.util.Convert;
import haus.steven.simunnization.world.World;

import java.util.HashMap;
import java.util.Map;


/**
 * A Spreadable has rules for how readily it spreads, as well as how quickly it dissipates.
 * Spreadables are shown both their hosts and their neighbors.
 */

public abstract class Spreadable {
    private Map<State, ConvertibleColor> colors = new HashMap<>();

    public Spreadable() {
        colors.put(State.SUSCEPTIBLE, new RGB(0, 255, 0));
        colors.put(State.INFECTED, new RGB(255, 0,0));
        colors.put(State.RECOVERED, new RGB(0, 0, 255));
    }
    /**
     * Invoked by the world every tick.
     *
     * @param world
     */
    public abstract void doTick(World world);

    public void setColor(State state, ConvertibleColor color) {
        colors.put(state, color);
    }

    public ConvertibleColor colorFor(State state) {
        return colors.get(state);
    }

    /**
     * Decides if two spreadables can infect the same singular entity
     * @param spreadable
     * @return
     */
    public boolean coexists(Spreadable spreadable) {
        return true;
    }
}
