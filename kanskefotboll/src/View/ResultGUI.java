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
        frame.setSize(850, 450);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Ladda bakgrundsbilden
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

        frame.setLocationRelativeTo(null); // Centrera GUI @Author Ali Farhan & Elias Celyir

        frame.setVisible(true);
    }

    /**
     * Returnerar filnamnet för den valda kategorin
     * @author ELias Celayir
     */
    private String getFilenameForCategory(String category) {
        return "leaderboard_" + category.replaceAll("leaderboard", "_") + ".txt";
    }


    /**
     * Ali gjorde metoden från början. Elias ändrat så den tar in en parameter för att kunna visa leaderboard för vald kategori.
     * @author Ali Farhan och Elias Celayir
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
