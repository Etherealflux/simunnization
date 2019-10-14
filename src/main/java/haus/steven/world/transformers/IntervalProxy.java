package haus.steven.world.transformers;

import haus.steven.world.World;

/**
 * Invokes the transformer once every
 */
public class IntervalProxy extends TransformerProxy {
    private final int period;

    public IntervalProxy(EntityTransformer transformer, int period) {
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
