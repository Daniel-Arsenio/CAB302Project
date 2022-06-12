package src.MazeGUI;

import javax.swing.*;
import java.awt.*;

class LoginWindow extends JFrame {
    final JFrame LoginWindowFrame = new JFrame("Maze Creator");
    private final JPanel loginWindow = new JPanel();
    private final JLabel usernameLabel = new JLabel();
    private final JLabel passwordLabel = new JLabel();
    private final JButton loginButton = new JButton();
    final JTextField usernameField = new JTextField(15);
    final JPasswordField passwordField = new JPasswordField(15);

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
                JOptionPane.showMessageDialog(loginWindow,"Username or password are incorrect, please try again.", "Login error", JOptionPane.ERROR_MESSAGE);

            else if (MainGUI.database.getPermission(usernameField.getText(), String.valueOf(passwordField.getPassword())).equals("Admin")) {
                MainGUI.currentUser.put("Username", usernameField.getText());
                MainGUI.currentUser.put("Password", String.valueOf(passwordField.getPassword()));
                MainGUI.closeLogin();
                MainGUI.openAdmin();
            }

            else if (MainGUI.database.getPermission(usernameField.getText(), String.valueOf(passwordField.getPassword())).equals("Creator")){
                MainGUI.currentUser.put("Username", usernameField.getText());
                MainGUI.currentUser.put("Password", String.valueOf(passwordField.getPassword()));
                MainGUI.closeLogin();
                MainGUI.openMazeC();
            }

            else if(MainGUI.database.getPermission(usernameField.getText(), String.valueOf(passwordField.getPassword())).equals("Publisher")){
                MainGUI.currentUser.put("Username", usernameField.getText());
                MainGUI.currentUser.put("Password", String.valueOf(passwordField.getPassword()));
                MainGUI.closeLogin();
                MainGUI.openPublish();
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

    /**
     *Add ui components to panel
     *
     * @param jp frame for componenents to be added to
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
    public static void addToPanel(JPanel jp, Component c, GridBagConstraints
            constraints, int width, int height, int x, int y, int top, int bot, int right, int left) {
        constraints.gridx = x;
        constraints.gridy = y;
        constraints.gridwidth = width;
        constraints.gridheight = height;
        constraints.insets = new Insets(top,left,bot,right);
        jp.add(c, constraints);
    }
}
