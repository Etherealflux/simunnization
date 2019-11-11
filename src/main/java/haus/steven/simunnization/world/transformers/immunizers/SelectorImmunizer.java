package haus.steven.simunnization.world.transformers.immunizers;

import haus.steven.simunnization.actors.Entity;
import haus.steven.simunnization.world.World;
import haus.steven.simunnization.world.selectors.EntitySelector;
import haus.steven.simunnization.world.transformers.Transformer;

/**
 * Immunizes entities provided by a selector
 */
public class SelectorImmunizer implements Transformer {

    private final EntitySelector selector;
    private final float efficacy;

    public SelectorImmunizer(EntitySelector selector, float efficacy) {
        this.selector = selector;
        this.efficacy = efficacy;
    }

    @Override
    public void transform(World world) {
        for (Entity entity : selector.select(world)) {
            entity.immunize(efficacy);
        }
    }
}
