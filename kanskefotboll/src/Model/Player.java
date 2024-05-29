package Model;


public class Player {
    private String name;
    private int score;
    private int lives;
    private int correctAnswers;


    /**
     * Creates a new player with the specified name and number of lives.
     * The starting score and number of correct answers are set to 0.
     *
     * @param name  The name of the player.
     * @param lives The number of lives the player starts with.
     * @author Manar Almaliki and Karam Kallab
     */
    public Player(String name, int lives) {
        this.name = name;
        this.score = 0;
        this.lives = lives;
        this.correctAnswers = 0;
    }


    /**
     * Returns the name of the player.
     *
     * @return The name of the player.
     * @uthor Manar Almaliki and Karam Kallab
     */
    public String getName() {
        return name;
    }


    /**
     * Returns the player's current score.
     *
     * @return The player's score.
     * @uthor Manar and Karam
     */
    public int getScore() {
        return score;
    }


    /**
     * Increases the player's score by the specified number of points and increments the number of correct answers.
     *
     * @param points The number of points to add to the player's current score.
     * @uthor Manar and Karam
     */
    public void increaseScore(int points) {
        score += points;
        correctAnswers++;
    }


    /**
     * Returns the player's remaining number of lives.
     *
     * @return The number of lives the player has remaining.
     * @uthor Manar and Karam
     */
    public int getLives() {
        return lives;
    }


    /**
     * Decreases the player's number of lives by one.
     *
     * @uthor Manar and Karam
     */
    public void decreaseLives() {
        lives--;
    }


    /**
     * Returns the number of correct answers the player has given.
     *
     * @return The number of correct answers.
     * @uthor Ali Farhan
     */
    public int getCorrectAnswers() {
        return correctAnswers;
    }


    /**
     * Resets the player's score to 0.
     *
     * @uthor Ali Farhan
     */
    public void resetScore() {
        score = 0;
    }


    /**
     * Resets the player's number of lives to a new specified value.
     *
     * @param newLives The new number of lives the player should have.
     * @uthor Ali Farhan
     */
    public void resetLives(int newLives) {
        lives = newLives;
    }
}
