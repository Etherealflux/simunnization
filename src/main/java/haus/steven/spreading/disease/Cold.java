package haus.steven.spreading.disease;

import haus.steven.spreading.SIRSpreadable;


/**
 * A very basic disease that spreads and fades quickly
 */
public class Cold extends SIRSpreadable {
    public Cold() {
        super(0.1, 0.1);
    }

    @Override
    public String getName() {
        return "Cold";
    }
}
