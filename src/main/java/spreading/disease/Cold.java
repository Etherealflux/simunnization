package spreading.disease;

import spreading.SIRSpreadable;


/**
 * A very basic disease that spreads and fades quickly
 */
public class Cold extends SIRSpreadable {
    public Cold() {
        super(0.1, 0);
    }

    @Override
    public String getName() {
        return "Cold";
    }
}
