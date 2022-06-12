package src.Model;

import src.MazeGUI.MainGUI;
import src.MazeGUI.MazeJFrame;
import src.Model.Interfaces.IMaze;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Maze implements IMaze {
    /**
     * Variable Declaration
     */
    private Cell[][] cells;
    //private MazeJFrame mazeFrame;
    private int cellsGenerated_counter = 0;
    ArrayList<String> TraceBack_List = new ArrayList<String>();
    //Starting position always 0 , 0
    private int X_currentLocation = 0;
    private int Y_currentLocation = 0;

    private int X_Size;
    private int Y_Size;

    private int EdgeSize = 20;

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
        if(X_Size>=40 && Y_Size>=40){
            EdgeSize = 15;
        }

        if(X_Size>=60 && Y_Size>=60){
            EdgeSize = 12;
        }

        if(X_Size>=80 && Y_Size>=80){
            EdgeSize = 7;
        }
    }

    /**
     * Return the specific cell
     *
     * @param x X position of the cell
     * @param y Y position of the cell
     * @return
     */
    public Cell getCell(int x,int y){
        return cells[x][y];
    }

    /**
     * Return the maze as an array of strings.
     *
     * @return Returns a list of strings representing cells in the form "0202", where 0 is no wall, and 2 is wall. In the form
     * "Right Left Top Bottom"
     * */
    public String[][] asStringList(Cell[][] cells, JPanel mazeFrame){
        String[][] maze = new String[cells.length][cells[0].length];
        for (int i = 0; i < cells.length; i++) {
            for (int x = 0; x < cells[0].length; x++) {
                maze[x][i] = String.valueOf(cells[i][x].getjcell().getBorder().getBorderInsets(mazeFrame).right) +
                        String.valueOf(cells[i][x].getjcell().getBorder().getBorderInsets(mazeFrame).left) +
                        String.valueOf(cells[i][x].getjcell().getBorder().getBorderInsets(mazeFrame).top) +
                        String.valueOf(cells[i][x].getjcell().getBorder().getBorderInsets(mazeFrame).bottom);
            }
        }
        return maze;
    }

    /**
     * Generate the Maze
     *
     * @param mazeFrame The mazeFrame that this Maze will be displayed to
     * @return
     */
    public void GenerateMaze(MazeJFrame mazeFrame) {
        for (int x = 0; x < X_Size; x++) {
            for (int y = 0; y < Y_Size; y++) {
                cells[x][y] = new Cell(x, y, EdgeSize, EdgeSize,EdgeSize, 2, 2, 2, 2 , mazeFrame);
                mazeFrame.mazePanel.add(cells[x][y].getjcell());
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
        cells[0][0].getjcell().BreakWall("left");
        cells[X_Size-1][Y_Size-1].getjcell().BreakWall("right");
        mazeFrame.repaint();
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
     * This method will break the wall of 2 cells
     *
     * @param target The target wall to be break
     */
    public void BreakCellsWall(String target,int X_Pos, int Y_Pos) {
        if (target == "top") {
            cells[X_Pos][Y_Pos].BreakCellWall("top");
            cells[X_Pos][Y_Pos - 1].BreakCellWall("bottom");
        }
        if (target == "left") {
            cells[X_Pos][Y_Pos].BreakCellWall("left");
            cells[X_Pos - 1][Y_Pos].BreakCellWall("right");
        }
        if (target == "bottom") {
            cells[X_Pos][Y_Pos].BreakCellWall("bottom");
            cells[X_Pos][Y_Pos + 1].BreakCellWall("top");
        }
        if (target == "right") {
            cells[X_Pos][Y_Pos].BreakCellWall("right");
            cells[X_Pos + 1][Y_Pos].BreakCellWall("left");
        }
    }

    /**
     * This method will add the wall of 2 cells
     *
     * @param target The target wall to be break
     */
    public void AddCellsWall(String target,int X_Pos, int Y_Pos) {
        if (target == "top") {
            cells[X_Pos][Y_Pos].AddCellWall("top");
            cells[X_Pos][Y_Pos - 1].AddCellWall("bottom");
        }
        if (target == "left") {
            cells[X_Pos][Y_Pos].AddCellWall("left");
            cells[X_Pos - 1][Y_Pos].AddCellWall("right");
        }
        if (target == "bottom") {
            cells[X_Pos][Y_Pos].AddCellWall("bottom");
            cells[X_Pos][Y_Pos + 1].AddCellWall("top");
        }
        if (target == "right") {
            cells[X_Pos][Y_Pos].AddCellWall("right");
            cells[X_Pos + 1][Y_Pos].AddCellWall("left");
        }
    }

    /**
     * Generate Solution for a Maze as long as there is a valid solution for the Maze (not a closed Maze)
     *
     * @param mazeFrame The frame that this Maze will be displayed on
     */
    public void GenerateSolution(MazeJFrame mazeFrame) {
        setAllCells_unVisited();
        X_currentLocation=0;Y_currentLocation=0;
        TraceBack_List = new ArrayList<String>();
        String Next_unVisitedCell;
        boolean noSolution = false;
        int cellsTraveledcounter = 0;
        int totalCells = X_Size * Y_Size;
        int totalCellTraveled=0;

        while (!((X_currentLocation==X_Size-1) && (Y_currentLocation ==Y_Size-1))|| noSolution) {
            Next_unVisitedCell = getNext_Reachable_unvisitedCell();
            if (X_currentLocation==0&&Y_currentLocation==0&&Next_unVisitedCell==""){
                noSolution=true;
                JOptionPane.showMessageDialog(mazeFrame,"No solution found, "+String.format("%.2f",Double.valueOf(Integer.toString(totalCellTraveled))/Double.valueOf(Integer.toString(totalCells))*100)+"% Reachable");
                break;
            }
            if ((Next_unVisitedCell == "")) {

                String history = TraceBack_List.get(cellsTraveledcounter - 1);
                if (history == "top") {
                    cells[X_currentLocation][Y_currentLocation].Visited = true;
                    cells[X_currentLocation][Y_currentLocation].markTraveled();
                    moveDown();
                    TraceBack_List.remove(cellsTraveledcounter - 1);
                    cellsTraveledcounter--;
                }
                if (history == "bottom") {
                    cells[X_currentLocation][Y_currentLocation].Visited = true;
                    cells[X_currentLocation][Y_currentLocation].markTraveled();
                    moveUp();
                    TraceBack_List.remove(cellsTraveledcounter - 1);
                    cellsTraveledcounter--;
                }
                if (history == "left") {
                    cells[X_currentLocation][Y_currentLocation].Visited = true;
                    cells[X_currentLocation][Y_currentLocation].markTraveled();
                    moveRight();
                    TraceBack_List.remove(cellsTraveledcounter - 1);
                    cellsTraveledcounter--;
                }
                if (history == "right") {
                    cells[X_currentLocation][Y_currentLocation].Visited = true;
                    cells[X_currentLocation][Y_currentLocation].markTraveled();
                    moveLeft();
                    TraceBack_List.remove(cellsTraveledcounter - 1);
                    cellsTraveledcounter--;
                }

            } else {
                cells[X_currentLocation][Y_currentLocation].Visited = true;
                cellsTraveledcounter++;
                totalCellTraveled++;

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

        if (!noSolution){
            removeAllMark();
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
    private void setAllCells_unVisited() {
        for (int x = 0; x < X_Size; x++) {
            for (int y = 0; y < Y_Size; y++) {
                cells[x][y].Visited = false;
            }
        }
    }

    public boolean checkWall(int X_pos,int Y_pos,String target){
        return cells[X_pos][Y_pos].getjcell().CheckWall(target);
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
     * Remove marked color for all cells
     */
    public void removeAllMark(){
        for (int x = 0; x < X_Size; x++) {
            for (int y = 0; y < Y_Size; y++) {
                cells[x][y].removeMark();
            }
        }
    }

    /**
     * Drop focus on selected cell
     *
     * @param X_Position
     * @param Y_Position
     */
    public void dropCellFocus(int X_Position, int Y_Position){
        cells[X_Position][Y_Position].dropCellFocus();
    }

    /**
     * Gain focus on selected cell
     *
     * @param X_Position
     * @param Y_Position
     */
    public void gainCellFocus(int X_Position, int Y_Position){
        cells[X_Position][Y_Position].gainCellFocus();
    }

    /**
     * This method will create from a jpanel
     *
     * @param panel the jpanel we want to turn into img
     * @param Width the width of the maze also the width of the img
     * @param Height the height of the maze also the height of the img
     */
    public void createImage(JPanel panel, int Width, int Height, String pathname) {
        BufferedImage bi = new BufferedImage(Width * EdgeSize+10, Height * EdgeSize+10, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bi.createGraphics();
        panel.paintAll(g);
        g.dispose();
        String currentTime = Long.toString(System.currentTimeMillis());
        try {
            ImageIO.write(bi, "png", new File(pathname+"\\"+currentTime+".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Wrap the maze around the image
     *
     * @param X_pos focused X
     * @param Y_pos focused Y
     * @param imageSize The size of image
     */
    public void Wrap_around_image(int X_pos,int Y_pos, int imageSize){
        for (int x = X_pos;x <(X_pos+imageSize);x++){
            for (int y = Y_pos;y <(Y_pos+imageSize);y++){
                AddCellsWall("top",x,y);
                AddCellsWall("left",x,y);
                AddCellsWall("right",x,y);
                AddCellsWall("bottom",x,y);
                cells[x][y].getjcell().setVisible(false);
            }
        }
    }

    /**
     * Saves maze to database
     *
     * @param mazeframe frame displaying from
     * @param mazeName name of maze being saved
     */
    public void saveMaze(MazeJFrame mazeframe, String mazeName){
        MainGUI.database.addMaze(asStringList(cells, mazeframe.controllerPanel), mazeName, X_Size, Y_Size, MainGUI.currentUser);
    }

    /**
     * Load a selected maze from the database
     *
     * @param mazeid ID of maze being loaded
     * @param frame frame on which to load maze
     * @param xsize x size of frame on which to load maze
     * @param ysize y size of rame on which to load maze
     */
    public void loadMaze(int mazeid, MazeJFrame frame, int xsize, int ysize) {
        String[][] mazestr = MainGUI.database.getMazeCells(mazeid, xsize, ysize);
        for (int x = 0; x< X_Size; x++){
            for(int y = 0; y< Y_Size; y++){
                cells[x][y] = new Cell(x, y, EdgeSize, EdgeSize, EdgeSize,
                        Integer.parseInt(String.valueOf(mazestr[y][x].toCharArray()[2])),
                        Integer.parseInt(String.valueOf(mazestr[y][x].toCharArray()[1])),
                        Integer.parseInt(String.valueOf(mazestr[y][x].toCharArray()[3])),
                        Integer.parseInt(String.valueOf(mazestr[y][x].toCharArray()[0])),
                        frame);
            }
        }
        for (int x = 0; x < Y_Size; x++ ){
            for(int y=0; y < X_Size; y++){
                frame.mazePanel.add(cells[x][y].getjcell());
            }
        }
        frame.mazePanel.setVisible(true);
        frame.mazePanel.repaint();
    }

    /**
     * Save the edited changes of a maze
     *
     * @param mazeframe frame of maze being edited
     * @param mazeId ID of maze being edited
     */
    public void saveEdit(MazeJFrame mazeframe, int mazeId) {
        MainGUI.database.editMaze(asStringList(cells, mazeframe.controllerPanel), mazeId);
    }
}