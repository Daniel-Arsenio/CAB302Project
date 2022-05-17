package src.MazeGUI;

import javax.swing.*;
import java.awt.*;

class GUIFunc extends JFrame{
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
