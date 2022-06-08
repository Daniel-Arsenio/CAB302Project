/**
 * Author Marcus Nguyen
 */
package Models;

import MazeGUI.MazeJFrame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Maze {
    /**
     * Variable Declaration
     */
    private Cell[][] cells;
    //private MazeJFrame mazeFrame;
    private int cellsGenerated_counter = 0;
    ArrayList<String> MazeGenerate_history = new ArrayList<String>();

    private long delay = 0;

    //Starting position always 0 , 0
    private int X_currentLocation = 0;
    private int Y_currentLocation = 0;

    private int X_Size;
    private int Y_Size;

    private int EdgeSize =10;


    /**
     * Constructor
     *
     * @param X_size X size of the Maze
     * @param Y_size Y size of the Maze
     */
    public Maze(int X_size, int Y_size) {
        this.X_Size = X_size;
        this.Y_Size = Y_size;
        cells = new Cell[X_Size][Y_Size];
        //mazeFrame.setVisible(true);
        //GenerateMaze();
    }
    public Maze(int X_size, int Y_size, MazeJFrame frm){
        this.X_Size = X_size;
        this.Y_Size = Y_size;
        cells = new Cell[X_Size][Y_Size];
    }

    /**
     * Generate the Maze
     */
    public void GenerateMaze(MazeJFrame mazeFrame) {
        for (int x = 0; x < X_Size; x++) {
            for (int y = 0; y < Y_Size; y++) {
                cells[x][y] = new Cell(x * EdgeSize, y * EdgeSize, EdgeSize, EdgeSize, 0, 0, 0, 0);
                mazeFrame.MazePanel.add(cells[x][y].getjcell());
            }
        }
        mazeFrame.repaint();
        int totalUnvisitedCells = X_Size * Y_Size;
        String Next_UnVisitedCell;
        while (!(totalUnvisitedCells == 0))
        {
            Next_UnVisitedCell = getNext_unVisitedCell();

            if ((Next_UnVisitedCell == "")) {
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                }
                if (MazeGenerate_history.size() == 0) {
                    cells[0][0].markTracedBack();
                    totalUnvisitedCells--;
                } else {
                    String history = MazeGenerate_history.get(cellsGenerated_counter - 1);
                    if (history == "top") {
                        cells[X_currentLocation][Y_currentLocation].markTracedBack();
                        cells[X_currentLocation][Y_currentLocation].Visited = true;
                        mazeFrame.repaint();
                        moveDown();
                        MazeGenerate_history.remove(cellsGenerated_counter - 1);
                        cellsGenerated_counter--;
                    }
                    if (history == "bottom") {
                        cells[X_currentLocation][Y_currentLocation].markTracedBack();
                        cells[X_currentLocation][Y_currentLocation].Visited = true;
                        mazeFrame.repaint();
                        moveUp();
                        MazeGenerate_history.remove(cellsGenerated_counter - 1);
                        cellsGenerated_counter--;
                    }
                    if (history == "left") {
                        cells[X_currentLocation][Y_currentLocation].markTracedBack();
                        cells[X_currentLocation][Y_currentLocation].Visited = true;
                        mazeFrame.repaint();
                        moveRight();
                        MazeGenerate_history.remove(cellsGenerated_counter - 1);
                        cellsGenerated_counter--;
                    }
                    if (history == "right") {
                        cells[X_currentLocation][Y_currentLocation].markTracedBack();
                        cells[X_currentLocation][Y_currentLocation].Visited = true;
                        mazeFrame.repaint();
                        moveLeft();
                        MazeGenerate_history.remove(cellsGenerated_counter - 1);
                        cellsGenerated_counter--;
                    }
                }
            } else {
                cells[X_currentLocation][Y_currentLocation].Visited = true;
                totalUnvisitedCells--;
                cellsGenerated_counter++;

                BreakCellsWall(Next_UnVisitedCell);

                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {

                }

                if (Next_UnVisitedCell == "top") {
                    moveUp();
                }
                if (Next_UnVisitedCell == "bottom") {
                    moveDown();
                }
                if (Next_UnVisitedCell == "left") {
                    moveLeft();
                }
                if (Next_UnVisitedCell == "right") {
                    moveRight();
                }
            }
        }
    }

    /**
     * Add the cell movement to the ArrayList for future trace-back
     * @param direction The direction of move
     */
    private void addtoHistory(String direction) {
        if (direction == "top") {
            MazeGenerate_history.add("top");
        }
        if (direction == "left") {
            MazeGenerate_history.add("left");
        }
        if (direction == "right") {
            MazeGenerate_history.add("right");
        }
        if (direction == "bottom") {
            MazeGenerate_history.add("bottom");
        }
    }

    /**
     * This method will return the next RANDOM cell that is not visited. If there is no un-visited cell, it will return ""
     * @return Return the next unvisited cell
     */
    private String getNext_unVisitedCell() {
        ArrayList<String> targetList = new ArrayList<String>();
        //Check left Cell
        if (!(X_currentLocation == 0)) {
            if (!cells[X_currentLocation - 1][Y_currentLocation].Visited) {
                targetList.add("left");
            }
        }
        //Check right Cell
        if (!(X_currentLocation == X_Size - 1)) {
            if (!cells[X_currentLocation + 1][Y_currentLocation].Visited) {
                targetList.add("right");
            }
        }
        //Check top Cell
        if (!(Y_currentLocation == 0)) {
            if (!cells[X_currentLocation][Y_currentLocation - 1].Visited) {
                targetList.add("top");
            }
        }
        //Check bottom Cell
        if (!(Y_currentLocation == Y_Size - 1)) {
            if (!cells[X_currentLocation][Y_currentLocation + 1].Visited) {
                targetList.add("bottom");
            }
        }

        Random r = new Random();

        if (targetList.size() == 0) {
            return "";
        } else {
            int randomitem = r.nextInt(targetList.size());
            String target = targetList.get(randomitem);
            addtoHistory(target);
            return target;
        }

    }

    /**
     * This method will break the wall of 2 cells
     * @param target The target wall to be break
     */
    private void BreakCellsWall(String target) {
        if (target == "top") {
            cells[X_currentLocation][Y_currentLocation].BreakCellWall("top");
            cells[X_currentLocation][Y_currentLocation - 1].BreakCellWall("bottom");
        }
        if (target == "left") {
            cells[X_currentLocation][Y_currentLocation].BreakCellWall("left");
            cells[X_currentLocation - 1][Y_currentLocation].BreakCellWall("right");
        }
        if (target == "bottom") {
            cells[X_currentLocation][Y_currentLocation].BreakCellWall("bottom");
            cells[X_currentLocation][Y_currentLocation + 1].BreakCellWall("top");
        }
        if (target == "right") {
            cells[X_currentLocation][Y_currentLocation].BreakCellWall("right");
            cells[X_currentLocation + 1][Y_currentLocation].BreakCellWall("left");
        }
    }

    /**
     * Move current cell left 1 cell
     */
    private void moveLeft() {
        X_currentLocation--;
    }

    /**
     * Move current cell right 1 cell
     */
    private void moveRight() {
        X_currentLocation++;
    }

    /**
     * Move current cell up 1 cell
     */
    private void moveUp() {
        Y_currentLocation--;
    }

    /**
     * Move current cell down 1 cell
     */
    private void moveDown() {
        Y_currentLocation++;
    }

    public void createImage(JPanel panel, int Width, int Height) {

        BufferedImage bi = new BufferedImage(Width * 50, Height * 50, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bi.createGraphics();
        panel.paintAll(g);
        g.dispose();

        try {
            ImageIO.write(bi, "png", new File("C:\\Users\\kimlo\\IdeaProjects\\MazeGeneratorSolution\\img.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Cell[][] getCells(){
        return this.cells;
    }
}