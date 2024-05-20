package View;

import Control.QuizController;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginGUI {
    private JFrame frame;
    private JLabel nameLabel;
    private JTextField nameField;
    private JLabel categoryLabel;
    private JComboBox<String> categoryComboBox;
    private JButton startButton;
    private JButton increaseVolumeButton;  // @author Ali Farhan
    private JButton decreaseVolumeButton;  // @author Ali Farhan
    private JButton muteButton;            // @author Ali Farhan

    private QuizController quizController;

    /**
     * @author Manar, Karam, Sleiman, Ali, Elias
     */
    public LoginGUI(QuizController quizController) {
        this.quizController = quizController;

        frame = new JFrame("Quiz Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(850, 450);

        // Ladda bakgrundsbilden
        ImageIcon backgroundIcon = new ImageIcon(getClass().getResource("/images/background.png"));
        BackgroundPanel backgroundPanel = new BackgroundPanel(backgroundIcon.getImage());
        backgroundPanel.setLayout(null);
        frame.setContentPane(backgroundPanel);

        nameLabel = new JLabel("Name:");
        nameLabel.setBounds(150, 30, 100, 30);
        frame.add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(260, 30, 300, 30);  // Öka bredden på textfältet
        frame.add(nameField);

        categoryLabel = new JLabel("Category:");
        categoryLabel.setBounds(140, 70, 100, 30);
        frame.add(categoryLabel);

        categoryComboBox = new JComboBox<>(new String[]{"Bundesliga", "Premier League", "Laliga", "Serie A", "Ligue 1"});
        categoryComboBox.setBounds(260, 80, 300, 30);  // Öka bredden på combobox
        frame.add(categoryComboBox);

        startButton = new JButton("Start Quiz");
        startButton.setBounds(320, 250, 200, 50);  // Justera placering och storlek
        frame.add(startButton);

        increaseVolumeButton = new JButton("+ Volume");
        increaseVolumeButton.setBounds(750, 300, 80, 40);  // Justera placering och storlek
        frame.add(increaseVolumeButton);

        decreaseVolumeButton = new JButton("- Volume");
        decreaseVolumeButton.setBounds(750, 350, 80, 40);  // Justera placering och storlek
        frame.add(decreaseVolumeButton);

        muteButton = new JButton("Mute");
        muteButton.setBounds(750, 250, 80, 40);  // Justera placering och storlek
        frame.add(muteButton);

        startButton.addActionListener(e -> {
            String playerName = nameField.getText();
            String selectedCategory = (String) categoryComboBox.getSelectedItem();
            quizController.onStartQuiz(playerName, selectedCategory);
            frame.dispose(); // Stäng inloggningssidan när quizet börjar
        });

        increaseVolumeButton.addActionListener(e -> quizController.increaseVolume());
        decreaseVolumeButton.addActionListener(e -> quizController.decreaseVolume());
        muteButton.addActionListener(e -> quizController.mute());

        frame.setLocationRelativeTo(null); // Centrera GUI @author Ali Farhan & Elias Celyir
        frame.setVisible(true);
    }

    public Component getFrame() {
        return frame;
    }
}
