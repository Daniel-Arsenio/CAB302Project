package MazeGUI;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

import static MazeGUI.GUIFunc.addToPanel;


class LoginWindow extends JFrame {
    final JFrame LoginWindowFrame = new JFrame("Maze Creator");
    private final JPanel loginWindow = new JPanel();
    private final JLabel usernameLabel = new JLabel();
    private final JLabel passwordLabel = new JLabel();
    private final JButton loginButton = new JButton();
    final JTextField usernameField = new JTextField(15);
    final JPasswordField passwordField = new JPasswordField(15);
    final HashMap<String, String> currentUser = new HashMap<>();

    public LoginWindow(){
        LoginWindowFrame.setSize(1000, 800);
        LoginWindowFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginWindow.setBackground(Color.LIGHT_GRAY);
        loginWindow.setSize(200,100);

        usernameLabel.setText("Username:");
        passwordLabel.setText("Password:");
        passwordField.setEchoChar('*');
        loginButton.setText("Login");
        loginButton.addActionListener(e -> {
            if (MainGUI.database.getPermission(usernameField.getText(), String.valueOf(passwordField.getPassword())) == null)
                JOptionPane.showMessageDialog(loginWindow,"Username or Password are Incorrect, please try again.", "Login Error", JOptionPane.ERROR_MESSAGE);

            else if (MainGUI.database.getPermission(usernameField.getText(), String.valueOf(passwordField.getPassword())).equals("Admin")) {
                MainGUI.closeLogin();
                MainGUI.openAdmin();
            }

            else if (MainGUI.database.getPermission(usernameField.getText(), String.valueOf(passwordField.getPassword())).equals("Creator")){
                MainGUI.closeLogin();
                MainGUI.openPublish();
            }

            else if(MainGUI.database.getPermission(usernameField.getText(), String.valueOf(passwordField.getPassword())).equals("Publisher")){
                MainGUI.closeLogin();
                MainGUI.openMazeC();
            }
        });
        loginComponentsLayout();
        LoginWindowFrame.add(loginWindow);
    }

    private void loginComponentsLayout(){
        GridBagLayout loginLayout = new GridBagLayout();
        loginWindow.setLayout(loginLayout);
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.CENTER;
        addToPanel(loginWindow, usernameLabel, constraints,1, 1, 0,0, 5,5,5,5);
        addToPanel(loginWindow, usernameField, constraints,1, 1,1,0,5,5,5,5);
        addToPanel(loginWindow, passwordLabel, constraints,1,1, 0,1,5,5,5,5);
        addToPanel(loginWindow, passwordField, constraints,1 ,1, 1,1,5,5,5,5);
        addToPanel(loginWindow, loginButton, constraints,2,1, 0,2,10,10,10,10);
    }
}
