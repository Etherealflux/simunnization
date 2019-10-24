package haus.steven.simunnization.experiments;

import java.util.Map;

/**
 * An Experiment is just a bundle of code to run.
 */
public interface Experiment {
    void run(Map<String, String> args);
}
