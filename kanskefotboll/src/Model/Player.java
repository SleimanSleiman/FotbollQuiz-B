package Model;

public class Player {
    private String name;
    private int score;
    private int lives;
    private int correctAnswers;

    /**
     * @author Manar och Karam
     */
    public Player(String name, int lives) {
        this.name = name;
        this.score = 0;
        this.lives = lives;
        this.correctAnswers = 0;
    }

    /**
     * @author Manar och Karam
     */
    public String getName() {
        return name;
    }

    /**
     * @author Manar och Karam
     */
    public int getScore() {
        return score;
    }

    /**
     * @author Manar och Karam
     */
    public void increaseScore(int points) {
        score += points;
        correctAnswers++;
    }

    /**
     * @author Manar och Karam
     */

    public int getLives() {
        return lives;
    }

    /**
     * @author Manar och Karam
     */
    public void decreaseLives() {
        lives--;
    }


    /**
     * @author Ali Farhan
     */

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    /**
     * @author Ali Farhan
     */

    public void resetScore() {
        score = 0;
    }

    /**
     * @author Ali Farhan
     */
    public void resetLives(int newLives) {
        lives =  newLives;
    }
}