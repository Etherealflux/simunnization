package haus.steven.simunnization.world.transformers.infectors;

import haus.steven.simunnization.actors.Entity;
import haus.steven.simunnization.spreading.Spreadable;
import haus.steven.simunnization.world.World;
import haus.steven.simunnization.world.selectors.EntitySelector;
import haus.steven.simunnization.world.transformers.Transformer;

/**
 * Immunizes entities provided by a selector
 */
public class SelectorInfector implements Transformer {

    private final EntitySelector selector;
    private final Spreadable spreadable;

    public SelectorInfector(EntitySelector selector, Spreadable spreadable) {
        this.selector = selector;
        this.spreadable = spreadable;
    }

    @Override
    public void transform(World world) {
        for (Entity entity : selector.select(world)) {
            entity.infect(spreadable, 1);
        }
    }
}
