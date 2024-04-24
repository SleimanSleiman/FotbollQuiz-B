package Control;

import Model.Player;
import Model.Question;
import View.LoginGUI;
import View.QuizGUI;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import java.io.*;

public class QuizController {
    private LoginGUI loginGUI;
    private QuizGUI quizGUI;
    private Quiz quiz;
    private Player player;
    /*
    diasondas
     */
    public QuizController() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                loginGUI = new LoginGUI(QuizController.this);
            }
        });
    }

    public void onStartQuiz(String playerName, String category) {
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
            case "Bundesligan":
                return generateCategory1Questions();
            case "Allsvenskan":
                return generateCategory2Questions();
            case "Laliga":
                return generateCategory3Questions();
            default:
                return null;
        }
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

    private void endQuiz() {
        // Implementera logik för att avsluta quizzet och visa resultatet
    }

    private List<Question> generateCategory1Questions() {
        return readQuestionsFromFile("Bundesligan.txt");
    }

    private List<Question> generateCategory2Questions() {
        return readQuestionsFromFile("Allsvenskan.txt");
    }

    private List<Question> generateCategory3Questions() {
        return readQuestionsFromFile("Laliga.txt");
    }

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
    }

    private void checkPlayerStatus(){
        if(player.getLives() <= 0){
            showLossDialog();
        }
    }

    private void showLossDialog() {
     int choice = JOptionPane.showConfirmDialog(null, "You have lost, do you wanna play again?", "Game over", JOptionPane.YES_NO_OPTION);
     if(choice == JOptionPane.YES_OPTION){
         restartGame();
     } else{
         System.exit(0);
     }
     }

    private void restartGame() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                loginGUI = new LoginGUI(QuizController.this);
                quizGUI.getFrame().setVisible(false);
            }
        });
    }


    /**
     * @author Ali Farhan
     */
    public void handleTimeOut() {
        if(quiz.getCurrentQuestion() != null) {
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
        }
    }
}
