package src.Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import src.MazeGUI.MazeJFrame;

import static org.junit.jupiter.api.Assertions.*;

class MazeTest {
    Maze maze;
    @BeforeEach
    void init(){
        maze = new Maze(30,30);
        maze.GenerateMaze(new MazeJFrame(30,30));
    }

    @Test
    void breakCellsWall() {
        maze.BreakCellsWall("right",10,10);
        assertEquals(0,maze.getCell(10,10).getRightEdge());
        assertEquals(0,maze.getCell(11,10).getLeftEdge());

        maze.BreakCellsWall("top",4,10);
        assertEquals(0,maze.getCell(4,10).getTopEdge());
        assertEquals(0,maze.getCell(4,9).getBottomEdge());

        maze.BreakCellsWall("left",22,22);
        assertEquals(0,maze.getCell(22,22).getLeftEdge());
        assertEquals(0,maze.getCell(21,22).getRightEdge());

        maze.BreakCellsWall("bottom",15,10);
        assertEquals(0,maze.getCell(15,10).getBottomEdge());
        assertEquals(0,maze.getCell(15,11).getTopEdge());
    }

    @Test
    void addCellsWall() {
        maze.AddCellsWall("right",10,10);
        assertEquals(2,maze.getCell(10,10).getRightEdge());
        assertEquals(2,maze.getCell(11,10).getLeftEdge());

        maze.AddCellsWall("top",4,10);
        assertEquals(2,maze.getCell(4,10).getTopEdge());
        assertEquals(2,maze.getCell(4,9).getBottomEdge());

        maze.AddCellsWall("left",22,22);
        assertEquals(2,maze.getCell(22,22).getLeftEdge());
        assertEquals(2,maze.getCell(21,22).getRightEdge());

        maze.AddCellsWall("bottom",15,10);
        assertEquals(2,maze.getCell(15,10).getBottomEdge());
        assertEquals(2,maze.getCell(15,11).getTopEdge());
    }

    @Test
    void checkWall() {
        //Adding Cell wall should return false
        maze.AddCellsWall("bottom",15,10);
        assertEquals(false,maze.checkWall(15,10,"bottom"));

        maze.AddCellsWall("bottom",10,10);
        assertEquals(false,maze.checkWall(10,10,"bottom"));

        maze.AddCellsWall("bottom",22,22);
        assertEquals(false,maze.checkWall(22,22,"bottom"));

        //Breaking Cell wall should return true
        maze.BreakCellsWall("bottom",7,7);
        assertEquals(true,maze.checkWall(7,7,"bottom"));

        maze.BreakCellsWall("bottom",19,19);
        assertEquals(true,maze.checkWall(19,19,"bottom"));

        maze.BreakCellsWall("bottom",27,27);
        assertEquals(true,maze.checkWall(27,27,"bottom"));
    }
}