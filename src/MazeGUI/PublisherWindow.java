package src.MazeGUI;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JFileChooser;

class PublisherWindow extends JFrame{

    private int selectedMaze;
    final JFrame PublisherFrame = new JFrame("User Creation");
    static final JTable MazeListTable = new JTable(MainGUI.database.getMazeTableModel());
    private final JScrollPane MazeListScrollPane = new JScrollPane(MazeListTable);
    private final JButton view_maze_button = new JButton();
    private final JButton logoutPublisherButton = new JButton();
    private int x_size=0;
    private int y_size=0;

    public PublisherWindow(){
        PublisherFrame.setSize(1400, 1000);
        PublisherFrame.setResizable(false);
        MazeListScrollPane.setPreferredSize(new Dimension(1000,750));
        MazeListTable.setAutoCreateRowSorter(true);
        MazeListTable.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
            }

            @Override
            public void focusLost(FocusEvent e) {
                selectedMaze = MazeListTable.getSelectedRow();
            }
        });

        Border blackline = BorderFactory.createLineBorder(Color.black);
        MazeListScrollPane.setBackground(Color.WHITE);
        MazeListScrollPane.setBorder(blackline);

        view_maze_button.setText("View Maze");
        view_maze_button.setPreferredSize(new Dimension(200,40));

        logoutPublisherButton.setText("Logout");
        logoutPublisherButton.setPreferredSize(new Dimension(200,40));
        logoutPublisherButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainGUI.closePublish();
                MainGUI.openLogin();
            }
        });


        view_maze_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedMaze = MazeListTable.getSelectedRow();
                if (selectedMaze == -1){
                    JOptionPane.showMessageDialog(PublisherFrame,"Please select a maze.", "Maze Viewer Error", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    int maze_id =  (int) MazeListTable.getValueAt(selectedMaze, 0);
                    switch ((String) MazeListTable.getValueAt(selectedMaze, 5)) {
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
                    selectedMaze = -1;
                    MazeJFrame mazeFrame = new MazeJFrame(x_size,  y_size, maze_id);
                    mazeFrame.btnGenerateMaze.setVisible(false);
                    mazeFrame.btnSaveMaze.setVisible(false);
                    mazeFrame.btnLeft.setVisible(false);
                    mazeFrame.btnRight.setVisible(false);
                    mazeFrame.btnTop.setVisible(false);
                    mazeFrame.btnBottom.setVisible(false);
                    mazeFrame.lblFocused_X.setVisible(false);
                    mazeFrame.lblFocused_Y.setVisible(false);
                    mazeFrame.btnExportMaze.setVisible(true);
                    mazeFrame.btnGenerateSolution.setVisible(true);
                }
            }
        });

        logoutPublisherButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainGUI.closeAdmin();
                MainGUI.openLogin();
            }
        });

        setPublisherLayout();
        PublisherFrame.repaint();
        PublisherFrame.dispose();


    }

    /**
     * Set layout of Creator landing window
     */
    private void setPublisherLayout() {
        DefaultTableModel tableModel = new DefaultTableModel(){//MainGUI.database.getMazeDataAsArray(),MainGUI.database.getMazeColumnNames()) {

            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        };
        MazeListTable.setModel(tableModel);
        MazeListTable.getTableHeader().setReorderingAllowed(false);
        GridBagLayout adminLayout = new GridBagLayout();
        PublisherFrame.setLayout(adminLayout);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.NORTHEAST;

        addToFrame(PublisherFrame, MazeListScrollPane, constraints, 1, 5, 0,1,5,5,5,5);
        addToFrame(PublisherFrame, view_maze_button, constraints, 1, 1, 1,1,5,5,5,5);
        addToFrame(PublisherFrame, logoutPublisherButton, constraints, 1, 1, 1,7,5,5,5,5);

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
