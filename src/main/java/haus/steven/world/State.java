package haus.steven.world;

/**
 * Actors can be in one of several states - susceptible, recovered, etc
 */
public enum State {
    SUSCEPTIBLE {
        public String toString() {
            return "Susceptible";
        }
    },
    INFECTED {
        public String toString() {
            return "Infected";
        }
    },
    RECOVERED {
        public String toString() {
            return "Recovered";
        }
    }
}
