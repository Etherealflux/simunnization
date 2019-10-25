package haus.steven.simunnization.world.transformers.proxies;

import haus.steven.simunnization.spreading.State;
import haus.steven.simunnization.world.World;
import haus.steven.simunnization.world.transformers.Transformer;

/**
 * Only allows transformations if a sufficient number of entities
 * are in a certain state.
 */
public class CountProxy extends TransformerProxy {

    private final State state;
    private final int count;

    public CountProxy(Transformer transformer, State state, int count) {
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
