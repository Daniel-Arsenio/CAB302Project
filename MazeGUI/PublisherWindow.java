package MazeGUI;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

class PublisherWindow extends JFrame{

    private int selected_user;
    final JFrame PublisherFrame = new JFrame("User Creation");
    private final JLabel MazeViewerLabel = new JLabel();
    private final JPanel MazeDisplayPanel = new JPanel();
    private final JTable MazeListTable = new JTable();
    private final JScrollPane MazeListScrollPane = new JScrollPane(MazeListTable);
    private final JButton export_maze_button = new JButton();
    private final JButton export_solution_button = new JButton();
    private final JButton view_maze_button = new JButton();
    private final JButton view_solution_button = new JButton();
    private final JButton logoutPublisherButton = new JButton();
    private final JButton publishButton = new JButton();

    public PublisherWindow(){
        PublisherFrame.setSize(1400, 1000);
        PublisherFrame.setResizable(false);
        MazeDisplayPanel.setPreferredSize(new Dimension(1000,550));
        MazeListScrollPane.setPreferredSize(new Dimension(1000,200));

        Border blackline = BorderFactory.createLineBorder(Color.black);
        MazeDisplayPanel.setBackground(Color.WHITE);
        MazeDisplayPanel.setBorder(blackline);
        MazeListScrollPane.setBackground(Color.WHITE);
        MazeListScrollPane.setBorder(blackline);

        view_maze_button.setText("View Maze");
        view_maze_button.setPreferredSize(new Dimension(200,40));

        view_solution_button.setText("View Solution");
        view_solution_button.setPreferredSize(new Dimension(200,40));

        publishButton.setText("Publish Maze");
        publishButton.setPreferredSize(new Dimension(200,40));

        export_maze_button.setText("Export Maze Design(s)");
        export_maze_button.setPreferredSize(new Dimension(200,40));

        export_solution_button.setText("Export Maze Solution(s)");
        export_solution_button.setPreferredSize(new Dimension(200,40));

        logoutPublisherButton.setText("Logout");
        logoutPublisherButton.setPreferredSize(new Dimension(200,40));
        logoutPublisherButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainGUI.closePublish();
                MainGUI.openLogin();
            }
        });

        MazeViewerLabel.setText("Maze Preview");
        MazeViewerLabel.setFont(new Font("Times New Roman", Font.ITALIC, 50));

        JLabel lbl = new JLabel();
        MazeDisplayPanel.add(lbl);

        view_maze_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selected_user = MazeListTable.getSelectedRow();
                if (selected_user == -1){
                    JOptionPane.showMessageDialog(PublisherFrame,"Please select a maze.", "Maze Viewer Error", JOptionPane.ERROR_MESSAGE);
                }
                else if (MainGUI.DataBase.getMazeData().get(selected_user)[0].equals("1")) {
                    ImageIcon Maze = new ImageIcon("Maze1.JPG");
                    Image image = Maze.getImage();
                    Image newimg = image.getScaledInstance(980, 530,  Image.SCALE_SMOOTH); // scale it the smooth way
                    Maze = new ImageIcon(newimg);
                    lbl.setIcon(Maze);
                }
                else if (MainGUI.DataBase.getMazeData().get(selected_user)[0].equals("2")) {
                    ImageIcon Maze = new ImageIcon("Maze2.JPG");
                    Image image = Maze.getImage();
                    Image newimg = image.getScaledInstance(980, 530,  Image.SCALE_SMOOTH); // scale it the smooth way
                    Maze = new ImageIcon(newimg);
                    lbl.setIcon(Maze);
                }
                else if (MainGUI.DataBase.getMazeData().get(selected_user)[0].equals("3")) {
                    ImageIcon Maze = new ImageIcon("Maze3.JPG");
                    Image image = Maze.getImage();
                    Image newimg = image.getScaledInstance(980, 530,  Image.SCALE_SMOOTH); // scale it the smooth way
                    Maze = new ImageIcon(newimg);
                    lbl.setIcon(Maze);
                }
            }
        });

        export_maze_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selected_user = MazeListTable.getSelectedRow();
                if (selected_user == -1){
                    JOptionPane.showMessageDialog(PublisherFrame,"Please select a maze.", "Maze Export Error", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    JOptionPane.showMessageDialog(null, "Maze Export Successful");
                }
            }
        });

        export_solution_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selected_user = MazeListTable.getSelectedRow();
                if (selected_user == -1){
                    JOptionPane.showMessageDialog(PublisherFrame,"Please select a maze.", "Maze Export Error", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    JOptionPane.showMessageDialog(null, "Solution Export Successful");
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


    private void setPublisherLayout() {
        DefaultTableModel tableModel = new DefaultTableModel(MainGUI.DataBase.getMazeDataAsArray(),MainGUI.DataBase.getMazeColumnNames()) {

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

        addToFrame(PublisherFrame, MazeDisplayPanel, constraints, 1, 2, 0,1,5,50,5,5);
        addToFrame(PublisherFrame, MazeListScrollPane, constraints, 1, 5, 0,3,5,5,5,5);
        addToFrame(PublisherFrame, view_solution_button, constraints, 1, 1, 1,1,5,5,5,5);
        addToFrame(PublisherFrame, publishButton, constraints, 1, 1, 1,2,5,5,5,5);
        addToFrame(PublisherFrame, export_maze_button, constraints, 1, 1, 1,4,5,5,5,5);
        addToFrame(PublisherFrame, export_solution_button, constraints, 1, 1, 1,5,5,5,5,5);
        addToFrame(PublisherFrame, view_maze_button, constraints, 1, 1, 1,6,5,5,5,5);
        addToFrame(PublisherFrame, logoutPublisherButton, constraints, 1, 1, 1,7,5,5,5,5);
        addToFrame(PublisherFrame, MazeViewerLabel, constraints, 1, 1, 0,0,5,5,5,5);

    }
    private void addToPanel(JPanel jp,Component c, GridBagConstraints
            constraints, int width, int height, int x, int y,int top, int bot,int right,int left) {
        constraints.gridx = x;
        constraints.gridy = y;
        constraints.gridwidth = width;
        constraints.gridheight = height;
        constraints.insets = new Insets(top,left,bot,right);
        jp.add(c, constraints);
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
