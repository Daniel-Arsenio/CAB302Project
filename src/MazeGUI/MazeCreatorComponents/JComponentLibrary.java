package src.MazeGUI.MazeCreatorComponents;

import javax.swing.*;
import java.awt.*;

/**
 * This Class contain method to create generic Java swing components
 */
public final class JComponentLibrary {
    /**
     * This method will return a general JPanel with given parameters
     *
     * @param frame           The JFrame that this panel will be added to
     * @param X_Position      X Position of the panel
     * @param Y_Position      Y Position of the Panel
     * @param Width           Width of the panel
     * @param Height          Height of the panel
     * @param backgroundColor Background color of the panel
     * @param Visible         Visibility of this panel
     * @return Generic JPanel from given parameters
     */
    public static JPanel CreateJPanel(JFrame frame, int X_Position, int Y_Position, int Width, int Height, Color backgroundColor, Boolean Visible) {
        JPanel jpanel = new JPanel();

        jpanel.setBounds(X_Position, Y_Position, Width, Height);
        jpanel.setBackground(backgroundColor);
        jpanel.setVisible(Visible);
        jpanel.setLayout(null);

        frame.add(jpanel);

        return jpanel;
    }

    /**
     * This method will return a general JLabel with given parameters
     *
     * @param panel      The JPanel that this label will be added to
     * @param X_Position X Position of the label
     * @param Y_Position Y Position of the label
     * @param text       Text of the label
     * @param textSize   Text size of the label
     * @param textColor  Color of the Text
     * @param Visible    Visibility of the label
     * @return Generic JLabel from given parameters
     */
    public static JLabel CreateJLabel(JPanel panel, int X_Position, int Y_Position, String text,int Width, int textSize, Color textColor, boolean Visible) {
        JLabel jlabel = new JLabel();

        jlabel.setText(text);
        jlabel.setFont(new Font("Times New Roman", Font.PLAIN, textSize));
        jlabel.setBounds(X_Position, Y_Position,Width,textSize+4);
        jlabel.setVisible(Visible);
        jlabel.setForeground(textColor);

        panel.add(jlabel);

        return jlabel;
    }

    /**
     * This method will return a general JButton with given parameters
     *
     * @param panel           The JPanel this button will be added to
     * @param X_Position      X Position of the button
     * @param Y_Position      Y Position of the button
     * @param Width           Width of the button
     * @param Height          Height of button
     * @param text            text displayed on the button
     * @param Visible         visibility of the button
     * @return Generic JButton from given parameters
     */
    public static JButton CreateButton(JPanel panel, int X_Position, int Y_Position, int Width, int Height, String text, Boolean Visible) {
        JButton jbutton = new JButton(text);

        jbutton.setBounds(X_Position, Y_Position, Width, Height);
        //jbutton.setBackground(backgroundColor);
        //jbutton.setForeground(textColor);
        jbutton.setVisible(Visible);

        panel.add(jbutton);
        return jbutton;
    }

    /**
     * This method will return a general JTextField with given parameters
     *
     * @param panel The JPanel this button will be added to
     * @param X_Position X Position of the button
     * @param Y_Position Y Position of the button
     * @param Width Width of the button
     * @param Height Height of button
     * @param text text displayed on the button
     * @param Visible visibility of the button
     * @return Generic JTextfield
     */
    public static JTextField CreateTextField(JPanel panel, int X_Position, int Y_Position, int Width, int Height, String text, Boolean Visible){
        JTextField jTextField = new JTextField(text);
        jTextField.setBounds(X_Position, Y_Position, Width, Height);
        jTextField.setVisible(Visible);

        panel.add(jTextField);
        return jTextField;
    }
}
