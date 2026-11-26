//fede
package sisco.ass02.exec;

import sisco.ass02.common.BodyView;
import sisco.ass02.common.SimulationEngine;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorEngine implements SimulationEngine {

    private final ExecutorService executor;
    private double virtualTime;

    public ExecutorEngine(int numBodies, int numThreads, double dt, double g, long seed) {
        this.executor = Executors.newFixedThreadPool(Math.max(1, numThreads));
        this.virtualTime = 0.0;
        // TODO: inizializzare i corpi (usando seed) e salvare parametri
    }

    @Override
    public void step() throws InterruptedException {
        // TODO: 1) calcolare forze/accelerazioni/velocità in parallelo leggendo lo stato attuale
        //       2) aggiornare le posizioni e incrementare virtualTime += dt
        throw new UnsupportedOperationException("TODO: implement step()");
    }

    @Override
    public void runSteps(int steps) throws InterruptedException {
        for (int i = 0; i < steps; i++) {
            step();
        }
    }

    @Override
    public List<? extends BodyView> getBodies() {
        // TODO: restituire uno snapshot/vista sicura dei corpi
        return Collections.emptyList();
    }

    @Override
    public double getVirtualTime() {
        return virtualTime;
    }

    @Override
    public void shutdown() {
        executor.shutdown();
    }
}