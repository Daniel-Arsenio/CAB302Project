/**
 * Author: Marcus Nguyen
 * Developed for CAB 302 project
 * QUT student 2022
 */
package src.MazeGUI;

import MazeGUI.MazeCreatorComponents.JCell;
import javax.swing.border.MatteBorder;
import java.awt.*;
public class Cell {
     private int CellId;
     private int X_pos;
     private int Y_Pos;
     private JCell jCell;
     int right, left, bottom, top;

     public boolean Visited=false;

     public Cell(int X_position, int Y_position, int Width, int Height, int top, int left, int bottom, int right)
     {
          jCell = new JCell(X_position, Y_position, Width, Height, top, left, bottom, right);
          this.right = right;
          this.left = left;
          this.bottom = bottom;
          this.top = top;
     }

     public JCell getjcell() {
          return jCell;
     }

     public void BreakCellWall(String target){
          jCell.BreakWall(target);
          jCell.setBackground(Color.BLUE);
          jCell.repaint();
     }

     public JCell setBorder(int top, int left, int bottom, int right){
          MatteBorder border = new MatteBorder(top,left,bottom,right,Color.BLACK);
          jCell.setBorder(border);
          jCell.repaint();
          return jCell;
     }
}
