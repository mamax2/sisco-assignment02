//fede
package sisco.ass02.common;

import java.util.List;

public interface SimulationListener {
    void onStepCompleted(List<? extends BodyView> bodies, double virtualTime, long stepNumber);
}