package haus.steven.simunnization.world.transformers;

import haus.steven.simunnization.world.World;

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