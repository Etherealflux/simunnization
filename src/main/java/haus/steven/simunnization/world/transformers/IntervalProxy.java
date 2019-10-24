package haus.steven.simunnization.world.transformers;

import haus.steven.simunnization.world.World;

/**
 * Invokes the transformer once every
 */
public class IntervalProxy extends TransformerProxy {
    private final int period;

    public IntervalProxy(Transformer transformer, int period) {
        super(transformer);
        this.period = period;
    }

    @Override
    public void transform(World world) {
        if (world.getTick() % period == 0)
            super.transform(world);
    }

    public String toString() {
        return String.format("Interval proxy, period of %d - " + super.toString(), period);
    }
}
