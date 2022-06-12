package src.MazeGUI;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

class MazeCLandingWindow extends JFrame{

    private int selected_user;
    final JFrame MazeCFrame = new JFrame("User Creation");
    static final JTable mazeListTable = new JTable(MainGUI.database.getMazeTableModel());
    private final JScrollPane mazeListScrollPane = new JScrollPane(mazeListTable);
    private final JButton newMazeButton = new JButton();
    private final JButton mazeEditButton = new JButton();
    private final JButton viewMaze = new JButton();
    private final JButton logoutButton = new JButton();
    private int selectedMaze;
    private int x_size=0;
    private int y_size=0;

    public MazeCLandingWindow(){
        MazeCFrame.setSize(1400, 1000);
        MazeCFrame.setResizable(false);
        mazeListScrollPane.setPreferredSize(new Dimension(1000,750));
        mazeListTable.setAutoCreateRowSorter(true);
        mazeListTable.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
            }

            @Override
            public void focusLost(FocusEvent e) {
                selectedMaze = mazeListTable.getSelectedRow();
            }
        });

        Border blackline = BorderFactory.createLineBorder(Color.black);
        mazeListScrollPane.setBackground(Color.WHITE);
        mazeListScrollPane.setBorder(blackline);

        viewMaze.setText("View Maze");
        viewMaze.setPreferredSize(new Dimension(200,40));

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

        viewMaze.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selected_user = mazeListTable.getSelectedRow();
                if (selected_user == -1){
                    JOptionPane.showMessageDialog(MazeCFrame,"Please select a maze.", "Maze Viewer Error", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    int maze_id =  (int) mazeListTable.getValueAt(selected_user, 0);
                    switch ((String) mazeListTable.getValueAt(selectedMaze, 5)) {
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
                    selected_user = -1;
                    MazeJFrame mazeFrame = new MazeJFrame(x_size,  y_size, maze_id);
                    mazeFrame.btnGenerateMaze.setVisible(false);
                    mazeFrame.btnSaveMaze.setVisible(false);
                    mazeFrame.btnLeft.setVisible(false);
                    mazeFrame.btnRight.setVisible(false);
                    mazeFrame.btnTop.setVisible(false);
                    mazeFrame.btnBottom.setVisible(false);
                    mazeFrame.lblFocused_X.setVisible(false);
                    mazeFrame.lblFocused_Y.setVisible(false);
                    mazeFrame.btnGenerateSolution.setVisible(true);
                }
            }
        });

        newMazeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String s  = (String)JOptionPane.showInputDialog(MazeCFrame,"Choose difficulty level"
                        ,"Choose Difficulty",JOptionPane.PLAIN_MESSAGE,null,new String[]{"Easy", "Medium", "Hard"},"Easy");
                if (s != null){
                    switch (s) {
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
                    MainGUI.closeMazeC();
                    MainGUI.openMazeEdit(x_size, y_size);
                }
            }
        });

        mazeEditButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selected_user = mazeListTable.getSelectedRow();
                if (selected_user == -1){
                    JOptionPane.showMessageDialog(MazeCFrame,"Please select a maze.", "Maze Viewer Error", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    String user = MainGUI.currentUser.get("Username");
                    String user2 = (String) mazeListTable.getValueAt(selected_user, 2);
                    if (user.equals(user2)) {
                        int maze_id =  (int) mazeListTable.getValueAt(selected_user, 0);
                        switch ((String) mazeListTable.getValueAt(selectedMaze, 5)) {
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
                        selected_user = -1;
                        MazeJFrame mazeFrame = new MazeJFrame(x_size,  y_size, maze_id);
                        mazeFrame.btnGenerateMaze.setVisible(false);
                        mazeFrame.btnAddImage.setVisible(true);
                        mazeFrame.lblImageSize.setVisible(true);
                        mazeFrame.tfImageSize.setVisible(true);
                        mazeFrame.btnGenerateSolution.setVisible(true);
                        mazeFrame.btnSaveMaze.setVisible(true);
                    }
                    else {
                        JOptionPane.showMessageDialog(MazeCFrame,"Cannot edit this maze.", "Maze Viewer Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
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

    /**
     * Set layout of Creator landing window
     */
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

        addToFrame(MazeCFrame, mazeListScrollPane, constraints, 1, 5, 0,1,5,5,5,5);
        addToFrame(MazeCFrame, newMazeButton, constraints, 1, 1, 1,1,5,5,5,5);
        addToFrame(MazeCFrame, mazeEditButton, constraints, 1, 1, 1,2,5,5,5,5);
        addToFrame(MazeCFrame, viewMaze, constraints, 1, 1, 1,3,5,5,5,5);
        addToFrame(MazeCFrame, logoutButton, constraints, 1, 1, 1,7,5,5,5,5);

    }

    /**
     * Add a component to frame of landing window
     *
     * @param jf frame for componenents to be added to
     * @param c component being added
     * @param constraints constraints of component
     * @param width width of component
     * @param height height of component
     * @param x x position of component
     * @param y y position of component
     * @param top top padding of component
     * @param bot bottom padding of component
     * @param right right padding of component
     * @param left left padding of component
     */
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

