package haus.steven.simunnization.world.transformers.proxies;

import haus.steven.simunnization.world.World;
import haus.steven.simunnization.world.transformers.Transformer;

/**
 * Proxies access to a transformer. This can be used to make transformers conditional.
 */
public abstract class TransformerProxy implements Transformer {
    private final Transformer transformer;

    public TransformerProxy(Transformer transformer) {
        this.transformer = transformer;
    }

    public void transform(World world) {
        transformer.transform(world);
    }

    public String toString() {
        return "Proxying: " + transformer.toString();
    }
}