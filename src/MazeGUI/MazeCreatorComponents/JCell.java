package src.MazeGUI.MazeCreatorComponents;

import src.MazeGUI.MazeJFrame;
import src.Model.Cell;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.EventListener;

public class JCell extends JPanel implements EventListener {
    private int topEdge = 2;
    private int rightEdge = 2;
    private int bottomEdge=2;
    private int leftEdge = 2;

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
    public JCell(int X_position, int Y_position, int Width, int Height, int top, int left, int bottom, int right, MazeJFrame mazeFrame, Cell cell) {

        topEdge=top;
        leftEdge=left;
        rightEdge=right;
        bottomEdge=bottom;

        this.setLayout(null);
        this.setBounds(X_position+2, Y_position+2, Width, Height);
        this.setBorder(new MatteBorder(top, left, bottom, right, Color.BLACK));
        this.setVisible(true);

        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mazeFrame.lblFocused_X.setText("Focused X: "+Integer.toString((cell.getX_pos())));
                mazeFrame.lblFocused_Y.setText("Focused Y: "+Integer.toString((cell.getY_pos())));
                mazeFrame.changeFocus(cell.getX_pos(),cell.getY_pos());
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }

    /**
     * Break the wall in the targeted direction
     *
     * @param target
     */
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

    /**
     *
     *
     * @param target
     */
    public void AddWall(String target){
        if (target == "top")
        {
            topEdge=2;
            this.setBorder(new MatteBorder(topEdge,leftEdge,bottomEdge,rightEdge,Color.BLACK));
            this.repaint();
        }
        if (target == "left")
        {
            leftEdge=2;
            this.setBorder(new MatteBorder(topEdge,leftEdge,bottomEdge,rightEdge,Color.BLACK));
            this.repaint();
        }
        if (target == "bottom")
        {
            bottomEdge=2;
            this.setBorder(new MatteBorder(topEdge,leftEdge,bottomEdge,rightEdge,Color.BLACK));
            this.repaint();
        }
        if (target == "right")
        {
            rightEdge=2;
            this.setBorder(new MatteBorder(topEdge,leftEdge,bottomEdge,rightEdge,Color.BLACK));
            this.repaint();
        }
    }

    /**
     * Check if there is a wall in that direction
     * @param target
     * @return true if there is a wall and false if there is no wall
     */
    public boolean CheckWall(String target){
        boolean reachable = false;
        if (target == "top")
        {
            if(topEdge>0){
                reachable=false;
            }
            else {
                reachable=true;
            }
        }
        if (target == "left")
        {
            if(leftEdge>0){
                reachable=false;
            }
            else {
                reachable=true;
            }
        }
        if (target == "bottom")
        {
            if(bottomEdge>0){
                reachable=false;
            }
            else {
                reachable=true;
            }
        }
        if (target == "right")
        {
            if(rightEdge>0){
                reachable=false;
            }
            else {
                reachable=true;
            }
        }
        return  reachable;
    }
}
