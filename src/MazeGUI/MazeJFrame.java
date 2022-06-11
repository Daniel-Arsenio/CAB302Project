/**
 * Author: Marcus Nguyen
 */
package src.MazeGUI;

import src.MazeGUI.MazeCreatorComponents.JComponentLibrary;



import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MazeJFrame extends JFrame {

    /**
     * Variable Declaration
     */
    public JPanel MazePanel, ControllerPanel;
    public JButton btnLeft,btnRight,btnTop,btnBottom,btnClose,btnGenerateMaze,btnGenerateSolution,btnAddImage, btnSave, btnLoad,btnExportMaze, btnBack, btnSaveMaze;
    public JLabel lblFocused_X,lblFocused_Y,lblImageSize;
    public JTextField tfImageSize;
    int X_MazeSize;
    int Y_MazeSize;
    private int EdgeSize = 20;
    private Maze maze;
    private boolean solution = false;
    public int focused_X=0;
    public int focused_Y=0;
    private final JTextField mazeName = new JTextField();
    /**
     * Constructor
     *
     * @param X_MazeSize  The X_MazeSize of the frame
     * @param Y_MazeSize The Y_MazeSize of the frame
     */
    public MazeJFrame(int X_MazeSize, int Y_MazeSize) {
        if(X_MazeSize>=40 && Y_MazeSize>=40){
            EdgeSize = 15;
        }

        if(X_MazeSize>=60 && Y_MazeSize>=60){
            EdgeSize = 10;
        }

        if(X_MazeSize>=80 && Y_MazeSize>=80){
            EdgeSize = 7;
        }
        this.X_MazeSize=X_MazeSize;
        this.Y_MazeSize=Y_MazeSize;
        this.setVisible(true);
        this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setTitle("Maze Generator");
        this.setBounds(0, 0, (X_MazeSize+2) * EdgeSize + (X_MazeSize*2) + 250 , (Y_MazeSize+2) * EdgeSize + (Y_MazeSize*2));
        this.setMinimumSize(new Dimension(1000,800));
        this.getContentPane().setLayout(null);


        MazePanel = JComponentLibrary.CreateJPanel(this,50,50,(X_MazeSize) * EdgeSize +4,(Y_MazeSize) * EdgeSize +4,null,true);
        ControllerPanel = JComponentLibrary.CreateJPanel(this,this.getWidth()-300,50,200,this.getHeight()-120,null,true);

        btnTop = JComponentLibrary.CreateButton(ControllerPanel,50,0,100,50,"T",true);
        btnLeft = JComponentLibrary.CreateButton(ControllerPanel,0,50,50,100,"L",true);
        btnRight = JComponentLibrary.CreateButton(ControllerPanel,150,50,50,100,"R",true);
        btnBottom = JComponentLibrary.CreateButton(ControllerPanel,50,150,100,50,"B",true);
        btnBack = JComponentLibrary.CreateButton(ControllerPanel, 0, ControllerPanel.getHeight()-50,200,50,"Back",true);
        btnGenerateMaze = JComponentLibrary.CreateButton(ControllerPanel,0,250,200,50,"Generate Maze",true);
        btnGenerateSolution = JComponentLibrary.CreateButton(ControllerPanel,0,300,200,50,"Generate Solution",false);
        btnSaveMaze = JComponentLibrary.CreateButton(ControllerPanel,0,430,200,50,"Save Maze",true);
        tfImageSize=JComponentLibrary.CreateTextField(ControllerPanel,100,400,100,25,null,false);

        btnExportMaze = JComponentLibrary.CreateButton(ControllerPanel,0,530,200,50,"Export Maze to image",false);

        btnAddImage = JComponentLibrary.CreateButton(ControllerPanel,0,350,200,50,"Add Image",false);
        btnLoad = JComponentLibrary.CreateButton(ControllerPanel,0,480,200,50,"Load Maze",false);

        lblFocused_X=JComponentLibrary.CreateJLabel(ControllerPanel,55,75,"Focused X:",95,15,Color.BLACK,true);
        lblFocused_Y=JComponentLibrary.CreateJLabel(ControllerPanel,55,105,"Focused Y:",95,15,Color.BLACK,true);
        lblImageSize=JComponentLibrary.CreateJLabel(ControllerPanel,0,400,"Image Size:",95,15,Color.BLACK,false);
        this.repaint();

        btnExportMaze.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jFileChooser = new JFileChooser();
                jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int res = jFileChooser.showSaveDialog(null);
                if(res == JFileChooser.APPROVE_OPTION){
                    File selFile = jFileChooser.getSelectedFile();
                    String path = selFile.getAbsolutePath();
                    maze.createImage(MazePanel,X_MazeSize,Y_MazeSize,path);
                }
            }
        });

        btnLeft.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!(focused_X==0)){
                    if((maze.checkWall(focused_X,focused_Y,"left"))){
                        maze.AddCellsWall("left",focused_X,focused_Y);
                    }
                    else{
                        maze.BreakCellsWall("left",focused_X,focused_Y);
                    }
                }
            }
        });

        btnRight.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!(focused_X==X_MazeSize-1)){
                    if((maze.checkWall(focused_X,focused_Y,"right"))){
                        maze.AddCellsWall("right",focused_X,focused_Y);
                    }
                    else{
                        maze.BreakCellsWall("right",focused_X,focused_Y);
                    }
                }
            }
        });

        btnTop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!(focused_Y==0)){
                    if((maze.checkWall(focused_X,focused_Y,"top"))){
                        maze.AddCellsWall("top",focused_X,focused_Y);
                    }
                    else{
                        maze.BreakCellsWall("top",focused_X,focused_Y);
                    }
                }
            }
        });

        btnBottom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!(focused_Y==Y_MazeSize-1)){
                    if((maze.checkWall(focused_X,focused_Y,"bottom"))){
                        maze.AddCellsWall("bottom",focused_X,focused_Y);
                    }
                    else{
                        maze.BreakCellsWall("bottom",focused_X,focused_Y);
                    }
                }
            }
        });

        btnAddImage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    JFileChooser jFileChooser = new JFileChooser();
                    FileNameExtensionFilter filter = new FileNameExtensionFilter("*.Images","png","jpg");
                    jFileChooser.addChoosableFileFilter(filter);
                    JPanel imagePanel = new JPanel();
                    imagePanel.setLayout(null);
                    int res = jFileChooser.showSaveDialog(null);

                    JLabel image = new JLabel();

                    imagePanel.add(image);
                    imagePanel.setBounds(EdgeSize*focused_X,EdgeSize*focused_Y,EdgeSize*Integer.parseInt(tfImageSize.getText()),EdgeSize*Integer.parseInt(tfImageSize.getText()));
                    if(res == JFileChooser.APPROVE_OPTION){
                        File selFile = jFileChooser.getSelectedFile();
                        String path = selFile.getAbsolutePath();
                        image.setIcon(resize(path));
                    }

                    image.setBounds(0,0,EdgeSize*Integer.parseInt(tfImageSize.getText()),EdgeSize*Integer.parseInt(tfImageSize.getText()));
                    maze.Wrap_around_image(focused_X,focused_Y,Integer.parseInt(tfImageSize.getText()));
                    MazePanel.add(imagePanel);
                    MazePanel.repaint();
                }
                catch (Exception exception){
                    JOptionPane.showMessageDialog(null,"Size of the image is required and must be a number");
                }

            }
        });

        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MazeCLandingWindow.mazeListTable.setModel(MainGUI.database.getMazeTableModel());
                CloseFrame();
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
                MainGUI.database.addMaze(maze.asStringList(maze.cells, ControllerPanel), mazeName.getText(), X_MazeSize, Y_MazeSize);
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

    private void CloseFrame(){
        this.dispose();
    }

    private void GenerateNewMaze() {
        this.MazePanel.removeAll();
        this.repaint();
        maze = new Maze(X_MazeSize,Y_MazeSize);
        maze.GenerateMaze(this);
        btnGenerateSolution.setVisible(true);
        btnLoad.setVisible(true);
        btnAddImage.setVisible(true);
        btnExportMaze.setVisible(true);
        //btnSave.setVisible(true);
        tfImageSize.setVisible(true);
        lblImageSize.setVisible(true);
        this.repaint();
    }
    void reGenerateMaze(int mazeid){
        this.MazePanel.removeAll();
        this.repaint();
        maze = new Maze(X_MazeSize,Y_MazeSize);
        Cell[][] cells = MainGUI.database.getMazeCells(mazeid, this.X_MazeSize, this.Y_MazeSize);
        for (int i = 0; i < Y_MazeSize; i++ ){
            for(int j=0; j < X_MazeSize; j++){
                this.MazePanel.add(cells[i][j].getjcell());
            }
        }
        btnGenerateSolution.setVisible(true);
        btnLoad.setVisible(true);
        btnAddImage.setVisible(true);
        btnExportMaze.setVisible(true);
        tfImageSize.setVisible(true);
        lblImageSize.setVisible(true);
        this.repaint();
    }

    private void GenerateSolution(){
        maze.GenerateSolution(this);
        this.repaint();
    }

    public void changeFocus(int X_Position, int Y_Position){
        maze.dropCellFocus(focused_X,focused_Y);

        focused_X=X_Position;focused_Y=Y_Position;

        maze.gainCellFocus(focused_X,focused_Y);
    }

    public ImageIcon resize(String imgPath)
    {
        ImageIcon path = new ImageIcon(imgPath);
        Image img = path.getImage();
        Image newImg = img.getScaledInstance(EdgeSize*Integer.parseInt(tfImageSize.getText()),EdgeSize*Integer.parseInt(tfImageSize.getText()) , Image.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(newImg);
        return image;
    }
}
