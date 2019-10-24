package haus.steven.simunnization;

import haus.steven.simunnization.experiments.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class Simunnization {
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        Map<String, String> expArgs = new HashMap<>();
        new VisualizationTest().run(expArgs);
    }
}
