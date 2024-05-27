package Model;

public class Player {
    private String name;
    private int score;
    private int lives;
    private int correctAnswers;

    /**
     * Skapar en ny spelare med angivet namn och antal liv.
     * Startpoängen och antalet korrekta svar sätts till 0.
     *
     * @param name  Namnet på spelaren.
     * @param lives Antalet liv som spelaren startar med.
     * @author Manar Almaliki och Karam Kallab
     */
    public Player(String name, int lives) {
        this.name = name;
        this.score = 0;
        this.lives = lives;
        this.correctAnswers = 0;
    }

    /**
     * Returnerar spelarens namn.
     *
     * @return Namnet på spelaren.
     * @author Manar Almaliki och Karam Kallab
     */
    public String getName() {
        return name;
    }

    /**
     * Returnerar spelarens nuvarande poäng.
     *
     * @return Spelarens poäng.
     * @author Manar och Karam
     */
    public int getScore() {
        return score;
    }

    /**
     * Ökar spelarens poäng med angivet antal poäng och ökar antalet korrekta svar.
     *
     * @param points Antalet poäng att lägga till spelarens nuvarande poäng.
     * @author Manar och Karam
     */
    public void increaseScore(int points) {
        score += points;
        correctAnswers++;
    }


    /**
     * Returnerar spelarens återstående antal liv.
     *
     * @return Antalet liv som spelaren har kvar.
     * @author Manar och Karam
     */
    public int getLives() {
        return lives;
    }

    /**
     * Minskar spelarens antal liv med ett.
     *
     * @author Manar och Karam
     */
    public void decreaseLives() {
        lives--;
    }


    /**
     * Returnerar antalet korrekta svar som spelaren har gett.
     *
     * @return Antalet korrekta svar.
     * @author Ali Farhan
     */
    public int getCorrectAnswers() {
        return correctAnswers;
    }

    /**
     * Återställer spelarens poäng till 0.
     *
     * @author Ali Farhan
     */
    public void resetScore() {
        score = 0;
    }

    /**
     * Återställer spelarens antal liv till ett nytt angivet värde.
     *
     * @param newLives Det nya antalet liv som spelaren ska ha.
     * @author Ali Farhan
     */
    public void resetLives(int newLives) {
        lives =  newLives;
    }
}