//fede
package sisco.ass02.gui;

import sisco.ass02.common.BodyView;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.util.Collections;
import java.util.List;

public class BodiesPanel extends JPanel {

    private List<? extends BodyView> bodies = Collections.emptyList();

    public void updateBodies(List<? extends BodyView> snapshot) {
        this.bodies = snapshot != null ? snapshot : Collections.emptyList();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // TODO: mappare coordinate fisiche (x, y) in coordinate schermo e disegnare i corpi
    }
}