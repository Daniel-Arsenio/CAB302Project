package src.MazeGUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.HashMap;

class AdminWindow extends JFrame{
    final JFrame AdminFrame = new JFrame("User creation");
    private final JPanel adminPanel = new JPanel();
    private final DefaultTableModel tableModel = MainGUI.database.getUserTableModel();
    private final JTable adminUserDisplay = new JTable(tableModel);
    private final JScrollPane adminUserScrollPane = new JScrollPane(adminUserDisplay);
    private final JButton adminEditButton = new JButton();
    private final JButton adminAddButton = new JButton();
    private final JButton adminRemoveButton = new JButton();
    private final JButton adminLogoutButton = new JButton();
    private final JPasswordField newUserPassword = new JPasswordField();;
    private final JTextField newUsername = new JTextField();
    private final JTextField newData = new JTextField();
    private HashMap<String, String> user = new HashMap<>();
    private int admin_selected_user;

    public AdminWindow(){
        AdminFrame.setSize(600,800);
        adminPanel.setSize(600,800);
        adminPanel.setBackground(Color.LIGHT_GRAY);
        adminUserDisplay.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
            }

            @Override
            public void focusLost(FocusEvent e) {
                admin_selected_user = adminUserDisplay.getSelectedRow();
            }
        });

        adminEditButton.setText("Edit User");
        adminEditButton.setPreferredSize(new Dimension(100,35));
        adminEditButton.addActionListener(e -> {

            if (admin_selected_user == -1){JOptionPane.showMessageDialog(AdminFrame,"Please select a User.", "Edit error", JOptionPane.ERROR_MESSAGE); }
            else{
                user.put("ID", String.valueOf(adminUserDisplay.getValueAt(admin_selected_user, 0)));
                user.put("Username", String.valueOf(adminUserDisplay.getValueAt(admin_selected_user, 1)));
                user.put("Password", String.valueOf(adminUserDisplay.getValueAt(admin_selected_user, 2)));
                user.put("Permission", String.valueOf(adminUserDisplay.getValueAt(admin_selected_user, 3)));
                HashMap<String, String> newUser = new HashMap<>(user);
                String s  = (String)JOptionPane.showInputDialog(adminPanel,"What would you like to edit?"
                    ,"Edit User",JOptionPane.PLAIN_MESSAGE,null,new String[]{"Username","Password","Permission"},"Username");

                switch (s) {
                    case "Username" -> {
                        int choice1 = JOptionPane.showOptionDialog(adminPanel, new Object[]{"Insert new Username", newUsername}, "Edit User"
                                , JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
                        if (choice1 == JOptionPane.OK_OPTION) {
                            newUser.replace("Username", newUsername.getText());
                            MainGUI.database.alterUser(user, newUser);
                            adminUserDisplay.setModel(MainGUI.database.getUserTableModel());
                            newUsername.setText("");
                        }
                    }
                    case "Password" -> {
                        int choice2 = JOptionPane.showOptionDialog(adminPanel, new Object[]{"Insert new Password", newUserPassword}, "Edit User"
                                , JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
                        if (choice2 == JOptionPane.OK_OPTION) {
                            newUser.replace("Password", String.valueOf(newUserPassword.getPassword()));

                            MainGUI.database.alterUser(user, newUser);
                            adminUserDisplay.setModel(MainGUI.database.getUserTableModel());
                            newUserPassword.setText("");
                        }
                    }
                    case "Permission" -> {
                        String z = (String) JOptionPane.showInputDialog(adminPanel, "Change Permission Level"
                                , "Add User", JOptionPane.PLAIN_MESSAGE, null, new String[]{"Publisher", "Creator"}, "Permission");
                        newUser.replace("Permission", z);

                        MainGUI.database.alterUser(user, newUser);
                        adminUserDisplay.setModel(MainGUI.database.getUserTableModel());
                        newUsername.setText("");
                    }
                }
                admin_selected_user = -1;
                user.clear();
            }
        });

        adminAddButton.setText("Add User");
        adminAddButton.setPreferredSize(new Dimension(100,35));
        adminAddButton.addActionListener(e -> {

            String s  = (String)JOptionPane.showInputDialog(adminPanel,"Choose permission level"
                    ,"Add User",JOptionPane.PLAIN_MESSAGE,null,new String[]{"Publisher","Creator"},"Permission");
            if (s != null){
                int choice = JOptionPane.showOptionDialog(adminPanel,new Object[]{"Insert new username and password",newUsername,newUserPassword},"Add user"
                        ,JOptionPane.OK_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,null,null);
                if (choice == JOptionPane.OK_OPTION){
                    HashMap<String, String> newUser = new HashMap<>();
                    newUser.put("Username", newUsername.getText());
                    newUser.put("Password", String.valueOf(newUserPassword.getPassword()));
                    newUser.put("Permission", s);

                    if (!MainGUI.database.addUser(newUser)) JOptionPane.showMessageDialog(AdminFrame,"User already exists.", "Creation error", JOptionPane.ERROR_MESSAGE);;

                    adminUserDisplay.setModel(MainGUI.database.getUserTableModel());
                    newUsername.setText("");
                    newUserPassword.setText("");
                }
            }


        });

        adminRemoveButton.setText("Remove User");
        adminRemoveButton.setPreferredSize(new Dimension(150,35));
        adminRemoveButton.addActionListener(e -> {
            if (admin_selected_user == -1)
            {
                JOptionPane.showMessageDialog(AdminFrame,"Please select a User.", "Edit error", JOptionPane.ERROR_MESSAGE);
            }
            else {
                user.put("ID", String.valueOf(adminUserDisplay.getValueAt(admin_selected_user, 0)));
                if (String.valueOf(adminUserDisplay.getValueAt(admin_selected_user, 0)).equals("0"))
                {
                    JOptionPane.showMessageDialog(AdminFrame,"Cannot delete root user.", "Root error", JOptionPane.ERROR_MESSAGE);
                }
                else {MainGUI.database.removeUser(user);}
            }
            adminUserDisplay.setModel(MainGUI.database.getUserTableModel());
            admin_selected_user = -1;
            user.clear();
        });

        adminLogoutButton.setText("Logout");
        adminLogoutButton.setPreferredSize(new Dimension(100,35));

        adminLogoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainGUI.closeAdmin();
                MainGUI.openLogin();
            }
        });

        adminUserDisplay.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
            }

            @Override
            public void focusLost(FocusEvent e) {
                admin_selected_user = adminUserDisplay.getSelectedRow();
            }
        });

        setAdminLayout();

        AdminFrame.add(adminPanel);
    }

    /**
     * Set layout of admin window
     */
    private void setAdminLayout(){
        adminUserDisplay.getTableHeader().setReorderingAllowed(false);
        GridBagLayout adminLayout = new GridBagLayout();
        adminPanel.setLayout(adminLayout);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.anchor = GridBagConstraints.CENTER;

        addToPanel(adminPanel, adminUserScrollPane,constraints,4,1,0,0,0,0,0,0);
        addToPanel(adminPanel, adminAddButton,constraints,1,1,0,1,0,0,0,0);
        addToPanel(adminPanel, adminEditButton,constraints,1,1,1,1,0,0,0,0);
        addToPanel(adminPanel, adminRemoveButton,constraints,1,1,2,1,0,0,0,0);
        addToPanel(adminPanel, adminLogoutButton,constraints,1,1,3,1,0,0,0,0);
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
