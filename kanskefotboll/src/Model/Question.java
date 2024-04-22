package Model;

public class Question {
    private String text;
    private String[] options;
    private int correctAnswerIndex;
    private int score;

    public Question(String text, String[] options, int correctAnswerIndex, int score) {
        this.text = text;
        this.options = options;
        this.correctAnswerIndex = correctAnswerIndex;
        this.score = score;
    }


    public String getText() {
        return text;
    }

    public String[] getOptions() {
        return options;
    }

    public int getCorrectAnswerIndex() {
        return correctAnswerIndex;
    }

    public int getScore() {
        return score;
    }
}