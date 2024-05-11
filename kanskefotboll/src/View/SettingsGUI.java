package View;

import javax.swing.*;
import java.awt.*;

public class SettingsGUI {
    private JFrame frame;

    public void displaySettings() {
        frame = new JFrame("Settings");
        frame.setSize(400, 200);
        frame.setLayout(new BorderLayout());


        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
