package sisco.ass02.mt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import sisco.ass02.common.BodyView;
import sisco.ass02.common.SimulationConfig;
import sisco.ass02.common.SimulationEngine;
import sisco.ass02.n_body_seq.Body;

/**
 * Motore thread
 *
 * Il thread che chiama step(), insieme agli n-workers attraversa la stessa barriera tre
 * volte per step (START, MID, END).
 * Quando step() ritorna, tutti i corpi sono aggiornati e lo stato è coerente.
 */
public class MultiThreadedEngine implements SimulationEngine {

	private final List<Body> bodies;
	private final double dt;
	private final Barrier barrier;
	private final List<Worker> workers;
	private double vt;   // tempo virtuale
	private boolean terminated = false;

	public MultiThreadedEngine(List<Body> bodies, double dt, int nWorkers) {
		this.bodies = bodies;
		this.dt = dt;
		this.vt = 0;
		this.barrier = new BarrierImpl(nWorkers + 1);   //perché partecipa anche il master
		this.workers = new ArrayList<>();

		// divido i corpi in blocchi uguali, l'ultimo worker prende anche il resto
		int chunk = bodies.size() / nWorkers;
		for (int k = 0; k < nWorkers; k++) {
			int from = k * chunk;
			int to = (k == nWorkers - 1) ? bodies.size() : from + chunk;
			workers.add(new Worker("worker-" + k, bodies, from, to, dt, barrier));
		}

		// i worker partono subito, ma restano fermi su START finché non chiamo step()
		for (Worker w : workers) {
			w.start();
		}
	}

	// un worker per ogni core disponibile
	public MultiThreadedEngine(List<Body> bodies, double dt) {
		this(bodies, dt, SimulationConfig.DEFAULT_THREADS);
	}

	@Override
	public void step() throws InterruptedException {
		if (terminated) {
			// i worker non ci sono più: senza questo controllo resterei bloccato sulla barriera
			throw new IllegalStateException("engine already shut down");
		}
		barrier.hitAndWaitAll();   // START: faccio partire i worker
		barrier.hitAndWaitAll();   // MID: tutti hanno calcolato le velocità
		barrier.hitAndWaitAll();   // END: tutti hanno aggiornato le posizioni
		vt += dt;
	}

	@Override
	public void runSteps(int steps) throws InterruptedException {
		for (int i = 0; i < steps; i++) {
			step();
		}
	}

	// lista in sola lettura; va letta tra uno step e l'altro
	@Override
	public List<? extends BodyView> getBodies() {
		return Collections.unmodifiableList(bodies);
	}

	@Override
	public double getVirtualTime() {
		return vt;
	}

	// interrompo i worker (fermi su START) e aspetto che terminino.
	// Va chiamato dallo stesso thread che chiama step(), quando non c'è uno step in corso
	// (vedi SimulationEngine.shutdown)
	@Override
	public void shutdown() {
		terminated = true;
		for (Worker w : workers) {
			w.interrupt();
		}
		for (Worker w : workers) {
			try {
				w.join();
			} catch (InterruptedException ex) {
				Thread.currentThread().interrupt();
			}
		}
	}
}
