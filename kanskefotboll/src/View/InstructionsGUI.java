package View;

import javax.swing.*;
import java.awt.*;

/**
 * The InstructionsGUI class creates a graphical user interface to display instructions.
 * Author: Sleiman Sleiman
 */
public class InstructionsGUI {
    private JFrame frame;

    /**
     * Creates a new InstructionsGUI.
     */
    public InstructionsGUI() {
        frame = new JFrame("Quiz Instructions");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 400);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.WHITE);

        JTextArea instructionsArea = new JTextArea();
        instructionsArea.setEditable(false);
        instructionsArea.setText(
                "Welcome to Football Quiz!\n\n" +
                        "Instructions:\n" +
                        "1. Enter your name and choose a category to start the quiz.\n" +
                        "2. You will be presented with multiple-choice questions.\n" +
                        "3. You earn points for each correct answer.\n" +
                        "4. You have a limited number of lives. Each incorrect answer costs a life.\n" +
                        "5. The quiz is timed, so answer as quickly as possible.\n\n" +
                        "Good luck!"
        );
        instructionsArea.setFont(new Font("Arial", Font.PLAIN, 16));
        instructionsArea.setBackground(new Color(245, 245, 245));
        instructionsArea.setForeground(Color.BLACK);
        instructionsArea.setLineWrap(true);
        instructionsArea.setWrapStyleWord(true);
        instructionsArea.setMargin(new Insets(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(instructionsArea);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        scrollPane.getViewport().setBackground(new Color(245, 245, 245));

        panel.add(scrollPane, BorderLayout.CENTER);

        frame.add(panel, BorderLayout.CENTER);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}
