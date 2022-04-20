/**
 * Author: Marcus Nguyen
 * Developed for CAB 302 project
 * QUT student 2022
 */
package Components;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.util.EventListener;

public class JCell extends JPanel implements EventListener {
    /**
     * Variable Declaration
     */
    int topEdge = 2;
    int leftEdge = 2;
    int bottomEdge = 2;
    int rightEdge = 2;

    /**
     * Constructor
     * @param X_position position of this Cell on the frame horizontally
     * @param Y_position position of this Cell on the frame vertically
     * @param Width      Width of the Cell
     * @param Height     Height of the Cell
     * @param top        top edge of the cell
     * @param left       left edge of the cell
     * @param bottom     bottom edge of the cell
     * @param right      right edge of the cell
     */
    public JCell(int X_position, int Y_position, int Width, int Height, int top, int left, int bottom, int right) {
        this.setLayout(null);
        this.setBounds(X_position+2, Y_position+2, Width, Height);
        this.setBorder(new MatteBorder(top, left, bottom, right, Color.BLACK));
        this.setVisible(true);
        repaint();
    }

    public void RemoveCellEdge(String target) {
        this.repaint();
    }
}
