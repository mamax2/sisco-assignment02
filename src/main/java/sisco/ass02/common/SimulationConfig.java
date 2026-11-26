//fede
package sisco.ass02.common;

/**
 * Parametri comuni a tutti i motori, così si confrontano sugli stessi dati.
 * La costante G non c'è: è fissa dentro Body (G_CONST_SCALED = 0.001).
 */
public final class SimulationConfig {
    public static final int DEFAULT_BODIES = 500;
    public static final int DEFAULT_STEPS = 100;
    public static final double DEFAULT_DT = 0.001;   // stesso valore del codice sequenziale fornito
    public static final long DEFAULT_SEED = 42L;
    public static final int DEFAULT_THREADS = Runtime.getRuntime().availableProcessors();

    private SimulationConfig() {
    }
}
