//fede
package sisco.ass02.exec;

import sisco.ass02.common.BodyView;
import sisco.ass02.common.SimulationEngine;
import sisco.ass02.n_body_seq.Body;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorEngine implements SimulationEngine {

    private final List<Body> bodies;
    private final double dt;
    private final ExecutorService executor;
    private double virtualTime;

    /**
     * Stessa firma di MultiThreadedEngine: i corpi arrivano già creati
     * (NBodySimulation.initRandom(n, seed) + getBodies()), così entrambi i motori
     * partono dagli stessi dati.
     */
    public ExecutorEngine(List<Body> bodies, double dt, int numThreads) {
        this.bodies = bodies;
        this.dt = dt;
        this.executor = Executors.newFixedThreadPool(Math.max(1, numThreads));
        this.virtualTime = 0.0;
    }

    @Override
    public void step() throws InterruptedException {
        // TODO: 1) calcolare le velocità in parallelo con NBodySimulation.computeTotalGForceOnBody(b, bodies)
        //          (come nella fase 1 di mt.Worker)
        //          (leggono le posizioni attuali: aspettare che finiscano TUTTI i task)
        //       2) aggiornare le posizioni (b.updatePos(dt)) e virtualTime += dt
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
        // vista in sola lettura, come MultiThreadedEngine (la copia per la GUI la fa il controller)
        return Collections.unmodifiableList(bodies);
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
