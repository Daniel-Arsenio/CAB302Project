package src.Model;

import src.MazeGUI.MazeCreatorComponents.JCell;
import src.MazeGUI.MazeJFrame;
import src.Model.Interfaces.ICell;


import javax.swing.border.MatteBorder;
import java.awt.*;

public class Cell implements ICell {
     private int X_pos;
     private int Y_pos;
     private JCell jCell;
     private int topEdge;
     private int rightEdge;
     private int bottomEdge;
     private int leftEdge;

     public boolean Visited=false;

     /**
      * Constructor of Cell
      *
      * @param X_position X position of the cell
      * @param Y_position Y position of the cell
      * @param Width the Width of the cell
      * @param Height the height of the cell
      * @param EdgeSize the size of the Edge
      * @param top top Edge value
      * @param left left Edge value
      * @param bottom bottom Edge value
      * @param right right Edge value
      * @param mazeJFrame the maze frame that will be displayed
      */
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

     /**
      * Get top edge
      * @return topEdge
      */
     public int getTopEdge(){
          return topEdge;
     }

     /**
      * Get left edge
      * @return leftEdge
      */
     public int getLeftEdge(){
          return leftEdge;
     }

     /**
      * get right edge
      *
      * @return rightEdge
      */
     public int getRightEdge(){
          return rightEdge;
     }

     /**
      * get bottom edge
      *
      * @return bottomEdge
      */
     public int getBottomEdge(){
          return bottomEdge;
     }

     /**
      * Return the jcell component
      *
      * @return Jcell
      */
     public JCell getjcell() {
          return jCell;
     }

     /**
      * Get X position
      *
      * @return X_pos
      */
     public int getX_pos(){
          return X_pos;
     }

     /**
      * Get Y position
      *
      * @return Y_pos
      */
     public int getY_pos(){
          return  Y_pos;
     }

     /**
      * Break the targeted cell wall
      *
      * @param target
      */
     public void BreakCellWall(String target){
          //Adding handler at the object
          if (target == "top")
          {
               topEdge=0;

          }
          if (target == "left")
          {
               leftEdge=0;

          }
          if (target == "bottom")
          {
               bottomEdge=0;

          }
          if (target == "right") {
               rightEdge = 0;
          }


          jCell.BreakWall(target);
          jCell.repaint();
     }

     /**
      * Add the selected cell wall
      *
      * @param target wall
      */
     public void AddCellWall(String target){
          //Adding handler at the object
          if (target == "top")
          {
               topEdge=2;

          }
          if (target == "left")
          {
               leftEdge=2;

          }
          if (target == "bottom")
          {
               bottomEdge=2;

          }
          if (target == "right") {
               rightEdge = 2;
          }

          jCell.AddWall(target);
          jCell.repaint();
     }

     /**
      * Check if the cell is reachable
      *
      * @param target target direction
      * @return true/false
      */
     public boolean IsReachable(String target){
          return jCell.CheckWall(target);
     }

     /**
      * Mark traced back
      */
     public void markTracedBack(){
          jCell.setBackground(null);
          jCell.repaint();
     }

     /**
      * Mark cell that "miner" has been to
      */
     public void markTraveled(){
          jCell.setBackground(Color.GREEN);
          jCell.repaint();
     }

     /**
      * Remove mark that "miner" placed
      */
     public void removeMark(){
          jCell.setBackground(null);
          jCell.repaint();
     }

     /**
      * Drop focus on currently selected cell
      */
     public void dropCellFocus(){
          jCell.setBackground(null);
          jCell.repaint();
     }

     /**
      *  Focus on selected cell
      */
     public void gainCellFocus(){
          jCell.setBackground(Color.GREEN);
          jCell.repaint();
     }
}
