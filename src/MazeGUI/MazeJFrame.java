package src.MazeGUI;

import src.MazeGUI.MazeCreatorComponents.JComponentLibrary;
import src.Model.Maze;
import src.Model.Cell;


import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class MazeJFrame extends JFrame {

    /**
     * Variable Declaration
     */
    public JPanel mazePanel, controllerPanel;
    public JButton btnLeft,btnRight,btnTop,btnBottom,btnGenerateMaze,btnGenerateSolution,btnAddImage,btnExportMaze, btnBack, btnSaveMaze;
    public JLabel lblFocused_X,lblFocused_Y,lblImageSize;
    public JTextField tfImageSize;
    int xMazeSize;
    int yMazeSize;
    private int edgeSize = 20;
    private Maze maze;
    private boolean solution = false;
    public int focusedX =0;
    public int focusedY =0;
    private final JTextField mazeName = new JTextField();

    /**
     * Constructor
     *
     * @param X_MazeSize  The X_MazeSize of the frame
     * @param Y_MazeSize The Y_MazeSize of the frame
     */
    public MazeJFrame(int X_MazeSize, int Y_MazeSize) {
        if(X_MazeSize>=40 && Y_MazeSize>=40){
            edgeSize = 15;
        }

        if(X_MazeSize>=60 && Y_MazeSize>=60){
            edgeSize = 10;
        }

        if(X_MazeSize>=80 && Y_MazeSize>=80){
            edgeSize = 7;
        }
        this.xMazeSize =X_MazeSize;
        this.yMazeSize =Y_MazeSize;
        this.setVisible(true);
        this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setTitle("Maze Generator");
        this.setBounds(0, 0, (X_MazeSize+2) * edgeSize + (X_MazeSize*2) + 250 , (Y_MazeSize+2) * edgeSize + (Y_MazeSize*2));
        this.setMinimumSize(new Dimension(1000,800));
        this.getContentPane().setLayout(null);


        mazePanel = JComponentLibrary.CreateJPanel(this,50,50,(X_MazeSize) * edgeSize +4,(Y_MazeSize) * edgeSize +4,null,true);
        controllerPanel = JComponentLibrary.CreateJPanel(this,this.getWidth()-300,50,200,this.getHeight()-120,null,true);

        btnTop = JComponentLibrary.CreateButton(controllerPanel,50,0,100,50,"T",true);
        btnLeft = JComponentLibrary.CreateButton(controllerPanel,0,50,50,100,"L",true);
        btnRight = JComponentLibrary.CreateButton(controllerPanel,150,50,50,100,"R",true);
        btnBottom = JComponentLibrary.CreateButton(controllerPanel,50,150,100,50,"B",true);
        btnBack = JComponentLibrary.CreateButton(controllerPanel, 0, controllerPanel.getHeight()-50,200,50,"Back",true);
        btnGenerateMaze = JComponentLibrary.CreateButton(controllerPanel,0,250,200,50,"Generate Maze",true);
        btnGenerateSolution = JComponentLibrary.CreateButton(controllerPanel,0,300,200,50,"Generate Solution",false);
        btnSaveMaze = JComponentLibrary.CreateButton(controllerPanel,0,430,200,50,"Save Maze",false);
        tfImageSize=JComponentLibrary.CreateTextField(controllerPanel,100,400,100,25,null,false);

        btnExportMaze = JComponentLibrary.CreateButton(controllerPanel,0,530,200,50,"Export Maze to image",false);

        btnAddImage = JComponentLibrary.CreateButton(controllerPanel,0,350,200,50,"Add Image",false);

        lblFocused_X=JComponentLibrary.CreateJLabel(controllerPanel,55,75,"Focused X:",95,15,Color.BLACK,true);
        lblFocused_Y=JComponentLibrary.CreateJLabel(controllerPanel,55,105,"Focused Y:",95,15,Color.BLACK,true);
        lblImageSize=JComponentLibrary.CreateJLabel(controllerPanel,0,400,"Image Size:",95,15,Color.BLACK,false);
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
                    maze.createImage(mazePanel,X_MazeSize,Y_MazeSize,path);
                }
            }
        });

        btnLeft.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!(focusedX ==0)){
                    if((maze.checkWall(focusedX, focusedY,"left"))){
                        maze.AddCellsWall("left", focusedX, focusedY);
                    }
                    else{
                        maze.BreakCellsWall("left", focusedX, focusedY);
                    }
                }
            }
        });

        btnRight.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!(focusedX ==X_MazeSize-1)){
                    if((maze.checkWall(focusedX, focusedY,"right"))){
                        maze.AddCellsWall("right", focusedX, focusedY);
                    }
                    else{
                        maze.BreakCellsWall("right", focusedX, focusedY);
                    }
                }
            }
        });

        btnTop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!(focusedY ==0)){
                    if((maze.checkWall(focusedX, focusedY,"top"))){
                        maze.AddCellsWall("top", focusedX, focusedY);
                    }
                    else{
                        maze.BreakCellsWall("top", focusedX, focusedY);
                    }
                }
            }
        });

        btnBottom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!(focusedY ==Y_MazeSize-1)){
                    if((maze.checkWall(focusedX, focusedY,"bottom"))){
                        maze.AddCellsWall("bottom", focusedX, focusedY);
                    }
                    else{
                        maze.BreakCellsWall("bottom", focusedX, focusedY);
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
                    imagePanel.setBounds(edgeSize * focusedX, edgeSize * focusedY, edgeSize *Integer.parseInt(tfImageSize.getText()), edgeSize *Integer.parseInt(tfImageSize.getText()));
                    if(res == JFileChooser.APPROVE_OPTION){
                        File selFile = jFileChooser.getSelectedFile();
                        String path = selFile.getAbsolutePath();
                        image.setIcon(resize(path));
                    }

                    image.setBounds(0,0, edgeSize *Integer.parseInt(tfImageSize.getText()), edgeSize *Integer.parseInt(tfImageSize.getText()));
                    maze.Wrap_around_image(focusedX, focusedY,Integer.parseInt(tfImageSize.getText()));
                    mazePanel.add(imagePanel);
                    mazePanel.repaint();
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
                closeFrame();
                MainGUI.openMazeC();
            }
        });

        btnGenerateMaze.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateNewMaze();
            }
        });

        btnSaveMaze.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int choice = JOptionPane.showOptionDialog(mazePanel,new Object[]{"Insert Maze Name:",mazeName},"Save Maze"
                        ,JOptionPane.OK_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,null,null);
                saveMaze();
            }
        });

        btnGenerateSolution.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!solution){
                    generateSolution();
                    solution=true;
                }else {
                    maze.removeAllMark();
                    mazePanel.repaint();
                    solution=false;
                }
            }
        });
    }

    /**
     * Constructor
     *
     * @param X_MazeSize frame size x
     * @param Y_MazeSize frame size y
     * @param mazeID ID of maze
     */
    public MazeJFrame(int X_MazeSize, int Y_MazeSize, int mazeID) {
        if(X_MazeSize>=40 && Y_MazeSize>=40){
            edgeSize = 15;
        }

        if(X_MazeSize>=60 && Y_MazeSize>=60){
            edgeSize = 10;
        }

        if(X_MazeSize>=80 && Y_MazeSize>=80){
            edgeSize = 7;
        }
        this.xMazeSize =X_MazeSize;
        this.yMazeSize =Y_MazeSize;
        this.setVisible(true);
        this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setTitle("Maze Generator");
        this.setBounds(0, 0, (X_MazeSize+2) * edgeSize + (X_MazeSize*2) + 250 , (Y_MazeSize+2) * edgeSize + (Y_MazeSize*2));
        this.setMinimumSize(new Dimension(1000,800));
        this.getContentPane().setLayout(null);


        mazePanel = JComponentLibrary.CreateJPanel(this,50,50,(X_MazeSize) * edgeSize +4,(Y_MazeSize) * edgeSize +4,null,true);
        controllerPanel = JComponentLibrary.CreateJPanel(this,this.getWidth()-300,50,200,this.getHeight()-120,null,true);

        btnTop = JComponentLibrary.CreateButton(controllerPanel,50,0,100,50,"T",true);
        btnLeft = JComponentLibrary.CreateButton(controllerPanel,0,50,50,100,"L",true);
        btnRight = JComponentLibrary.CreateButton(controllerPanel,150,50,50,100,"R",true);
        btnBottom = JComponentLibrary.CreateButton(controllerPanel,50,150,100,50,"B",true);
        btnBack = JComponentLibrary.CreateButton(controllerPanel, 0, controllerPanel.getHeight()-50,200,50,"Back",true);
        btnGenerateMaze = JComponentLibrary.CreateButton(controllerPanel,0,250,200,50,"Generate Maze",true);
        btnGenerateSolution = JComponentLibrary.CreateButton(controllerPanel,0,300,200,50,"Generate Solution",false);
        btnSaveMaze = JComponentLibrary.CreateButton(controllerPanel,0,430,200,50,"Save Maze",false);
        tfImageSize=JComponentLibrary.CreateTextField(controllerPanel,100,400,100,25,null,false);

        btnExportMaze = JComponentLibrary.CreateButton(controllerPanel,0,530,200,50,"Export Maze to image",false);

        btnAddImage = JComponentLibrary.CreateButton(controllerPanel,0,350,200,50,"Add Image",false);

        lblFocused_X=JComponentLibrary.CreateJLabel(controllerPanel,55,75,"Focused X:",95,15,Color.BLACK,true);
        lblFocused_Y=JComponentLibrary.CreateJLabel(controllerPanel,55,105,"Focused Y:",95,15,Color.BLACK,true);
        lblImageSize=JComponentLibrary.CreateJLabel(controllerPanel,0,400,"Image Size:",95,15,Color.BLACK,false);
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
                    maze.createImage(mazePanel,X_MazeSize,Y_MazeSize,path);
                }
            }
        });

        btnLeft.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!(focusedX ==0)){
                    if((maze.checkWall(focusedX, focusedY,"left"))){
                        maze.AddCellsWall("left", focusedX, focusedY);
                    }
                    else{
                        maze.BreakCellsWall("left", focusedX, focusedY);
                    }
                }
            }
        });

        btnRight.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!(focusedX ==X_MazeSize-1)){
                    if((maze.checkWall(focusedX, focusedY,"right"))){
                        maze.AddCellsWall("right", focusedX, focusedY);
                    }
                    else{
                        maze.BreakCellsWall("right", focusedX, focusedY);
                    }
                }
            }
        });

        btnTop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!(focusedY ==0)){
                    if((maze.checkWall(focusedX, focusedY,"top"))){
                        maze.AddCellsWall("top", focusedX, focusedY);
                    }
                    else{
                        maze.BreakCellsWall("top", focusedX, focusedY);
                    }
                }
            }
        });

        btnBottom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!(focusedY ==Y_MazeSize-1)){
                    if((maze.checkWall(focusedX, focusedY,"bottom"))){
                        maze.AddCellsWall("bottom", focusedX, focusedY);
                    }
                    else{
                        maze.BreakCellsWall("bottom", focusedX, focusedY);
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
                    imagePanel.setBounds(edgeSize * focusedX, edgeSize * focusedY, edgeSize *Integer.parseInt(tfImageSize.getText()), edgeSize *Integer.parseInt(tfImageSize.getText()));
                    if(res == JFileChooser.APPROVE_OPTION){
                        File selFile = jFileChooser.getSelectedFile();
                        String path = selFile.getAbsolutePath();
                        image.setIcon(resize(path));
                    }

                    image.setBounds(0,0, edgeSize *Integer.parseInt(tfImageSize.getText()), edgeSize *Integer.parseInt(tfImageSize.getText()));
                    maze.Wrap_around_image(focusedX, focusedY,Integer.parseInt(tfImageSize.getText()));
                    mazePanel.add(imagePanel);
                    mazePanel.repaint();
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
                closeFrame();
            }
        });

        btnGenerateMaze.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateNewMaze();
            }
        });

        btnSaveMaze.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editMaze(mazeID);
            }
        });

        btnGenerateSolution.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!solution){
                    generateSolution();
                    solution=true;
                }else {
                    maze.removeAllMark();
                    mazePanel.repaint();
                    solution=false;
                }
            }
        });
        loadMaze(mazeID, X_MazeSize, Y_MazeSize);
    }

    /**
     * Close this frame
     */
    private void closeFrame(){
        this.dispose();
    }

    /**
     * Generate new maze
     */
    private void generateNewMaze() {
        this.mazePanel.removeAll();
        this.repaint();
        maze = new Maze(xMazeSize, yMazeSize);
        maze.GenerateMaze(this);
        btnGenerateSolution.setVisible(true);
        btnAddImage.setVisible(true);
        btnExportMaze.setVisible(true);
        tfImageSize.setVisible(true);
        lblImageSize.setVisible(true);
        btnSaveMaze.setVisible(true);
        this.repaint();
    }

    /**
     * Generate solution for a maze
     */
    private void generateSolution(){
        maze.GenerateSolution(this);
        this.repaint();
    }

    /**
     * Change the cell thats being selected
     * @param X_Position x position of cell
     * @param Y_Position y position of cell
     */
    public void changeFocus(int X_Position, int Y_Position){
        maze.dropCellFocus(focusedX, focusedY);

        focusedX =X_Position;
        focusedY =Y_Position;

        maze.gainCellFocus(focusedX, focusedY);
    }

    /**
     * resize image being added to maze
     * @param imgPath file directory of selected image
     * @return resized image
     */
    public ImageIcon resize(String imgPath)
    {
        ImageIcon path = new ImageIcon(imgPath);
        Image img = path.getImage();
        Image newImg = img.getScaledInstance(edgeSize *Integer.parseInt(tfImageSize.getText()), edgeSize *Integer.parseInt(tfImageSize.getText()) , Image.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(newImg);
        return image;
    }

    /**
     * Saves maze to database
     *
     */
    private void saveMaze(){
        maze.saveMaze(this, mazeName.getText());
    }

    /**
     * Load a selected maze from the database
     *
     * @param mazeId ID of maze being loaded
     * @param xsize x size of frame on which to load maze
     * @param ysize y size of rame on which to load maze
     */
    private void loadMaze(int mazeId, int xsize, int ysize) {
        maze = new Maze(xsize, ysize);
        maze.loadMaze(mazeId, this, xsize,ysize);
    }

    /**
     * Save the edited changes of a maze
     *
     * @param mazeId ID of maze being edited
     */
    private void editMaze(int mazeId) {
        maze.saveEdit(this, mazeId);
    }
}
