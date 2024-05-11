package Control;

import Model.Player;
import Model.Question;
import Model.Quiz;
import View.LoginGUI;
import View.QuizGUI;
import View.ResultGUI;
import View.SettingsGUI;
import Model.Music;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.io.*;

public class QuizController {
    private LoginGUI loginGUI;
    private QuizGUI quizGUI;
    private Quiz quiz;
    private Player player;
    private String lastSelectedCategory; //@author Ali Farhan
    private Music music;


    public QuizController() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                loginGUI = new LoginGUI(QuizController.this);
            }
        });
    }

    public void onStartQuiz(String playerName, String category) {
        this.lastSelectedCategory = category; // @author Ali Farhan
        loginGUI = null; // Stäng inloggningssidan när quizet börjar
        List<Question> questions = generateQuestionsForCategory(category);
        player = new Player(playerName, 3);
        quiz = new Quiz(questions);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                quizGUI = new QuizGUI(QuizController.this);
                showNextQuestion();
            }
        });
    }

    public Music getMusic() {
        return music;
    }
    public void submitAnswer(int selectedOption) {
        Question currentQuestion = quiz.getCurrentQuestion();
        if (currentQuestion != null) {
            if (selectedOption == currentQuestion.getCorrectAnswerIndex()) {

                player.increaseScore(currentQuestion.getScore());
            } else {

                player.decreaseLives();
                checkPlayerStatus();
            }
            quiz.moveToNextQuestion(); // Flytta till nästa fråga
            showNextQuestion();
            quizGUI.updateScore(player.getScore());
            quizGUI.updateLives(player.getLives());
        }
    }


    private List<Question> generateQuestionsForCategory(String category) {
        switch (category) {
            case "Bundesliga":
                return generateCategory1Questions();
            case "Premier League":
                return generateCategory2Questions();
            case "Laliga":
                return generateCategory3Questions();
            case "Serie A":
                return generateCategory4Questions();
            case "Ligue 1":
                return generateCategory5Questions();
            default:
                return null;
        }//@author Sleiman Sleiman
    }

    private void showNextQuestion() {
        Question currentQuestion = quiz.getCurrentQuestion();
        if (currentQuestion != null) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    quizGUI.showQuestion(currentQuestion);
                }
            });
        } else {
            endQuiz();
        }
    }


    /**
     * @Author Ali Farhan
     */
    private void endQuiz() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                if (quizGUI != null) {
                    quizGUI.getFrame().dispose();
                }
                saveScoreToLeaderboard();
                new ResultGUI(QuizController.this, player.getScore(), player.getCorrectAnswers(), quiz.getQuestions().size(), player.getName());
            }
        });
    }



    private List<Question> generateCategory1Questions() { return readQuestionsFromFile("Bundesliga.txt"); }

    private List<Question> generateCategory2Questions() {
        return readQuestionsFromFile("Premier_League.txt");
    }

    private List<Question> generateCategory3Questions() { return readQuestionsFromFile("Laliga.txt"); }
    private List<Question> generateCategory4Questions() { return readQuestionsFromFile("SerieA.txt"); }
    private List<Question> generateCategory5Questions() { return readQuestionsFromFile("Ligue1.txt"); }
//@author Sleiman Sleiman


    private List<Question> readQuestionsFromFile(String filename) {
        List<Question> questions = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Question")) {
                    String questionText = line.substring(line.indexOf(":") + 2);
                    String optionsLine = reader.readLine().substring("Options: ".length());
                    String[] options = optionsLine.split(", ");
                    int correctAnswerIndex = Integer.parseInt(reader.readLine().substring("Correct Answer Index: ".length()));
                    int score = Integer.parseInt(reader.readLine().substring("Score: ".length()));
                    questions.add(new Question(questionText, options, correctAnswerIndex, score));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return questions;
    } //@author Sleiman Sleiman


    /**
     * Kontrollera spelarens status i spelet
     * @Author Ali Farhan och Elias Celyir
     */
    private void checkPlayerStatus(){
        if(player.getLives() <= 0){
            endQuiz();
        }
    }

    private void openSettingsWindow() {
        SettingsGUI settingsGUI = new SettingsGUI(music);
        settingsGUI.displaySettings();
    }


    // Problemet med gamla gui som dyker upp var här, kan tas bort helt efter vi kontrollerat med resterande i gruppen
    // Problemet löst av Ali och Elias
    /*
    private void showLossDialog() {
        JPanel panel = new JPanel();
        JLabel messageLabel = new JLabel("You have lost, do you want to play again?");
        messageLabel.setForeground(new Color(0x0C0C0C));
        panel.add(messageLabel);

        JButton yesButton = new JButton("Yes");
        yesButton.setBackground(new Color(0xFFFFFF));
        yesButton.setForeground(new Color(0x0C0C0C));

        JButton noButton = new JButton("No");
        noButton.setBackground(new Color(0xFFFFFF));
        noButton.setForeground(new Color(0x0C0C0C));


        int choice = JOptionPane.showOptionDialog(null, panel, "Game over", JOptionPane.YES_NO_OPTION,
                JOptionPane.PLAIN_MESSAGE, null, new JButton[]{yesButton, noButton}, null);

        if (choice == JOptionPane.YES_OPTION) {
            restartGame();
        } else {
            endQuiz();
        }
    }
    */





    /**
     * @Author Ali Farhan
     */
    public void restartGame() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                if (quizGUI != null) {
                    quizGUI.getFrame().dispose();
                }

                player.resetScore();
                player.resetLives(3);

                // Skapa och visa LoginGUI igen
                loginGUI = new LoginGUI(QuizController.this);
                loginGUI.getFrame().setVisible(true);
            }
        });
    }


    /**
     * @author Ali Farhan
     */
    public void handleTimeOut() {
        if (quiz.getCurrentQuestion() != null) {
            player.decreaseLives();
            checkPlayerStatus();

            quiz.moveToNextQuestion();
            if (quiz.hasNextQuestion()) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        showNextQuestion();
                    }
                });
            } else {
                endQuiz();
            }
            quizGUI.updateLives(player.getLives()); // Uppdatera GUI med antalet liv
        }
    }


    /**
    * @author Ali Farhan
     */
    private void saveScoreToLeaderboard() {
        String filename = "leaderboard.txt";
        try (FileWriter fw = new FileWriter(filename, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(player.getName() + ": " + player.getScore());
        } catch (IOException e) {
            System.err.println("Error writing to the leaderboard file: " + e.getMessage());
        }
    }




}
