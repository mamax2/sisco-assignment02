//fede
package sisco.ass02.common;

import java.util.List;

/**
 * bodies deve essere una copia fatta dal controller dopo lo step:
 * la GUI può leggerla dall'EDT senza race con il motore.
 */
public interface SimulationListener {
    void onStepCompleted(List<? extends BodyView> bodies, double virtualTime, long stepNumber);
}