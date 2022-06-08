/**
 * Author: Marcus Nguyen
 * Developed for CAB 302 project
 * QUT student 2022
 */
package MazeGUI.MazeCreatorComponents;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.util.EventListener;

public class JCell extends JPanel implements EventListener {
    int topEdge = 2;
    int rightEdge = 2;
    int bottomEdge=2;
    int leftEdge = 2;
    /**
     * Constructor
     * @param X_position position of this MazeGUI.Cell on the frame horizontally
     * @param Y_position position of this MazeGUI.Cell on the frame vertically
     * @param Width      Width of the MazeGUI.Cell
     * @param Height     Height of the MazeGUI.Cell
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


    public void BreakWall(String target){
        if (target == "top")
        {
            topEdge=0;
            this.setBorder(new MatteBorder(topEdge,leftEdge,bottomEdge,rightEdge,Color.BLACK));
            this.repaint();
        }
        if (target == "left")
        {
            leftEdge=0;
            this.setBorder(new MatteBorder(topEdge,leftEdge,bottomEdge,rightEdge,Color.BLACK));
            this.repaint();
        }
        if (target == "bottom")
        {
            bottomEdge=0;
            this.setBorder(new MatteBorder(topEdge,leftEdge,bottomEdge,rightEdge,Color.BLACK));
            this.repaint();
        }
        if (target == "right")
        {
            rightEdge=0;
            this.setBorder(new MatteBorder(topEdge,leftEdge,bottomEdge,rightEdge,Color.BLACK));
            this.repaint();
        }
    }

    public void Addwall(String target){
        if(target == "left"){
            leftEdge = 2;
            this.setBorder(new MatteBorder(topEdge,leftEdge,bottomEdge,rightEdge,Color.BLACK));
            this.repaint();
        }
        if(target == "right"){
            rightEdge = 2;
            this.setBorder(new MatteBorder(topEdge,leftEdge,bottomEdge,rightEdge,Color.BLACK));
            this.repaint();
        }
        if(target == "top"){
            topEdge = 2;
            this.setBorder(new MatteBorder(topEdge,leftEdge,bottomEdge,rightEdge,Color.BLACK));
            this.repaint();
        }
        if(target == "bottom"){
            bottomEdge = 2;
            this.setBorder(new MatteBorder(topEdge,leftEdge,bottomEdge,rightEdge,Color.BLACK));
            this.repaint();
        }
    }
}
