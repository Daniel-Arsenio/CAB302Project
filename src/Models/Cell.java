/**
 * Author: Marcus Nguyen
 */
package Models;

import MazeGUI.MazeCreatorComponents.JCell;

import javax.swing.border.MatteBorder;
import java.awt.*;

public class Cell {
    private int CellId = 0;
    private int X_pos;
    private int Y_pos;
    private JCell jCell;
    private int topEdge;
    private int rightEdge;
    private int bottomEdge;
    private int leftEdge;

    public boolean Visited=false;

    public Cell(int X_position, int Y_position, int Width, int Height, int top, int left, int bottom, int right)
    {
        jCell = new JCell(X_position, Y_position, Width, Height, top, left, bottom, right);
        X_pos = X_position;
        Y_pos = Y_position;
        topEdge = top;
        leftEdge = left;
        rightEdge = right;
        bottomEdge = bottom;
    }

    public JCell getjcell() {
        return jCell;
    }

    public void BreakCellWall(String target){
        jCell.BreakWall(target);
        jCell.setBackground(null);
        jCell.repaint();
    }

    public boolean IsReachable(String target){
        return jCell.CheckWall(target);
    }

    public void markTracedBack(){
        jCell.setBackground(null);
        jCell.repaint();
    }

    public void markTraveled(){
        jCell.setBackground(Color.GREEN);
        jCell.repaint();
    }

    public void removeMark(){
        jCell.setBackground(null);
        jCell.repaint();
    }

    public JCell setBorder(int top, int left, int bottom, int right){
        MatteBorder border = new MatteBorder(top,left,bottom,right,Color.BLACK);
        jCell.setBorder(border);
        jCell.repaint();
        return jCell;
    }
}