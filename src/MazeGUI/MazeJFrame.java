/**
 * Author: Marcus Nguyen
 */
package src.MazeGUI;

import src.MazeGUI.MazeCreatorComponents.JComponentLibrary;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MazeJFrame extends JFrame {

    /**
     * Variable Declaration
     */
    public JPanel MazePanel, ControllerPanel;
    public JButton btnLeft,btnRight,btnTop,btnBottom,btnBack,btnGenerateMaze,btnGenerateSolution, btnSaveMaze;
    public JLabel lblFocused_X,lblFocused_Y;
    private final JTextField mazeName = new JTextField();
    private int X_MazeSize;
    private int Y_MazeSize;
    private int EdgeSize = 20;
    private Maze maze;
    private boolean solution = false;
    /**
     * Constructor
     *
     * @param X_MazeSize  The X_MazeSize of the frame
     * @param Y_MazeSize The Y_MazeSize of the frame
     */
    public MazeJFrame(int X_MazeSize, int Y_MazeSize) {
        if(X_MazeSize>40 && Y_MazeSize>40){
            EdgeSize = 15;
        }

        if(X_MazeSize>60 && Y_MazeSize>60){
            EdgeSize = 10;
        }

        if(X_MazeSize>80 && Y_MazeSize>80){
            EdgeSize = 7;
        }
        this.X_MazeSize=X_MazeSize;
        this.Y_MazeSize=Y_MazeSize;
        this.setVisible(true);
        this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setTitle("Maze Generator");
        this.setBounds(50, 50, (X_MazeSize+2) * EdgeSize + (X_MazeSize*2) + 250 , (Y_MazeSize+2) * EdgeSize + (Y_MazeSize*2));
        this.setMinimumSize(new Dimension(1000,800));
        this.getContentPane().setLayout(null);


        MazePanel = JComponentLibrary.CreateJPanel(this,50,50,(X_MazeSize) * EdgeSize +4,(Y_MazeSize) * EdgeSize +4,null,true);
        ControllerPanel = JComponentLibrary.CreateJPanel(this,this.getWidth()-300,50,200,this.getHeight()-120,null,true);

        btnTop = JComponentLibrary.CreateButton(ControllerPanel,50,0,100,50,"T",true);
        btnLeft = JComponentLibrary.CreateButton(ControllerPanel,0,50,50,100,"L",true);
        btnRight = JComponentLibrary.CreateButton(ControllerPanel,150,50,50,100,"R",true);
        btnBottom = JComponentLibrary.CreateButton(ControllerPanel,50,150,100,50,"B",true);
        btnBack = JComponentLibrary.CreateButton(ControllerPanel, 0, ControllerPanel.getHeight()-50,200,50,"Back",true);
        btnGenerateMaze = JComponentLibrary.CreateButton(ControllerPanel,0,300,200,50,"Generate Maze",true);
        btnGenerateSolution = JComponentLibrary.CreateButton(ControllerPanel,0,360,200,50,"Generate Solution",false);
        btnSaveMaze = JComponentLibrary.CreateButton(ControllerPanel,0,420,200,50,"Save Maze",true);

        lblFocused_X=JComponentLibrary.CreateJLabel(ControllerPanel,55,75,"Focused X:",95,15,Color.BLACK,true);
        lblFocused_Y=JComponentLibrary.CreateJLabel(ControllerPanel,55,105,"Focused Y:",95,15,Color.BLACK,true);
        this.repaint();

        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainGUI.closeMazeEdit();
                MazeCLandingWindow.mazeListTable.setModel(MainGUI.database.getMazeTableModel());
                MainGUI.openMazeC();
            }
        });

        btnGenerateMaze.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GenerateNewMaze();
            }
        });

        btnSaveMaze.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int choice = JOptionPane.showOptionDialog(MazePanel,new Object[]{"Insert Maze Name:",mazeName},"Save Maze"
                        ,JOptionPane.OK_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,null,null);

                MainGUI.database.addMaze(maze.asStringList(maze.cells, ControllerPanel), mazeName.getText());
                mazeName.setText("");
            }
        });

        btnGenerateSolution.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!solution){
                    GenerateSolution();
                    solution=true;
                }else {
                    maze.removeAllMark();
                    MazePanel.repaint();
                    solution=false;
                }
            }
        });
    }

    private void GenerateNewMaze() {
        this.MazePanel.removeAll();
        this.repaint();
        maze = new Maze(X_MazeSize,Y_MazeSize);
        maze.GenerateMaze(this);
        btnGenerateSolution.setVisible(true);
        this.repaint();
    }

    private void GenerateSolution(){
        maze.GenerateSolution(this);
        this.repaint();
    }
}
