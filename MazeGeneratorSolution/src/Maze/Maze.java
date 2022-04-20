/**
 * Author: Marcus Nguyen
 * Developed for CAB 302 project
 * QUT student 2022
 */
package Maze;

import Components.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Maze {
    /**
     * Variable Declaration
     */
    private Cell[][] cells;
    private MazeJFrame mazeFrame;

    //Starting position always 0 , 0
    private int X_currentLocation = 0;
    private int Y_currentLocation = 0;

    private int X_Size;
    private int Y_Size;

    private int EdgeSize = 50;


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
        mazeFrame = new MazeJFrame(X_size, Y_size);
        mazeFrame.setVisible(true);
        GenerateMaze();
        //createImage(mazeFrame.panel,X_Size,Y_Size);
    }

    private void GenerateMaze() {
        for (int x = 0; x < X_Size; x++) {
            for (int y = 0;y<Y_Size;y++)
            {
                cells[x][y] = new Cell(x*EdgeSize,y*EdgeSize,EdgeSize,EdgeSize,2,2,2,2);
                mazeFrame.panel.add(cells[x][y].getjcell());
                mazeFrame.repaint();
            }
        }
    }

    private void GenerateSolution(){

    }

    /**
     * Move left 1 cell
     */
    private void moveLeft() {
        if (!X_checkBorder()) {
            X_currentLocation--;
        } else return;
    }

    /**
     * Move right 1 cell
     */
    private void moveRight() {
        if (!X_checkBorder()) {
            X_currentLocation++;
        } else return;
    }

    /**
     * Move up 1 cell
     */
    private void moveUp() {
        Y_currentLocation--;
    }

    /**
     * Move down 1 cell
     */
    private void moveDown() {
        Y_currentLocation++;
    }

    /**
     * Check if the current cell is border cell by X Axis
     *
     * @return true
     */
    private boolean X_checkBorder() {
        if (X_currentLocation == 0 || X_currentLocation == X_Size)
            return true;
        return false;
    }

    /**
     * Check if the current cell is border cell by Y Axis
     *
     * @return true
     */
    private boolean Y_checkBorder() {
        if (Y_currentLocation == 0 || X_currentLocation == Y_Size)
            return true;
        return false;
    }

    public void createImage(JPanel panel, int Width, int Height) {

        BufferedImage bi = new BufferedImage(Width*50, Height*50, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bi.createGraphics();
        panel.paintAll(g);
        g.dispose();

        try
        {
            ImageIO.write(bi, "png", new File("E:\\MazeGeneratorSolution"));
        }
        catch ( IOException e)
        {
            e.printStackTrace();
        }
    }
}
