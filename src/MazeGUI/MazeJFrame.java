package MazeGUI;


import MazeGUI.MazeCreatorComponents.JComponentLibrary;
import Models.Maze;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MazeJFrame extends JFrame {

    /**
     * Variable Declaration
     */
    public JPanel MazePanel, ControllerPanel;
    public JButton btnLeft,btnRight,btnTop,btnBottom,btnClose,btnGenerateMaze,btnBreak,btnAdd;
    public JLabel lblFocused_X,lblFocused_Y;
    private int X_MazeSize;
    private int Y_MazeSize;
    private int CellSize = 30;
    private String breakWallCondition;
    private Maze maze;
    private int X_currentLocation = 0;
    private int Y_currentLocation = 0;
    private boolean editCheck;
    /**
     * Constructor
     *
     * @param X_MazeSize  The X_MazeSize of the frame
     * @param Y_MazeSize The Y_MazeSize of the frame
     */
    public MazeJFrame(int X_MazeSize, int Y_MazeSize) {
        this.X_MazeSize=X_MazeSize;
        this.Y_MazeSize=Y_MazeSize;
        this.setVisible(true);
        this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setTitle("Maze Generator");
        this.setBounds(50, 50, (X_MazeSize+2) * CellSize+ (X_MazeSize*2) + 250 , (Y_MazeSize+2) * CellSize+ (Y_MazeSize*2));
        this.setMinimumSize(new Dimension(1000,800));
        this.getContentPane().setLayout(null);


        MazePanel = JComponentLibrary.CreateJPanel(this,50,50,(X_MazeSize) * CellSize+4,(Y_MazeSize) * CellSize+4,null,true);
        ControllerPanel = JComponentLibrary.CreateJPanel(this,this.getWidth()-300,50,200,this.getHeight()-120,null,true);

        btnTop = JComponentLibrary.CreateButton(ControllerPanel,50,0,100,50,"T",true);
        btnLeft = JComponentLibrary.CreateButton(ControllerPanel,0,50,50,100,"L",true);
        btnRight = JComponentLibrary.CreateButton(ControllerPanel,150,50,50,100,"R",true);
        btnBottom = JComponentLibrary.CreateButton(ControllerPanel,50,150,100,50,"B",true);
        btnClose = JComponentLibrary.CreateButton(ControllerPanel, 0, ControllerPanel.getHeight()-50,200,50,"Close",true);
        btnBreak = JComponentLibrary.CreateButton(ControllerPanel,0,300,100,50,"Break",true);
        btnAdd = JComponentLibrary.CreateButton(ControllerPanel,100,300,100,50,"Add",true);
        btnGenerateMaze = JComponentLibrary.CreateButton(ControllerPanel,0,400,200,50,"Generate Maze",true);
        lblFocused_X=JComponentLibrary.CreateJLabel(ControllerPanel,55,75,"X Location: ",95,15,Color.BLACK,true);
        lblFocused_Y=JComponentLibrary.CreateJLabel(ControllerPanel,55,105,"Y Location: ",95,15,Color.BLACK,true);
        this.repaint();

        btnClose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CloseFrame();
            }
        });

        btnGenerateMaze.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GenerateNewMaze();

            }
        });

        btnAdd.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                editCheck = true;
                System.out.println(editCheck);

            }
        });
        btnBreak.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                editCheck = false;
                System.out.println(editCheck);

            }
        });

        btnBottom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                breakWallCondition= "bottom";
                EditMaze(breakWallCondition,editCheck);

            }
        });
        btnTop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                breakWallCondition= "top";
                EditMaze(breakWallCondition,editCheck);

            }
        });
        btnLeft.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                breakWallCondition= "left";
                EditMaze(breakWallCondition,editCheck);
                System.out.println(editCheck);

            }
        });
        btnRight.addActionListener(new ActionListener() {


            @Override
            public void actionPerformed(ActionEvent e) {
                breakWallCondition= "right";
                EditMaze(breakWallCondition,editCheck);

            }
        });
    }

    private void CloseFrame(){
        this.dispose();
    }

    private void GenerateNewMaze() {
        this.MazePanel.removeAll();
        this.repaint();
        maze = new Maze(X_MazeSize,Y_MazeSize);
        maze.GenerateMaze(this);
        this.X_currentLocation = 0;
        this.Y_currentLocation = 0;
        maze.getCells();
        this.repaint();
    }



    private void EditMaze(String target,Boolean check){
        Models.Cell[][] editcells = this.maze.getCells();
        System.out.println(X_MazeSize);
        System.out.println(Y_MazeSize);
        System.out.println(X_currentLocation);
        System.out.println(Y_currentLocation);
        try{
            if(target == "top" && check == true){
                editcells[X_currentLocation][Y_currentLocation].AddCellWall("top");
                editcells[X_currentLocation][Y_currentLocation -1].AddCellWall("bottom");
                this.MazePanel.repaint();
                Y_currentLocation--;//increment left

            }
            if (target == "top" && check == false) {

                editcells[X_currentLocation][Y_currentLocation].BreakCellWall("top");
                editcells[X_currentLocation][Y_currentLocation - 1].BreakCellWall("bottom");
                this.MazePanel.repaint();
                Y_currentLocation--;// increment up


            }
            if(target == "left" && check == true){
                editcells[X_currentLocation][Y_currentLocation].AddCellWall("left");
                editcells[X_currentLocation - 1][Y_currentLocation].AddCellWall("right");
                this.MazePanel.repaint();
                X_currentLocation--;//increment left

            }
            if (target == "left" && check == false) {
                editcells[X_currentLocation][Y_currentLocation].BreakCellWall("left");
                editcells[X_currentLocation - 1][Y_currentLocation].BreakCellWall("right");
                this.MazePanel.repaint();
                X_currentLocation--;//increment left


            }
            if(target == "bottom" && check == true){
                editcells[X_currentLocation][Y_currentLocation].AddCellWall("bottom");
                editcells[X_currentLocation ][Y_currentLocation + 1].AddCellWall("top");
                this.MazePanel.repaint();
                Y_currentLocation++;//increment left

            }
            if (target == "bottom" && check == false) {
                editcells[X_currentLocation][Y_currentLocation].BreakCellWall("bottom");
                editcells[X_currentLocation][Y_currentLocation + 1].BreakCellWall("top");
                this.MazePanel.repaint();
                Y_currentLocation++;//increment bottom

            }
            if(target == "right" && check == true){
                editcells[X_currentLocation][Y_currentLocation].AddCellWall("right");
                editcells[X_currentLocation + 1][Y_currentLocation].AddCellWall("left");
                this.MazePanel.repaint();
                X_currentLocation++;//increment left

            }
            if (target == "right" && check == false) {
                editcells[X_currentLocation][Y_currentLocation].BreakCellWall("right");
                editcells[X_currentLocation + 1][Y_currentLocation].BreakCellWall("left");
                this.MazePanel.repaint();
                X_currentLocation++;// increment right


            }
            updateLabel();//updates label to coordinates

        }catch(Exception e){
            X_currentLocation = 0;
            Y_currentLocation = 0;
        }



    }

    private void updateLabel(){
        String updateX = String.format("X Location:%s",String.valueOf(X_currentLocation));
        lblFocused_X.setText(updateX);
        String updateY = String.format("Y Location:%s",String.valueOf(Y_currentLocation));
        lblFocused_Y.setText(updateY);

    }






}
