package Model;

public class Player {
    private String name;
    private int score;
    private int lives;
    private int correctAnswers;

    public Player(String name, int lives) {
        this.name = name;
        this.score = 0;
        this.lives = lives;
        this.correctAnswers = 0;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public void increaseScore(int points) {
        score += points;
        correctAnswers++;
    }

    public int getLives() {
        return lives;
    }

    public void decreaseLives() {
        lives--;
    }
    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public void resetScore() {
        score = 0;
    }

    public void resetLives(int newLives) {
        lives =  newLives;
    }
}