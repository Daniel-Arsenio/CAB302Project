package src.MazeGUI;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

class MazeCLandingWindow extends JFrame{

    private int selected_user;
    final JFrame MazeCFrame = new JFrame("User Creation");
    private final JLabel mazeViewerLabel = new JLabel();
    private final JPanel mazeDisplayPanel = new JPanel();
    static final JTable mazeListTable = new JTable(MainGUI.database.getMazeTableModel());
    private final JScrollPane mazeListScrollPane = new JScrollPane(mazeListTable);
    private final JButton newMazeButton = new JButton();
    private final JButton mazeEditButton = new JButton();
    private final JButton viewMaze = new JButton();
    private final JButton viewMazeSolution = new JButton();
    private final JButton logoutButton = new JButton();

    public MazeCLandingWindow(){
        MazeCFrame.setSize(1400, 1000);
        MazeCFrame.setResizable(false);
        mazeDisplayPanel.setPreferredSize(new Dimension(1000,550));
        mazeListScrollPane.setPreferredSize(new Dimension(1000,200));
        mazeListTable.setAutoCreateRowSorter(true);

        Border blackline = BorderFactory.createLineBorder(Color.black);
        mazeDisplayPanel.setBackground(Color.WHITE);
        mazeDisplayPanel.setBorder(blackline);
        mazeListScrollPane.setBackground(Color.WHITE);
        mazeListScrollPane.setBorder(blackline);

        viewMaze.setText("View Maze");
        viewMaze.setPreferredSize(new Dimension(200,40));

        viewMazeSolution.setText("View Solution");
        viewMazeSolution.setPreferredSize(new Dimension(200,40));

        newMazeButton.setText("New Maze");
        newMazeButton.setPreferredSize(new Dimension(200,40));

        mazeEditButton.setText("Edit Selected Maze");
        mazeEditButton.setPreferredSize(new Dimension(200,40));

        logoutButton.setText("Logout");
        logoutButton.setPreferredSize(new Dimension(200,40));
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainGUI.closePublish();
                MainGUI.openLogin();
            }
        });

        mazeViewerLabel.setText("Maze Preview");
        mazeViewerLabel.setFont(new Font("Times New Roman", Font.ITALIC, 50));

        JLabel lbl = new JLabel();
        mazeDisplayPanel.add(lbl);

        viewMaze.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selected_user = mazeListTable.getSelectedRow();
                if (selected_user == -1){
                    JOptionPane.showMessageDialog(MazeCFrame,"Please select a maze.", "Maze Viewer Error", JOptionPane.ERROR_MESSAGE);
                }
                else if (1 == 2){//MainGUI.database.getMazeData().get(selected_user)[0].equals("1")''') {
                    ImageIcon Maze = new ImageIcon("Maze1.JPG");
                    Image image = Maze.getImage();
                    Image newimg = image.getScaledInstance(980, 530,  Image.SCALE_SMOOTH); // scale it the smooth way
                    Maze = new ImageIcon(newimg);
                    lbl.setIcon(Maze);
                }
                else if (1 == 1){//MainGUI.database.getMazeData().get(selected_user)[0].equals("2")) {
                    ImageIcon Maze = new ImageIcon("Maze2.JPG");
                    Image image = Maze.getImage();
                    Image newimg = image.getScaledInstance(980, 530,  Image.SCALE_SMOOTH); // scale it the smooth way
                    Maze = new ImageIcon(newimg);
                    lbl.setIcon(Maze);
                }
                else if (selected_user == 1){//MainGUI.database.getMazeData().get(selected_user)[0].equals("3")) {
                    ImageIcon Maze = new ImageIcon("Maze3.JPG");
                    Image image = Maze.getImage();
                    Image newimg = image.getScaledInstance(980, 530,  Image.SCALE_SMOOTH); // scale it the smooth way
                    Maze = new ImageIcon(newimg);
                    lbl.setIcon(Maze);
                }
            }
        });

        newMazeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainGUI.closeMazeC();
                int x_size=0;
                int y_size=0;

                String s  = (String)JOptionPane.showInputDialog(MazeCFrame,"Choose difficulty level"
                        ,"Add User",JOptionPane.PLAIN_MESSAGE,null,new String[]{"Kids","Easy", "Medium", "Hard"},"Easy");
                if (s != null){
                    switch (s) {
                        case "Kids" -> {
                                x_size = 10;
                                y_size = 10;
                            }
                        case "Easy" -> {
                            x_size = 20;
                            y_size = 20;
                        }
                        case "Medium" -> {
                            x_size = 30;
                            y_size = 30;
                        }
                        case "Hard" -> {
                            x_size = 40;
                            y_size = 40;
                        }
                    }
                    MainGUI.openMazeEdit(x_size, y_size);
                }
            }
        });

        mazeEditButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainGUI.closeMazeC();
                //MainGUI.openMazeEdit();
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainGUI.closeMazeC();
                MainGUI.openLogin();
            }
        });

        setMazeCLayout();
        MazeCFrame.repaint();
        MazeCFrame.dispose();


    }


    private void setMazeCLayout() {
        DefaultTableModel tableModel = new DefaultTableModel(){//MainGUI.database.getMazeDataAsArray(),MainGUI.database.getMazeColumnNames()) {

            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        };
        mazeListTable.setModel(tableModel);
        mazeListTable.getTableHeader().setReorderingAllowed(false);
        GridBagLayout adminLayout = new GridBagLayout();
        MazeCFrame.setLayout(adminLayout);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.NORTHEAST;

        addToFrame(MazeCFrame, mazeDisplayPanel, constraints, 1, 2, 0,1,5,50,5,5);
        addToFrame(MazeCFrame, mazeListScrollPane, constraints, 1, 5, 0,3,5,5,5,5);
        addToFrame(MazeCFrame, viewMazeSolution, constraints, 1, 1, 1,1,5,5,5,5);
        addToFrame(MazeCFrame, newMazeButton, constraints, 1, 1, 1,4,5,5,5,5);
        addToFrame(MazeCFrame, mazeEditButton, constraints, 1, 1, 1,5,5,5,5,5);
        addToFrame(MazeCFrame, viewMaze, constraints, 1, 1, 1,6,5,5,5,5);
        addToFrame(MazeCFrame, logoutButton, constraints, 1, 1, 1,7,5,5,5,5);
        addToFrame(MazeCFrame, mazeViewerLabel, constraints, 1, 1, 0,0,5,5,5,5);

    }

    private void addToFrame(JFrame jf,Component c, GridBagConstraints
            constraints, int width, int height, int x, int y,int top, int bot,int right,int left) {
        constraints.gridx = x;
        constraints.gridy = y;
        constraints.gridwidth = width;
        constraints.gridheight = height;
        constraints.insets = new Insets(top,left,bot,right);
        jf.add(c, constraints);
    }

}

