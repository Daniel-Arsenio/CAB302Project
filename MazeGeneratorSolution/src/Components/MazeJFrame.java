/**
 * Author: Marcus Nguyen
 * Developed for CAB 302 project
 * QUT student 2022
 */
package Components;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.EventListener;

public class MazeJFrame extends JFrame implements EventListener {

    //Variable Declaration
    private JCell cells = new JCell(10, 10, 50, 50, 1, 1, 0, 0);
    public JPanel panel = new JPanel();

    /**
     * Constructor
     *
     * @param Width  The Width of the frame
     * @param Height The Height of the frame
     */
    public MazeJFrame(int Width, int Height) {
        this.setVisible(true);
        this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setTitle("Maze Generator");
        this.setBounds(50, 50, (Width+2) * 50+ (Width*2) , (Height+2) * 50+ (Height*2));
        this.add(panel);
        this.getContentPane().setLayout(null);
        panel.setLayout(null);
        //panel.setBorder(new MatteBorder(2,2,2,2,Color.BLACK));
        panel.setBounds(50,50,(Width) * 50+4,(Height) * 50+4);
        this.repaint();
    }

}
