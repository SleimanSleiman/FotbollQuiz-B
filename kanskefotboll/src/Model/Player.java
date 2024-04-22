package Model;

public class Player {
    private String name;
    private int score;
    private int lives;

    public Player(String name, int lives) {
        this.name = name;
        this.score = 0;
        this.lives = lives;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public void increaseScore(int points) {
        score += points;
    }

    public int getLives() {
        return lives;
    }

    public void decreaseLives() {
        lives--;
    }
}