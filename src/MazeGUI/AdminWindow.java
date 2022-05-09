package MazeGUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import static MazeGUI.GUIFunc.addToPanel;

class AdminWindow extends JFrame{
    final JFrame AdminFrame = new JFrame("User Creation");
    private final JPanel adminPanel = new JPanel();
    private final DefaultTableModel tableModel = new DefaultTableModel(MainGUI.DataBase.getUserDataAsArray(),MainGUI.DataBase.getUserColumnNames()) {
        @Override
        public boolean isCellEditable(int row, int column) {
            //all cells false
            return false;
        }
    };
    private final JTable adminUserDisplay = new JTable(tableModel);
    private final JScrollPane adminUserScrollPane = new JScrollPane(adminUserDisplay);
    private final JButton adminEditButton = new JButton();
    private final JButton adminAddButton = new JButton();
    private final JButton adminLogoutButton = new JButton();
    private final JPasswordField newUserPassword = new JPasswordField();;
    private final JTextField newUsername = new JTextField();
    private final JTextField newData = new JTextField();
    private final String[] newUserData = new String[4];
    private int admin_selected_user;

    public AdminWindow(){
        System.out.println(admin_selected_user);
        AdminFrame.setSize(600,800);
        adminPanel.setSize(600,800);
        adminPanel.setBackground(Color.LIGHT_GRAY);

        adminEditButton.setText("Edit User");
        adminEditButton.setPreferredSize(new Dimension(100,35));
        adminEditButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (admin_selected_user == -1){JOptionPane.showMessageDialog(AdminFrame,"Please select a User.", "Edit Error", JOptionPane.ERROR_MESSAGE); }
                else{
                    String s  = (String)JOptionPane.showInputDialog(adminPanel,"What would you like to edit?"
                        ,"Edit User",JOptionPane.PLAIN_MESSAGE,null,new String[]{"Username","Password","Permission"},"Username");
                    switch (s) {
                        case "Username" -> {
                            int choice1 = JOptionPane.showOptionDialog(adminPanel, new Object[]{"Insert new Username", newUsername}, "Edit User"
                                    , JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
                            if (choice1 == JOptionPane.OK_OPTION) {
                                MainGUI.DataBase.setUserData(newUsername.getText(), admin_selected_user, 1);
                                tableModel.setDataVector(MainGUI.DataBase.getUserDataAsArray(), MainGUI.DataBase.getUserColumnNames());
                                adminUserDisplay.setModel(tableModel);
                                newUsername.setText("");
                            }
                        }
                        case "Password" -> {
                            int choice2 = JOptionPane.showOptionDialog(adminPanel, new Object[]{"Insert new Password", newUserPassword}, "Edit User"
                                    , JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
                            if (choice2 == JOptionPane.OK_OPTION) {
                                MainGUI.DataBase.setUserData(String.valueOf(newUserPassword.getPassword()), admin_selected_user, 2);
                                tableModel.setDataVector(MainGUI.DataBase.getUserDataAsArray(), MainGUI.DataBase.getUserColumnNames());
                                adminUserDisplay.setModel(tableModel);
                                newUserPassword.setText("");
                            }
                        }
                        case "Permission" -> {
                            String z = (String) JOptionPane.showInputDialog(adminPanel, "Change Permission Level"
                                    , "Add User", JOptionPane.PLAIN_MESSAGE, null, new String[]{"Publisher", "Creator"}, "Permission");
                            MainGUI.DataBase.setUserData(z, admin_selected_user, 3);
                            tableModel.setDataVector(MainGUI.DataBase.getUserDataAsArray(), MainGUI.DataBase.getUserColumnNames());
                            adminUserDisplay.setModel(tableModel);
                        }
                    }
                }
            }
        });

        adminAddButton.setText("Add User");
        adminAddButton.setPreferredSize(new Dimension(100,35));
        adminAddButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String s  = (String)JOptionPane.showInputDialog(adminPanel,"Choose Permission Level"
                        ,"Add User",JOptionPane.PLAIN_MESSAGE,null,new String[]{"Publisher","Creator"},"Permission");
                if (s != null){
                    newUserData[0] = "0";
                    newUserData[3] = s;
                    int choice = JOptionPane.showOptionDialog(adminPanel,new Object[]{"Insert new username and Password",newUsername,newUserPassword},"Add User"
                            ,JOptionPane.OK_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,null,null);
                    if (choice == JOptionPane.OK_OPTION){
                        newUserData[1] = newUsername.getText();
                        newUserData[2] = String.valueOf(newUserPassword.getPassword());
                        MainGUI.DataBase.addUser(newUserData);
                        tableModel.setDataVector(MainGUI.DataBase.getUserDataAsArray(),MainGUI.DataBase.getUserColumnNames());
                        adminUserDisplay.setModel(tableModel);
                        newUsername.setText("");
                        newUserPassword.setText("");
                    }
                }


            }
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

    private void setAdminLayout(){
        adminUserDisplay.getTableHeader().setReorderingAllowed(false);
        GridBagLayout adminLayout = new GridBagLayout();
        adminPanel.setLayout(adminLayout);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.anchor = GridBagConstraints.CENTER;

        addToPanel(adminPanel, adminUserScrollPane,constraints,3,1,0,0,0,0,0,0);
        addToPanel(adminPanel, adminAddButton,constraints,1,1,0,1,0,0,0,0);
        addToPanel(adminPanel, adminEditButton,constraints,1,1,1,1,0,0,0,0);
        addToPanel(adminPanel, adminLogoutButton,constraints,1,1,2,1,0,0,0,0);
    }
}
