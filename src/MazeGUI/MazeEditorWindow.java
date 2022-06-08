/**
 * Author: Marcus Nguyen
 * Developed for CAB 302 project
 * QUT student 2022
 */
package src.MazeGUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

class MazeEditorWindow {
    /**
     * Variable Declaration
     */
    private Cell[][] cells;
    MazeJFrame mazeFrame;
    private int[] X_CellHistory;
    private int[] Y_CellHistory;
    private int root;

    //Starting position always 0 , 0
    private final int X_currentLocation = 10;
    private final int Y_currentLocation = 10;

    private final int X_Size;
    private final int Y_Size;

    private final int EdgeSize = 50;

    /**
     * Constructor
     *
     * @param X_size X size of the Maze
     * @param Y_size Y size of the Maze
     */

    public MazeEditorWindow(int X_size, int Y_size) {
        this.X_Size = X_size;
        this.Y_Size = Y_size;
        cells = new Cell[X_Size][Y_Size];
        mazeFrame = new MazeJFrame(X_size, Y_size);
        mazeFrame.setVisible(false);
        GenerateMaze();
        createImage(mazeFrame.MazePanel,X_Size,Y_Size);
    }

    private void GenerateMaze() {
        for (int x = 0; x < X_Size; x++) {
            for (int y = 0; y < Y_Size; y++) {
                this.cells[x][y] = new Cell(x * EdgeSize, y * EdgeSize, EdgeSize, EdgeSize, 2, 2, 2, 2);
                mazeFrame.MazePanel.add(cells[x][y].getjcell());
            }
        }
        mazeFrame.repaint();
    }

    public void createImage(JPanel panel, int Width, int Height) {

        BufferedImage bi = new BufferedImage(Width*50, Height*50, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bi.createGraphics();
        panel.paintAll(g);
        g.dispose();

        try
        {
            ImageIO.write(bi, "png", new File("img.png"));
        }
        catch ( IOException e)
        {
            e.printStackTrace();
        }
    }
}
