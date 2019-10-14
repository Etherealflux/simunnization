package haus.steven.world.setup;

import haus.steven.world.World;

/**
 * Proxies access to a transformer. This can be used to make transformers conditional.
 */
public abstract class TransformerProxy implements EntityTransformer {
    private final EntityTransformer transformer;

    public TransformerProxy(EntityTransformer transformer) {
        this.transformer = transformer;
    }

    public void transform(World world) {
        transformer.transform(world);
    }

    public String toString() {
        return "Proxying: " + transformer.toString();
    }
}