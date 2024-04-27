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

    private QuizController quizController;

    public LoginGUI(QuizController quizController) {
        this.quizController = quizController;

        frame = new JFrame("Quiz Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLayout(null);

        nameLabel = new JLabel("Name:");
        nameLabel.setBounds(50, 30, 100, 30);
        frame.add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(150, 30, 200, 30);
        frame.add(nameField);

        categoryLabel = new JLabel("Category:");
        categoryLabel.setBounds(50, 80, 100, 30);
        frame.add(categoryLabel);

        categoryComboBox = new JComboBox<>(new String[]{"Bundesligan", "Allsvenskan", "Laliga"});
        categoryComboBox.setBounds(150, 80, 200, 30);
        frame.add(categoryComboBox);

        startButton = new JButton("Start Quiz");
        startButton.setBounds(150, 130, 100, 40);
        frame.add(startButton);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String playerName = nameField.getText();
                String selectedCategory = (String) categoryComboBox.getSelectedItem();
                quizController.onStartQuiz(playerName, selectedCategory);
                frame.dispose(); // Stäng inloggningssidan när quizet börjar
            }
        });

        frame.setVisible(true);
    }

    /**
     * @Author Ali Farhan
     */
    public Component getFrame() {
        return frame;
    }
}
