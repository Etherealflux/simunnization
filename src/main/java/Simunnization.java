import haus.steven.experiments.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class Simunnization {
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        Map<String, String> expArgs = new HashMap<>();
        new ThresholdExperiment().run(expArgs);
    }
}
