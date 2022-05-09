/**
 * Author: Marcus Nguyen
 * Developed for CAB 302 project
 * QUT student 2022
 */
package MazeGUI;

import MazeGUI.MainGUI;
import MazeGUI.MazeCreatorComponents.JComponentLibrary;
import com.sun.tools.javac.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MazeJFrame extends JFrame {

    /**
     * Variable Declaration
     */
    public JPanel MazePanel, ControllerPanel;
    public JButton btnLeft,btnRight,btnTop,btnBottom,btnLogout, btnGenerate, btnSolution;
    public JLabel lblFocused_X,lblFocused_Y;
    private int CellSize = 50;
    /**
     * Constructor
     *
     * @param Width  The Width of the frame
     * @param Height The Height of the frame
     */
    public MazeJFrame(int Width, int Height) {

        this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setTitle("Maze Generator");
        this.setBounds(50, 50, (Width+2) * CellSize+ (Width*2) + 250 , (Height+2) * CellSize+ (Height*2));
        this.setMinimumSize(new Dimension(1000,500));
        this.getContentPane().setLayout(null);

        MazePanel = JComponentLibrary.CreateJPanel(this,50,50,(Width) * CellSize+4,(Height) * CellSize+4,null,true);
        ControllerPanel = JComponentLibrary.CreateJPanel(this,this.getWidth()-300,50,200,this.getHeight()-120,null,true);

        btnTop = JComponentLibrary.CreateButton(ControllerPanel,50,0,100,50,Color.WHITE,Color.BLACK,"T",true);
        btnLeft = JComponentLibrary.CreateButton(ControllerPanel,0,50,50,100,Color.WHITE,Color.BLACK,"L",true);
        btnRight = JComponentLibrary.CreateButton(ControllerPanel,150,50,50,100,Color.WHITE,Color.BLACK,"R",true);
        btnBottom = JComponentLibrary.CreateButton(ControllerPanel,50,150,100,50,Color.WHITE,Color.BLACK,"B",true);
        btnGenerate = JComponentLibrary.CreateButton(ControllerPanel,0,ControllerPanel.getHeight()-150,200,50,Color.WHITE,Color.BLACK,"Generate Maze",true);
        btnSolution = JComponentLibrary.CreateButton(ControllerPanel,0,ControllerPanel.getHeight()-100,200,50,Color.WHITE,Color.BLACK,"Generate Optimal Solution",true);
        btnLogout = JComponentLibrary.CreateButton(ControllerPanel, 0, ControllerPanel.getHeight()-50,200,50,Color.WHITE, Color.BLACK,"Back",true);
        btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainGUI.closeMazeEdit();
                MainGUI.openMazeC();
            }
        });
        lblFocused_X=JComponentLibrary.CreateJLabel(ControllerPanel,55,75,"Focused X:",95,15,Color.BLACK,true);
        lblFocused_Y=JComponentLibrary.CreateJLabel(ControllerPanel,55,105,"Focused Y:",95,15,Color.BLACK,true);
        this.repaint();
    }
}
