package View;

import javax.swing.*;
import java.awt.*;

/**
 * InstructionsGUI-klassen skapar ett grafiskt användargränssnitt för att visa instruktioner.
 *
 *  @author Sleiman Sleiman
 */
public class InstructionsGUI {
    private JFrame frame;

    public InstructionsGUI() {
        frame = new JFrame("Quiz Instruktioner");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 400);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.WHITE);

        JTextArea instructionsArea = new JTextArea();
        instructionsArea.setEditable(false);
        instructionsArea.setText(
                "Välkommen till Fotbollsquiz!\n\n" +
                        "Instruktioner:\n" +
                        "1. Ange ditt namn och välj en kategori för att starta quizet.\n" +
                        "2. Du kommer att få flervalsfrågor.\n" +
                        "3. För varje rätt svar får du poäng.\n" +
                        "4. Du har ett begränsat antal liv. Varje fel svar kostar ett liv.\n" +
                        "5. Quizet är tidsbestämt, så svara så snabbt som möjligt.\n\n" +
                        "Lycka till!"
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