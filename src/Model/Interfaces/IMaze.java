package src.Model.Interfaces;

import src.MazeGUI.MazeJFrame;

import javax.swing.*;

public interface IMaze {
        void GenerateMaze(MazeJFrame mazeFrame);

        void createImage(JPanel panel, int Width, int Height, String pathname);

        void GenerateSolution(MazeJFrame mazeFrame);

        void BreakCellsWall(String target,int X_Pos, int Y_Pos);

        void AddCellsWall(String target,int X_Pos, int Y_Pos);


}

