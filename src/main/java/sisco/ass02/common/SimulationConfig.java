//fede
package sisco.ass02.common;

public final class SimulationConfig {
    public static final int DEFAULT_BODIES = 500;
    public static final int DEFAULT_STEPS = 100;
    public static final double DEFAULT_DT = 0.01;
    public static final double DEFAULT_G = 6.67430e-11; // o il valore usato in n_body_seq
    public static final long DEFAULT_SEED = 42L;

    private SimulationConfig() {
    }
}