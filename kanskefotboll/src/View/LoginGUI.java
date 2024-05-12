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

    public LoginGUI(QuizController quizController) {
        this.quizController = quizController;

        frame = new JFrame("Quiz Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 300);
        frame.setLayout(null);
        frame.getContentPane().setBackground(new Color(192, 255, 193));

        nameLabel = new JLabel("Name:");
        nameLabel.setBounds(150, 30, 100, 30);
        frame.add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(210, 30, 200, 30);
        frame.add(nameField);

        categoryLabel = new JLabel("Category:");
        categoryLabel.setBounds(140, 70, 100, 30);
        frame.add(categoryLabel);

        categoryComboBox = new JComboBox<>(new String[]{"Bundesliga", "Premier League", "Laliga", "Serie A", "Ligue 1"});
        categoryComboBox.setBounds(210, 70, 200, 30);
        frame.add(categoryComboBox);

        startButton = new JButton("Start Quiz");
        startButton.setBounds(250, 220, 100, 40);
        frame.add(startButton);

        increaseVolumeButton = new JButton("+ Volume");
        increaseVolumeButton.setBounds(400, 100, 100, 30);
        frame.add(increaseVolumeButton);

        decreaseVolumeButton = new JButton("- Volume");
        decreaseVolumeButton.setBounds(400, 140, 100, 30);
        frame.add(decreaseVolumeButton);

        muteButton = new JButton("Mute");
        muteButton.setBounds(400, 180, 100, 30);
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
