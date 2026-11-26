//fede
package sisco.ass02.gui;

import sisco.ass02.common.SimulationController;
import sisco.ass02.common.SimulationEngine;
import sisco.ass02.common.SimulationListener;
import sisco.ass02.common.SimulationState;

import java.util.Objects;

public class DefaultSimulationController implements SimulationController {

    private final SimulationEngine engine;
    private final SimulationListener listener;
    private volatile SimulationState state = SimulationState.IDLE;

    public DefaultSimulationController(SimulationEngine engine, SimulationListener listener) {
        this.engine = Objects.requireNonNull(engine);
        this.listener = Objects.requireNonNull(listener);
    }

    @Override
    public void start() {
        // TODO: avviare il thread loop e portare lo stato a RUNNING
        state = SimulationState.RUNNING;
    }

    @Override
    public void pause() {
        // TODO: mettere in pausa il loop di esecuzione
        state = SimulationState.PAUSED;
    }

    @Override
    public void resume() {
        // TODO: riprendere l'esecuzione da PAUSED
        state = SimulationState.RUNNING;
    }

    @Override
    public void stepOnce() {
        // TODO: eseguire un singolo step se in PAUSED/IDLE e notificare il listener
    }

    @Override
    public void stop() {
        // TODO: fermare il loop, portare lo stato a STOPPED e rilasciare risorse se previsto
        state = SimulationState.STOPPED;
    }

    @Override
    public SimulationState getState() {
        return state;
    }
}