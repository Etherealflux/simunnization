package haus.steven.simunnization;

import haus.steven.simunnization.experiments.VisualizationTest;
import haus.steven.simunnization.experiments.talk.TalkGraphShapes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class SimunnizationTalk {
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        Map<String, String> expArgs = new HashMap<>();
        new TalkGraphShapes().run(expArgs);
    }
}
