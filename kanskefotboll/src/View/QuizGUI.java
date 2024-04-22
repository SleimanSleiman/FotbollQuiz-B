package View;

import Control.QuizController;
import Model.Question;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QuizGUI {
    private JFrame frame;
    private JLabel questionLabel;
    private JLabel scoreLabel;
    private JRadioButton[] optionButtons;
    private JButton submitButton;
    private JLabel livesLabel;

    private ButtonGroup buttonGroup;

    private QuizController quizController;

    public QuizGUI(QuizController quizController) {
        this.quizController = quizController;

        frame = new JFrame("Quiz Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(null);

        questionLabel = new JLabel();
        questionLabel.setBounds(50, 50, 500, 30);
        frame.add(questionLabel);

        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setBounds(50, 20, 200, 30);
        frame.add(scoreLabel);

        livesLabel = new JLabel("Lives: 1");
        livesLabel.setBounds(50, 80, 200, 30);
        frame.add(livesLabel);

        optionButtons = new JRadioButton[4];
        buttonGroup = new ButtonGroup();

        for (int i = 0; i < optionButtons.length; i++) {
            optionButtons[i] = new JRadioButton();
            optionButtons[i].setBounds(50, 100 + i * 50, 500, 30);
            frame.add(optionButtons[i]);
            buttonGroup.add(optionButtons[i]);
        }

        submitButton = new JButton("Submit");
        submitButton.setBounds(250, 300, 100, 40);
        frame.add(submitButton);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedOption = -1;
                for (int i = 0; i < optionButtons.length; i++) {
                    if (optionButtons[i].isSelected()) {
                        selectedOption = i;
                        break;
                    }
                }
                quizController.submitAnswer(selectedOption);
            }
        });

        frame.setVisible(true);
    }

    public void showQuestion(Question question) {
        questionLabel.setText(question.getText());
        String[] options = question.getOptions();
        for (int i = 0; i < optionButtons.length; i++) {
            optionButtons[i].setText(options[i]);
            optionButtons[i].setVisible(true);
        }
    }

    public void hideOptions() {
        for (int i = 0; i < optionButtons.length; i++) {
            optionButtons[i].setVisible(false);
        }
    }

    public void updateScore(int score) {
        scoreLabel.setText("Score: " + score);
    }

    public void updateLives(int lives) {
        livesLabel.setText("Lives: " + lives);
    }
    public JFrame getFrame() {
        return frame;
    }
}
