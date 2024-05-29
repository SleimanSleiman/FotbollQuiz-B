package View;


import Control.QuizController;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


/**
 * ResultGUI displays the result of the quiz and provides options to play again, exit, or show the leaderboard.
 * Authors: Ali Farhan, Elias Celayir
 */
public class ResultGUI {
    private JFrame frame;
    private JLabel scoreLabel;
    private JButton restartButton;
    private JButton exitButton;
    private JButton leaderboardButton;
    private QuizController quizController;


    /**
     * Creates a new ResultGUI.
     *
     * @param quizController The controller handling the quiz logic.
     * @param score The player's score.
     * @param correctAnswers The number of correct answers.
     * @param totalQuestions The total number of questions.
     * @param playerName The player's name.
     * Authors: Ali Farhan, Elias Celayir
     */
    public ResultGUI(QuizController quizController, int score, int correctAnswers, int totalQuestions, String playerName) {
        this.quizController = quizController;


        frame = new JFrame("Quiz Result for " + playerName);
        frame.setSize(850, 450);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        ImageIcon backgroundIcon = new ImageIcon(getClass().getResource("/images/background.png"));
        BackgroundPanel backgroundPanel = new BackgroundPanel(backgroundIcon.getImage());
        backgroundPanel.setLayout(null);
        frame.setContentPane(backgroundPanel);

        scoreLabel = new JLabel("Your Score: " + score + " / " + totalQuestions, SwingConstants.CENTER);
        scoreLabel.setBounds(225, 40, 400, 30);
        frame.add(scoreLabel);

        JLabel correctLabel = new JLabel("Correct answers: " + correctAnswers + " / " + totalQuestions, SwingConstants.CENTER);
        correctLabel.setBounds(225, 80, 400, 30);
        frame.add(correctLabel);

        restartButton = new JButton("Play again");
        restartButton.setBounds(325, 150, 200, 40);
        restartButton.setBackground(new Color(0xFFFFFF));
        restartButton.setForeground(new Color(0x0C0C0C));
        frame.add(restartButton);

        exitButton = new JButton("Exit");
        exitButton.setBounds(325, 250, 200, 40);
        exitButton.setBackground(new Color(0xFFFFFF));
        frame.add(exitButton);

        leaderboardButton = new JButton("Show Leaderboard");
        leaderboardButton.setBounds(325, 200, 200, 40);
        leaderboardButton.setForeground(new Color(0x0C0C0C));
        leaderboardButton.setBackground(new Color(0xFFFFFF));
        frame.add(leaderboardButton);

        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                quizController.restartGame();
                frame.dispose();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });


        leaderboardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayLeaderboard(quizController.getLastSelectedCategory());
            }
        });


        frame.setLocationRelativeTo(null);


        frame.setVisible(true);
    }


    /**
     * Returns the filename for the selected category.
     *
     * @param category The category for which the filename is to be returned.
     * @return The filename for the leaderboard for the specified category.
     * Author: Elias Celayir
     */
    private String getFilenameForCategory(String category) {
        return "leaderboard_" + category.replaceAll("leaderboard", "_") + ".txt";
    }


    /**
     * Displays the leaderboard for the selected category.
     *
     * @param category The category for which the leaderboard is to be displayed.
     * Authors: Ali Farhan, Elias Celayir
     */
    public void displayLeaderboard(String category) {
        String filename = getFilenameForCategory(category);
        JFrame leaderboardFrame = new JFrame("Leaderboard - " + category);
        JTextArea textArea = new JTextArea(10, 30);
        textArea.setEditable(false);

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                textArea.append(line + "\n");
            }
        } catch (IOException e) {
            System.err.println("Error reading the leaderboard file: " + e.getMessage());
        }

        leaderboardFrame.add(new JScrollPane(textArea));
        leaderboardFrame.pack();
        leaderboardFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        leaderboardFrame.setVisible(true);
    }
}
