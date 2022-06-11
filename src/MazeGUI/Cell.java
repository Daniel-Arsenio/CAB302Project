/**
 * Author: Marcus Nguyen
 */
package src.MazeGUI;

import src.MazeGUI.MazeCreatorComponents.JCell;


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

     public Cell(int X_position, int Y_position, int Width, int Height, int EdgeSize ,int top, int left, int bottom, int right, MazeJFrame mazeJFrame)
     {
          jCell = new JCell(X_position*EdgeSize, Y_position*EdgeSize, Width, Height, top, left, bottom, right , mazeJFrame , this);
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

     public int getX_pos(){
          return X_pos;
     }

     public int getY_pos(){
          return  Y_pos;
     }

     public void BreakCellWall(String target){
          jCell.BreakWall(target);
          jCell.repaint();
     }

     public void AddCellWall(String target){
          jCell.AddWall(target);
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

     public void dropCellFocus(){
          jCell.setBackground(null);
          jCell.repaint();
     }

     public void gainCellFocus(){
          jCell.setBackground(Color.GREEN);
          jCell.repaint();
     }
}
