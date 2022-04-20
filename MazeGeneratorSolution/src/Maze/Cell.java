/**
 * Author: Marcus Nguyen
 * Developed for CAB 302 project
 * QUT student 2022
 */
package Maze;

import Components.JCell;

import javax.swing.border.MatteBorder;
import java.awt.*;

public class Cell {
     private int CellId;
     private int X_pos;
     private int Y_Pos;
     private JCell jCell;

     public Cell(int X_position, int Y_position, int Width, int Height, int top, int left, int bottom, int right)
     {
          jCell = new JCell(X_position, Y_position, Width, Height, top, left, bottom, right);
     }

     public JCell getjcell() {
          return jCell;
     }
}
