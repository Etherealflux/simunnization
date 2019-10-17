package haus.steven.experiments;

import java.util.Map;

/**
 * An Experiment is just a bundle of code to run.
 */
public interface Experiment {
    public void run(Map<String, String> args);
}
