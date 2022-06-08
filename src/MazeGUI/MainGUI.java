package src.MazeGUI;

import javax.swing.*;
import java.sql.SQLException;
import java.util.HashMap;

public class MainGUI extends JFrame implements Runnable {
    static DatabaseLibrary database;
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
    static MazeEditorWindow mainMazeEditorWindow = new MazeEditorWindow(6,6);
    static final HashMap<String, String> currentUser = new HashMap<>();


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
        mainMazeCLandingWindow.MazeCFrame.setVisible(true);
    }
    public static void closeMazeC(){
        mainMazeCLandingWindow.MazeCFrame.setVisible(false);
        mainMazeCLandingWindow.MazeCFrame.dispose();
    }

    static void openMazeEdit(){
        mainMazeEditorWindow.mazeFrame.repaint();
        mainMazeEditorWindow.mazeFrame.setVisible(true);
    }
    public static void closeMazeEdit(){
        mainMazeEditorWindow.mazeFrame.setVisible(false);
        mainMazeEditorWindow.mazeFrame.dispose();
    }



    @Override
    public void run() {
        openLogin();
        }

    public static void main(String[] args) throws SQLException {SwingUtilities.invokeLater(new MainGUI("Maze Creator"));}
}
