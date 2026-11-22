package sisco.ass02.mt;

import java.util.List;

import sisco.ass02.n_body_seq.Body;
import sisco.ass02.n_body_seq.V2d;

/**
 * Ogni step ha due fasi separate da una barriera:
 *  1. calcola la nuova velocità (legge le posizioni di TUTTI i corpi)
 *  2. aggiorna la posizione
 * comunica con gli altri solo tramite la barriera e termina quando viene interrotto
 */
class Worker extends Thread {

	private final List<Body> bodies;   // tutti i corpi
	private final int from, to;        // il blocco di corpi di questo worker
	private final double dt;
	private final Barrier barrier;

	Worker(String name, List<Body> bodies, int from, int to, double dt, Barrier barrier) {
		super(name);
		this.bodies = bodies;
		this.from = from;
		this.to = to;
		this.dt = dt;
		this.barrier = barrier;
	}

	@Override
	public void run() {
		try {
			while (true) {
				// START: aspetto che il master chieda un nuovo step
				barrier.hitAndWaitAll();

				// fase 1 velocità
				for (int i = from; i < to; i++) {
					Body b = bodies.get(i);
					V2d totalForce = computeTotalGForceOnBody(b);
					V2d acc = new V2d(totalForce).scalarMul(1.0 / b.getMass());
					b.updateVelocity(acc, dt);
				}

				// MID: aspetto che tutti abbiano finito la fase 1,
				// altrimenti sposterei corpi che altri stanno ancora leggendo
				barrier.hitAndWaitAll();

				// fase 2 nuove posizioni
				for (int i = from; i < to; i++) {
					bodies.get(i).updatePos(dt);
				}

				// END: step finito per tutti
				barrier.hitAndWaitAll();
			}
		} catch (InterruptedException ex) {
			// interrotto dallo shutdown
		}
	}

	// forza totale esercitata dagli altri corpi su b
	private V2d computeTotalGForceOnBody(Body b) {
		V2d totalForce = new V2d(0, 0);
		for (int j = 0; j < bodies.size(); j++) {
			Body otherBody = bodies.get(j);
			if (!b.equals(otherBody)) {
				try {
					totalForce.sum(b.computeGForceBy(otherBody));
				} catch (Exception ex) {
					// corpi sovrapposti
				}
			}
		}
		return totalForce;
	}
}
