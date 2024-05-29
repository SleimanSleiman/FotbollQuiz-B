package Control;


import Model.Player;
import Model.Question;
import Model.QuestionHandler;
import Model.Music;
import View.LoginGUI;
import View.QuizGUI;
import View.ResultGUI;
import java.util.*;
import javax.swing.*;
import java.io.*;
import java.util.List;


public class QuizController {
    private QuizGUI quizGUI;
    private QuestionHandler quiz;
    private Player player;


    private String lastSelectedCategory; //@author Ali Farhan
    private Music backgroundMusic;
    private boolean quizEnded = false;




    /**
     * Creates a new QuizController instance and displays the login window.
     *
     * @author Manar Almaliki, Karam Kallab, Sleiman Sleiman, Ali Farhna
     */


    public QuizController() {


        backgroundMusic = new Music("backgroundMusic.wav");
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                LoginGUI loginGUI = new LoginGUI(QuizController.this);
            }
        });
    }


    /**
     * Starts the quiz with the player's name and selected category.
     *
     * @param playerName The name of the player.
     * @param category   The selected category for the questions.
     * @author Manar Almaliki, Karam Kallab, Ali Farhan
     */




    public void onStartQuiz(String playerName, String category) {
        this.lastSelectedCategory = category; // @author Ali Farhan
        LoginGUI loginGUI = null; // Stäng inloggningssidan när quizet börjar
        List<Question> questions = generateQuestionsForCategory(category);
        player = new Player(playerName, 3);
        quiz = new QuestionHandler(questions);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                quizGUI = new QuizGUI(QuizController.this);
                showNextQuestion();
            }
        });
    }




    /**
     * Sends the player's answer and updates the score and lives.
     *
     * @param selectedOption The selected answer option.
     * @author Manar Almaliki, Karam Kallab, Ali Farhan
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
     * Generates questions based on the selected category.
     *
     * @param category The selected category.
     * @return A list of questions for the category.
     * @author Sleiman and Elias
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
     * Displays the next question in the quiz.
     *
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
     * Ends the quiz and displays the result.
     *
     * @author Ali Farhan
     */


    private void endQuiz() {
        if (quizEnded) return; // Kontrollera om quizen redan har avslutats
        quizEnded = true; // Sätt flaggan till true


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
     * Reads questions from a file for a league.
     *
     * @return A list of questions for the league.
     * @author Elias and Sleiman
     */


    private List<Question> generateCategory1Questions() {
        return readQuestionsFromFile("Bundesliga.txt");
    }


    private List<Question> generateCategory2Questions() {
        return readQuestionsFromFile("Premier_League.txt");
    }


    private List<Question> generateCategory3Questions() {
        return readQuestionsFromFile("Laliga.txt");
    }


    private List<Question> generateCategory4Questions() {
        return readQuestionsFromFile("SerieA.txt");
    }


    private List<Question> generateCategory5Questions() {
        return readQuestionsFromFile("Ligue1.txt");
    }




    /**
     * Reads questions from a specified file.
     *
     * @param filename The name of the file containing questions.
     * @return A list of questions from the file.
     * @author Sleiman Sleiman & Elias Celayir & Karam Kallab
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
                    questions.add(new Question(questionText, options, correctAnswerIndex, score));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Collections.shuffle(questions);
        return questions;
    }


    /**
     * Checks the player's status in the game.
     *
     * @author Ali Farhan and Elias Celayir
     */
    private void checkPlayerStatus() {
        if (player.getLives() <= 0) {
            endQuiz();
        }
    }


    /**
     * Restarts the game and resets the player's score and lives.
     *
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


                // Reset the flag to allow a new quiz round
                quizEnded = false;


                // Create and display the LoginGUI again
                LoginGUI loginGUI = new LoginGUI(QuizController.this);
                loginGUI.getFrame().setVisible(true);
            }
        });
    }


    /**
     * Handles the timeout event and moves to the next question or ends the quiz.
     *
     * @author Ali Farhan
     */
    public void handleTimeOut() {
        if (quizEnded) return;


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
            quizGUI.updateLives(player.getLives());
        }
    }


    /**
     * Reads the leaderboard from a file.
     *
     * @param filename The name of the file containing the leaderboard.
     * @return A list of leaderboard entries.
     * @throws IOException If an error occurs while reading from the file.
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
                    if (parts.length == 2) {
                        entries.add(new String[]{parts[0], parts[1].trim()});
                    }
                }
            }
        }
        return entries;
    }


    /**
     * Saves the leaderboard to the text file. The method sorts the list and saves the top 5
     * scores to the file.
     *
     * @param entries  The leaderboard entries to be saved.
     * @param filename The name of the file to save the leaderboard to.
     * @throws IOException If an error occurs while writing to the file.
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
     * Saves the player's score to the leaderboard, it works for every category and saves
     * the top 5 scores to the file.
     *
     * @author Ali Farhan and Elias Celayir
     */
    public void saveScoreToLeaderboard() {
        String category = lastSelectedCategory;
        String filename = "leaderboard_" + category + ".txt";


        try {
            List<String[]> entries = readLeaderboard(filename);
            entries.add(new String[]{player.getName(), String.valueOf(player.getScore())});
            SaveLeaderboard(entries, filename);
        } catch (IOException e) {
            System.err.println("Error writing to the leaderboard file: " + e.getMessage());
        }
    }


    /**
     * Increases the volume of the background music.
     *
     * @author Ali Farhan
     */
    public void increaseVolume() {
        if (backgroundMusic != null) {
            backgroundMusic.increaseVolume();
        }
    }


    /**
     * Decreases the volume of the background music.
     *
     * @author Ali Farhan
     */
    public void decreaseVolume() {
        if (backgroundMusic != null) {
            backgroundMusic.decreaseVolume();
        }
    }


    /**
     * Mutes the background music.
     *
     * @author Ali Farhan
     */
    public void mute() {
        if (backgroundMusic != null) {
            backgroundMusic.mute();
        }
    }


    /**
     * Retrieves the last selected category.
     *
     * @return The last selected category.
     * @uthor Elias Celayir
     */
    public String getLastSelectedCategory() {
        return lastSelectedCategory;
    }
}
