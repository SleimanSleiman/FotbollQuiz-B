package Model;

public class Question {
    private String text;
    private String[] options;
    private int correctAnswerIndex;
    private int score;
    private String imagePath;

    /**
     * @author Manar och Karam
     */
    public Question(String text, String[] options, int correctAnswerIndex, int score, String imagePath) {
        this.text = text;
        this.options = options;
        this.correctAnswerIndex = correctAnswerIndex;
        this.score = score;
        this.imagePath = imagePath;
    }

    /**
     * @author Manar och Karam
     */
    public String getText() {
        return text;
    }

    /**
     * @author Manar och Karam
     */
    public String[] getOptions() {
        return options;
    }

    /**
     * @author Manar och Karam
     */
    public int getCorrectAnswerIndex() {
        return correctAnswerIndex;
    }

    /**
     * @author Manar och Karam
     */
    public int getScore() {
        return score;
    }

    /**
     * @Author Elias Celayir
     */
    public String getImagePath() {
        return imagePath;
    }
}