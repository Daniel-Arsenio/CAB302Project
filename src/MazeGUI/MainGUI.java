package src.MazeGUI;

import javax.swing.*;
import java.sql.SQLException;
import java.util.HashMap;
import src.DataBaseLibrary.*;
import src.MazeGUI.*;

public class MainGUI extends JFrame implements Runnable {
    public static DatabaseLibrary database;
    static {
        try {
            database = new DatabaseLibrary();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    static LoginWindow mainLoginWindow = new LoginWindow();
    static AdminWindow mainAdminWindow = new AdminWindow();
    static PublisherWindow mainPublisherWindow = new PublisherWindow();
    static MazeCLandingWindow mainMazeCLandingWindow = new MazeCLandingWindow();
    static MazeJFrame mainMazeEditorWindow;
    public static HashMap<String, String> currentUser = new HashMap<>();


    public MainGUI(String args) throws SQLException {
    }

    /**
     *
     * Opens the main login page.
     *
     */
    public static void openLogin(){
        mainLoginWindow.LoginWindowFrame.repaint();
        mainLoginWindow.LoginWindowFrame.setVisible(true);
    }
    static void closeLogin(){
        mainLoginWindow.LoginWindowFrame.setVisible(false);
        mainLoginWindow.usernameField.setText("");
        mainLoginWindow.passwordField.setText("");
        mainLoginWindow.LoginWindowFrame.dispose();
    }

    static void openPublish(){
        mainPublisherWindow.PublisherFrame.repaint();
        PublisherWindow.MazeListTable.setModel(MainGUI.database.getMazeTableModel());
        mainPublisherWindow.PublisherFrame.setVisible(true);
    }
    static void closePublish(){
        mainPublisherWindow.PublisherFrame.setVisible(false);
        mainPublisherWindow.PublisherFrame.dispose();
    }

    static void openAdmin(){
        mainAdminWindow.AdminFrame.repaint();
        mainAdminWindow.AdminFrame.setVisible(true);
    }
    static void closeAdmin(){
        mainAdminWindow.AdminFrame.setVisible(false);
        mainAdminWindow.AdminFrame.dispose();
    }

    static void openMazeC(){
        mainMazeCLandingWindow.MazeCFrame.repaint();
        MazeCLandingWindow.mazeListTable.setModel(MainGUI.database.getMazeTableModel());
        mainMazeCLandingWindow.MazeCFrame.setVisible(true);
    }
    public static void closeMazeC(){
        mainMazeCLandingWindow.MazeCFrame.setVisible(false);
        mainMazeCLandingWindow.MazeCFrame.dispose();
    }

    static void openMazeEdit(int X_MazeSize, int Y_MazeSize){
        mainMazeEditorWindow = new MazeJFrame(X_MazeSize, Y_MazeSize);
    }



    @Override
    public void run() {
        openLogin();
        }

    public static void main(String[] args) throws SQLException {SwingUtilities.invokeLater(new MainGUI("Maze Creator"));}
}
