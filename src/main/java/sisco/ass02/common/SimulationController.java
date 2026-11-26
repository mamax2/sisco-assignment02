//fede
package sisco.ass02.common;

public interface SimulationController {
    void start();
    void pause();
    void resume();
    void stepOnce();
    void stop();
    SimulationState getState();
}