package src.MazeGUI;

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
    ArrayList<String> TraceBack_List = new ArrayList<String>();

    private long delay = 0;

    //Starting position always 0 , 0
    private int X_currentLocation = 0;
    private int Y_currentLocation = 0;

    private int X_Size;
    private int Y_Size;

    private int EdgeSize = 10;


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

    /**
     * Generate the Maze
     *
     * @param mazeFrame The mazeFrame that this Maze will be displayed to
     */
    public void GenerateMaze(MazeJFrame mazeFrame) {
        for (int x = 0; x < X_Size; x++) {
            for (int y = 0; y < Y_Size; y++) {
                cells[x][y] = new Cell(x * EdgeSize, y * EdgeSize, EdgeSize, EdgeSize, 0, 0, 0, 0);
                mazeFrame.MazePanel.add(cells[x][y].getjcell());
            }
        }
        mazeFrame.repaint();

        X_currentLocation = 0;
        Y_currentLocation = 0;

        TraceBack_List = new ArrayList<String>();

        int totalUnvisitedCells = X_Size * Y_Size;
        String Next_UnVisitedCell;
        while (!(totalUnvisitedCells == 0)) {
            Next_UnVisitedCell = getNext_unVisitedCell();

            if ((Next_UnVisitedCell == "")) {
                if (TraceBack_List.size() == 0) {
                    cells[0][0].markTracedBack();
                    totalUnvisitedCells--;
                } else {
                    String history = TraceBack_List.get(cellsGenerated_counter - 1);
                    if (history == "top") {
                        cells[X_currentLocation][Y_currentLocation].markTracedBack();
                        cells[X_currentLocation][Y_currentLocation].Visited = true;
                        mazeFrame.repaint();
                        moveDown();
                        TraceBack_List.remove(cellsGenerated_counter - 1);
                        cellsGenerated_counter--;
                    }
                    if (history == "bottom") {
                        cells[X_currentLocation][Y_currentLocation].markTracedBack();
                        cells[X_currentLocation][Y_currentLocation].Visited = true;
                        mazeFrame.repaint();
                        moveUp();
                        TraceBack_List.remove(cellsGenerated_counter - 1);
                        cellsGenerated_counter--;
                    }
                    if (history == "left") {
                        cells[X_currentLocation][Y_currentLocation].markTracedBack();
                        cells[X_currentLocation][Y_currentLocation].Visited = true;
                        mazeFrame.repaint();
                        moveRight();
                        TraceBack_List.remove(cellsGenerated_counter - 1);
                        cellsGenerated_counter--;
                    }
                    if (history == "right") {
                        cells[X_currentLocation][Y_currentLocation].markTracedBack();
                        cells[X_currentLocation][Y_currentLocation].Visited = true;
                        mazeFrame.repaint();
                        moveLeft();
                        TraceBack_List.remove(cellsGenerated_counter - 1);
                        cellsGenerated_counter--;
                    }
                }
            } else {
                cells[X_currentLocation][Y_currentLocation].Visited = true;
                totalUnvisitedCells--;
                cellsGenerated_counter++;

                BreakCellsWall(Next_UnVisitedCell);

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
     *
     * @param direction The direction of move
     */
    private void addtoHistory(String direction) {
        if (direction == "top") {
            TraceBack_List.add("top");
        }
        if (direction == "left") {
            TraceBack_List.add("left");
        }
        if (direction == "right") {
            TraceBack_List.add("right");
        }
        if (direction == "bottom") {
            TraceBack_List.add("bottom");
        }
    }

    /**
     * This method will return the next RANDOM cell that is not visited. If there is no un-visited cell, it will return ""
     *
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
     *
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
     * Generate Solution for a Maze as long as there is a valid solution for the Maze (not a closed Maze)
     */
    public void GenerateSolution() {
        setAllCells_unVisisted();
        X_currentLocation=0;Y_currentLocation=0;
        TraceBack_List = new ArrayList<String>();
        String Next_unVisitedCell;
        int cellsTraveledcounter = 0;
        int totalUnvisitedCells = X_Size * Y_Size;
        while (!((X_currentLocation==X_Size-1) && (Y_currentLocation ==Y_Size-1))) {
            //while(!(totalUnvisitedCells==0)){
            Next_unVisitedCell = getNext_Reachable_unvisitedCell();

            if ((Next_unVisitedCell == "")) {

                String history = TraceBack_List.get(cellsTraveledcounter - 1);
                if (history == "top") {
                    cells[X_currentLocation][Y_currentLocation].Visited = true;
                    moveDown();
                    TraceBack_List.remove(cellsTraveledcounter - 1);
                    cellsTraveledcounter--;
                }
                if (history == "bottom") {
                    cells[X_currentLocation][Y_currentLocation].Visited = true;
                    moveUp();
                    TraceBack_List.remove(cellsTraveledcounter - 1);
                    cellsTraveledcounter--;
                }
                if (history == "left") {
                    cells[X_currentLocation][Y_currentLocation].Visited = true;
                    moveRight();
                    TraceBack_List.remove(cellsTraveledcounter - 1);
                    cellsTraveledcounter--;
                }
                if (history == "right") {
                    cells[X_currentLocation][Y_currentLocation].Visited = true;
                    moveLeft();
                    TraceBack_List.remove(cellsTraveledcounter - 1);
                    cellsTraveledcounter--;
                }

            } else {
                cells[X_currentLocation][Y_currentLocation].Visited = true;
                cellsTraveledcounter++;

                if (Next_unVisitedCell == "top") {
                    moveUp();
                }
                if (Next_unVisitedCell == "bottom") {
                    moveDown();
                }
                if (Next_unVisitedCell == "left") {
                    moveLeft();
                }
                if (Next_unVisitedCell == "right") {
                    moveRight();
                }
            }
        }
        X_currentLocation=0;Y_currentLocation=0;
        for (int i=0; i<TraceBack_List.size();i++){
            if (TraceBack_List.get(i) == "top") {
                cells[X_currentLocation][Y_currentLocation].markTraveled();
                moveUp();
            }
            if (TraceBack_List.get(i) == "bottom") {
                cells[X_currentLocation][Y_currentLocation].markTraveled();
                moveDown();
            }
            if (TraceBack_List.get(i) == "left") {
                cells[X_currentLocation][Y_currentLocation].markTraveled();
                moveLeft();
            }
            if (TraceBack_List.get(i) == "right") {
                cells[X_currentLocation][Y_currentLocation].markTraveled();
                moveRight();
            }
        }
        cells[X_Size-1][Y_Size-1].markTraveled();
    }

    /**
     * Get the next Reachable Unvisited Cell
     *
     * @return direction of next Reachable Unvisited Cell
     */
    private String getNext_Reachable_unvisitedCell() {
        ArrayList<String> targetList = new ArrayList<String>();
        //Check left Cell
        if (!(X_currentLocation == 0)) {
            if (!cells[X_currentLocation - 1][Y_currentLocation].Visited && cells[X_currentLocation][Y_currentLocation].IsReachable("left")) {
                targetList.add("left");
            }
        }
        //Check right Cell
        if (!(X_currentLocation == X_Size - 1)) {
            if (!cells[X_currentLocation + 1][Y_currentLocation].Visited && cells[X_currentLocation][Y_currentLocation].IsReachable("right")) {
                targetList.add("right");
            }
        }
        //Check top Cell
        if (!(Y_currentLocation == 0)) {
            if (!cells[X_currentLocation][Y_currentLocation - 1].Visited && cells[X_currentLocation][Y_currentLocation].IsReachable("top")) {
                targetList.add("top");
            }
        }
        //Check bottom Cell
        if (!(Y_currentLocation == Y_Size - 1)) {
            if (!cells[X_currentLocation][Y_currentLocation + 1].Visited && cells[X_currentLocation][Y_currentLocation].IsReachable("bottom")) {
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
     * return all cells Visited state back to false
     */
    private void setAllCells_unVisisted() {
        for (int x = 0; x < X_Size; x++) {
            for (int y = 0; y < Y_Size; y++) {
                cells[x][y].Visited = false;
            }
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


    /**
     * This method will create from a jpanel
     *
     * @param panel the jpanel we want to turn into img
     * @param Width the width of the panel also the width of the img
     * @param Height the height of the panel also the height of the img
     */
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
}

