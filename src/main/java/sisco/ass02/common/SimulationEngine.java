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

    /**
     * Stato attuale (vista in sola lettura sui corpi veri, non una copia).
     * Va letto tra uno step e l'altro; per la GUI serve una copia (la fa il controller)
     */
    List<? extends BodyView> getBodies();

    /** Tempo virtuale corrente */
    double getVirtualTime();

    /**
     * Libera le risorse.
     * Va chiamato dallo stesso thread che chiama step(), quando non c'è uno step
     * in corso (es. il thread del controller, all'uscita dal suo loop).
     * Dopo shutdown() non si può più chiamare step().
     */
    void shutdown();
}
