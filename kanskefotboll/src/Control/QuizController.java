package Control;

import Model.Player;
import Model.Question;
import Model.Quiz;
import Model.Music;
import View.LoginGUI;
import View.QuizGUI;
import View.ResultGUI;

import java.util.*;
import javax.swing.*;
import java.io.*;
import java.util.List;

public class QuizController {
    private LoginGUI loginGUI;
    private QuizGUI quizGUI;
    private Quiz quiz;
    private Player player;
    private String lastSelectedCategory; //@author Ali Farhan
    private Music backgroundMusic;



    /**
     * @author Manar Almaliki, Karam Kallab, Sleiman Sleiman, Ali Farhna
     */
    public QuizController() {

        backgroundMusic = new Music("backgroundMusic.wav");
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                loginGUI = new LoginGUI(QuizController.this);
            }
        });
    }

    /**
     * @author Manar Almaliki, Karam Kallab, Ali Farhan
     */

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


    /**
     * @author Manar Almaliki, Karam Kallab, Ali Farhan mindre justeringar i metoden
     */
    public void submitAnswer(int selectedOption) {
        Question currentQuestion = quiz.getCurrentQuestion();

        if (currentQuestion != null) {
            quizGUI.resetTimer();
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



    /**
     * @author Sleiman och Elias
     */
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
        }
    }


    /**
     * @author Manar, Karam Kallab, Ali Farhan
     */

    private void showNextQuestion() {
        Question currentQuestion = quiz.getCurrentQuestion();
        if (currentQuestion != null) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    quizGUI.showQuestion(currentQuestion);
                    quizGUI.resetTimer();
                }
            });
        } else {
            endQuiz();
        }
    }


    /**
     * @author Ali Farhan
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



    /**
     * @author Elias och Sleiman
     */
    private List<Question> generateCategory1Questions() { return readQuestionsFromFile("Bundesliga.txt"); }
    private List<Question> generateCategory2Questions() {
        return readQuestionsFromFile("Premier_League.txt");
    }
    private List<Question> generateCategory3Questions() { return readQuestionsFromFile("Laliga.txt"); }
    private List<Question> generateCategory4Questions() { return readQuestionsFromFile("SerieA.txt"); }
    private List<Question> generateCategory5Questions() { return readQuestionsFromFile("Ligue1.txt"); }



    /**
     * @author Sleiman Sleiman & Elias Celayir
     */
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

                    questions.add(new Question(questionText, options, correctAnswerIndex, score, imagePath));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Collections.shuffle(questions);
        return questions;
    }

    /**
     * Kontrollera spelarens status i spelet
     * @author Ali Farhan och Elias Celayir
     */
    private void checkPlayerStatus(){
        if(player.getLives() <= 0){
            endQuiz();
        }
    }
    
    /**
     * @author Ali Farhan
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
     * Metod som läser in läser in leaderboard från textfil.
     * @author Elias Celayir
     */
    private List<String[]> readLeaderboard(String filename) throws IOException {
        List<String[]> entries = new ArrayList<>();
        File file = new File(filename);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(":");
                    if(parts.length == 2) {
                        entries.add(new String[] {parts[0], parts[1].trim()});
                    }
                }
            }
        }
        return entries;
    }

    /**
     * Metod som sparar leaderboard till textfilen. Metoden sorterar listan och sparar de 5 bästa resultaten till filen.
     * @author Elias Celayir
     */
    private void SaveLeaderboard(List<String[]> entries, String filename) throws IOException {
        Collections.sort(entries, new Comparator<String[]>() {
            @Override
            public int compare(String[] entry1, String[] entry2) {
                int score1 = Integer.parseInt(entry1[1]);
                int score2 = Integer.parseInt(entry2[1]);
                return Integer.compare(score2, score1);
            }
        });
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (int i = 0; i < Math.min(5, entries.size()); i++) {
                writer.write(entries.get(i)[0] + ": " + entries.get(i)[1]);
                writer.newLine();
            }
        }
    }



    /**
     * Metod som sparar spelarens resultat till leaderboard, den funkar till varje kategori och sparar de 5 bästa resultaten till filen.
     * @author Ali Farhan och Elias Celayir
     */
    public void saveScoreToLeaderboard() {
        String category = lastSelectedCategory;
        String filename = "leaderboard_" + category + ".txt";

        try {
            List<String[]> entries = readLeaderboard(filename);
            entries.add(new String[] {player.getName(), String.valueOf(player.getScore())});
            SaveLeaderboard(entries, filename);
        } catch (IOException e) {
            System.err.println("Error writing to the leaderboard file: " + e.getMessage());
        }
    }


    /**
     * @author Ali Farhan
     */
    public void increaseVolume() {
        if (backgroundMusic != null) {
            backgroundMusic.increaseVolume();
        }
    }

    /**
     * @author Ali Farhan
     */

    public void decreaseVolume() {
        if (backgroundMusic != null) {
            backgroundMusic.decreaseVolume();
        }
    }

    /**
     * @author Ali Farhan
     */

    public void mute() {
        if (backgroundMusic != null) {
            backgroundMusic.mute();
        }
    }

    public String getLastSelectedCategory() {
        return lastSelectedCategory;
    }


}
