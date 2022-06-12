package src.Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import src.MazeGUI.MazeJFrame;

import static org.junit.jupiter.api.Assertions.*;

class CellTest {
    Cell[][] cell;
    MazeJFrame frame = new MazeJFrame(30,30);
    @BeforeEach
    void  init(){
        cell = new Cell[5][5];
        for (int x=0;x<5;x++){
            for (int y=0;y<5;y++){
                cell[x][y] = new Cell(x,y,5,5,5,2,2,0,0,frame);
            }
        }
    }

    @Test
    void getX_pos() {
        assertEquals(3,cell[3][2].getX_pos());
        assertEquals(1,cell[1][3].getX_pos());
        assertEquals(2,cell[2][4].getX_pos());
        assertEquals(4,cell[4][4].getX_pos());
        assertNotEquals(0,cell[4][4].getX_pos());
    }

    @Test
    void getY_pos() {
        assertEquals(2,cell[3][2].getY_pos());
        assertEquals(3,cell[1][3].getY_pos());
        assertEquals(4,cell[2][4].getY_pos());
        assertEquals(0,cell[4][0].getY_pos());
        assertNotEquals(2,cell[2][4].getY_pos());
    }

    @Test
    void breakCellWall() {
        cell[3][3].BreakCellWall("left");
        assertEquals(0,cell[3][3].getLeftEdge());
        assertEquals(0,cell[2][3].getRightEdge());
    }

    @Test
    void addCellWall() {
        cell[3][3].AddCellWall("left");
        cell[2][3].AddCellWall("right");
        assertEquals(2,cell[3][3].getLeftEdge());
        assertEquals(2,cell[2][3].getRightEdge());
    }

    @Test
    void isReachable() {
        cell[3][3].AddCellWall("left");
        cell[3][3].BreakCellWall("bottom");
        assertEquals(false,cell[2][3].IsReachable("left"));
        assertEquals(true,cell[3][4].IsReachable("bottom"));
    }
}