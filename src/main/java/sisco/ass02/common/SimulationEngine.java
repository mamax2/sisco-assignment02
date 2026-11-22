package sisco.ass02.common;

import java.util.List;

/**
 * Motore di simulazione
 * */
public interface SimulationEngine {

    /** Esegue un singolo step */
    void step() throws InterruptedException;

    /** Esegue M step consecutivi (senza GUI) */
    void runSteps(int steps) throws InterruptedException;

    /** Stato attuale per la GUI */
    List<? extends BodyView> getBodies();

    /** Tempo virtuale corrente */
    double getVirtualTime();

    /** Libera le risorse  */
    void shutdown();
}
