package sisco.ass02.n_body_seq;

import java.util.*;

public class NBodySimulation {

	private Optional<SimulationView> view;

	/* bodies in the field */
	List<Body> bodies;

	/* virtual time */
	private double vt;

	/* virtual time step */
	double dt;

	public NBodySimulation(SimulationView view) {
		this.view = Optional.of(view);
	}
	
	public NBodySimulation() {
		this.view = Optional.empty();
	}
	
	public void init(ArrayList<Body> bodies) {
		this.bodies = bodies;
	}

	public void initRandom(int nBodies) {
		initRandom(nBodies, System.currentTimeMillis());
	}

	/* con lo stesso seed si ottengono sempre gli stessi corpi (per confrontare i motori) */
	public void initRandom(int nBodies, long seed) {
		Random rand = new Random(seed);
		bodies = new ArrayList<Body>();
		for (int i = 0; i < nBodies; i++) {
			double x = -1.0 + 2.0*rand.nextDouble();
			double y = -1.0 + 2.0*rand.nextDouble();
			double vx = -0.1 + 0.2*rand.nextDouble();
			double vy = -0.1 + 0.2*rand.nextDouble();
			double m = 1 + rand.nextDouble()*9;
			Body b = new Body(i, new P2d(x, y), new V2d(vx, vy), 0.01, m);
			bodies.add(b);
		}
	}
		
	public void execute(long nSteps) {

		if (view.isPresent()) {
			view.get().init();
		}
		
		/* init virtual time */

		vt = 0;
		dt = 0.001;

		long iter = 0;

		/* simulation loop */

		while (iter < nSteps) {

			/* update bodies velocity */

			for (int i = 0; i < bodies.size(); i++) {
				Body b = bodies.get(i);

				/* compute total force on bodies */
				V2d totalForce = computeTotalGForceOnBody(b, bodies);

				/* compute instant acceleration */
				V2d acc = new V2d(totalForce).scalarMul(1.0 / b.getMass());

				/* update velocity */
				b.updateVelocity(acc, dt);
			}

			/* compute bodies new pos */

			
			for (Body b : bodies) {
				b.updatePos(dt);
			}

			/* update virtual time */

			vt = vt + dt;
			iter++;

			/* display current stage */

			if (view.isPresent()) {
				view.get().display(bodies, vt, iter);
			}
		}
	}

	/*
	 * 
	 * Compute the total G force exterted on a body by the other bodies
	 * (public static: riusato anche dai motori concorrenti)
	 */
	public static V2d computeTotalGForceOnBody(Body b, List<Body> bodies) {
		V2d totalForce = new V2d(0, 0);
		for (int j = 0; j < bodies.size(); j++) {
			Body otherBody = bodies.get(j);
			if (!b.equals(otherBody)) {
				try {
					V2d forceByOtherBody = b.computeGForceBy(otherBody);
					totalForce.sum(forceByOtherBody);
				} catch (Exception ex) {
				}
			}
		}
		return totalForce;
	}
	
	public List<Body> getBodies() {
		return bodies;
	}

	public int getNumBodies() {
		return bodies.size();
	}	
}
