package haus.steven.world.transformers;

import haus.steven.spreading.State;
import haus.steven.world.World;

/**
 * Only allows transformations if a sufficient number of entities
 * are in a certain state.
 */
public class CountProxy extends TransformerProxy {

    private final State state;
    private final int count;

    public CountProxy(EntityTransformer transformer, State state, int count) {
        super(transformer);
        this.state = state;
        this.count = count;
    }

    @Override
    public void transform(World world) {
        if (world.count(state) > count) {
            super.transform(world);
        }
    }

    @Override
    public String toString() {
        return String.format("Count proxy, expecting %d of %s - " + super.toString(), count, state.toString());
    }
}
