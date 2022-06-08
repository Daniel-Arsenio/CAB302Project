/**
 * Author: Marcus Nguyen
 */
package src.MazeGUI;

import src.MazeGUI.MazeCreatorComponents.JCell;

import javax.swing.border.MatteBorder;
import java.awt.*;

public class Cell {
     private int CellId;
     private int X_pos;
     private int Y_Pos;
     private JCell jCell;

     public boolean Visited=false;

     public Cell(int X_position, int Y_position, int Width, int Height, int top, int left, int bottom, int right)
     {
          jCell = new JCell(X_position, Y_position, Width, Height, top, left, bottom, right);
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

     public JCell setBorder(int top, int left, int bottom, int right){
          MatteBorder border = new MatteBorder(top,left,bottom,right,Color.BLACK);
          jCell.setBorder(border);
          jCell.repaint();
          return jCell;
     }
}
