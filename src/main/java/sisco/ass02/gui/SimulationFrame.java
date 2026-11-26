//fede
package sisco.ass02.gui;

import sisco.ass02.common.BodyView;
import sisco.ass02.common.SimulationListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.util.List;

public class SimulationFrame extends JFrame implements SimulationListener {

    private final BodiesPanel bodiesPanel = new BodiesPanel();
    private final JLabel statusLabel = new JLabel("Stato: IDLE | Step: 0 | vt: 0.00");

    public SimulationFrame() {
        super("N-Body Simulation");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JButton startBtn = new JButton("Start");
        JButton pauseBtn = new JButton("Pause");
        JButton resumeBtn = new JButton("Resume");
        JButton stepBtn = new JButton("Step");
        JButton stopBtn = new JButton("Stop");

        JPanel controls = new JPanel();
        controls.add(startBtn);
        controls.add(pauseBtn);
        controls.add(resumeBtn);
        controls.add(stepBtn);
        controls.add(stopBtn);

        add(statusLabel, BorderLayout.NORTH);
        add(bodiesPanel, BorderLayout.CENTER);
        add(controls, BorderLayout.SOUTH);

        setSize(800, 600);
        setLocationRelativeTo(null);

        // TODO: collegare i pulsanti al SimulationController
    }

    @Override
    public void onStepCompleted(List<? extends BodyView> bodies, double virtualTime, long stepNumber) {
        SwingUtilities.invokeLater(() -> {
            bodiesPanel.updateBodies(bodies);
            statusLabel.setText(String.format("Stato: OK | Step: %d | vt: %.2f", stepNumber, virtualTime));
        });
    }
}