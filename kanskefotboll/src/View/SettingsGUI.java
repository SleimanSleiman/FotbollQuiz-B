package View;


import javax.swing.*;
import java.awt.*;

/**
 * @author Sleiman Sleiman
 */

public class SettingsGUI {
    private JFrame frame;

    public void displaySettings() {
        frame = new JFrame("Settings");
        frame.setSize(400, 200);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(new Color(192, 255, 193));
        frame.setLocationRelativeTo(null);



        frame.setVisible(true);
    }


    }