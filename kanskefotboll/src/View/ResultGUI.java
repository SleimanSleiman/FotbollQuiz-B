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
 * @author Ali Farhan
 */
public class ResultGUI {
    private JFrame frame;
    private JLabel scoreLabel;
    private JButton restartButton;
    private JButton exitButton;
    private JButton leaderboardButton;
    private QuizController quizController;

    /**
     * @author Ali Farhan och Elias Celayir
     */

    public ResultGUI(QuizController quizController, int score, int correctAnswers, int totalQuestions, String playerName) {
        this.quizController = quizController;

        frame = new JFrame("Quiz Result for " + playerName);
        frame.setSize(400, 250);
        frame.setLayout(null);
        frame.getContentPane().setBackground(new Color(0xC0FFC1)); // Set background color here
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        scoreLabel = new JLabel("Your Score: " + score + " / " + totalQuestions);
        scoreLabel.setBounds(140, 40, 200, 30);
        frame.add(scoreLabel);

        JLabel correctLabel = new JLabel("Correct answers: " + correctAnswers + " / " + totalQuestions);
        correctLabel.setBounds(120, 60, 200, 30);
        frame.add(correctLabel);

        restartButton = new JButton("Play again");
        restartButton.setBounds(90, 120, 100, 30);
        restartButton.setBackground(new Color(0xFFFFFF));
        restartButton.setForeground(new Color(0x0C0C0C));
        frame.add(restartButton);

        exitButton = new JButton("Avsluta");
        exitButton.setBounds(200, 120, 100, 30);
        exitButton.setBackground(new Color(0xFFFFFF));
        frame.add(exitButton);

        leaderboardButton = new JButton("Show Leaderboard");
        leaderboardButton.setBounds(100, 170, 190, 30);
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
                displayLeaderboard();
            }
        });

        frame.setLocationRelativeTo(null); // Centrera GUI @Author Ali Farhan & Elias Celyir

        frame.setVisible(true);
    }



    /**
     * @author Ali Farhan
     */
    public void displayLeaderboard() {
        JFrame leaderboardFrame = new JFrame("Leaderboard");
        JTextArea textArea = new JTextArea(10, 30);
        textArea.setEditable(false);

        try (BufferedReader reader = new BufferedReader(new FileReader("leaderboard.txt"))) {
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
