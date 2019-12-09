package haus.steven.simunnization;

import haus.steven.simunnization.experiments.VisualizationTest;
import haus.steven.simunnization.experiments.talk.TalkGraphShapes;
import haus.steven.simunnization.experiments.talk.TalkImmunizers;
import haus.steven.simunnization.experiments.talk.TalkScaleFreeBehavior;
import haus.steven.simunnization.experiments.talk.TalkSpreadingModels;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class SimunnizationTalk {
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        Map<String, String> expArgs = new HashMap<>();
        expArgs.put("fig-dir", "C:/Users/hausss/Documents/RPI/2019/frontiers/talk/figs/generated/");
        new TalkGraphShapes().run(expArgs);
        //new TalkSpreadingModels().run(expArgs);
        //new TalkScaleFreeBehavior().run(expArgs);
        //new TalkImmunizers().run(expArgs);
    }
}
