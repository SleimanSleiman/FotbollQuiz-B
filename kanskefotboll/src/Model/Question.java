package Model;


public class Question {
    private String text;
    private String[] options;
    private int correctAnswerIndex;
    private int score;


    /**
     * Creates a new question with the specified text, options, index of correct answer, and score.
     *
     * @param text              The text of the question.
     * @param options           The options for the question.
     * @param correctAnswerIndex The index of the correct answer in the options array.
     * @param score             The score for the question.
     * @author Manar Almaliki and Karam Kallab
     */
    public Question(String text, String[] options, int correctAnswerIndex, int score) {
        this.text = text;
        this.options = options;
        this.correctAnswerIndex = correctAnswerIndex;
        this.score = score;
    }


    /**
     * Returns the text of the question.
     *
     * @return The text of the question.
     * @uthor Manar Almaliki and Karam Kallab
     */
    public String getText() {
        return text;
    }


    /**
     * Returns the options for the question.
     *
     * @return An array containing the options for the question.
     * @uthor Manar Almaliki and Karam Kallab
     */
    public String[] getOptions() {
        return options;
    }


    /**
     * Returns the index of the correct answer in the options array.
     *
     * @return The index of the correct answer.
     * @uthor Manar Almaliki and Karam Kallab
     */
    public int getCorrectAnswerIndex() {
        return correctAnswerIndex;
    }


    /**
     * Returns the score for the question.
     *
     * @return The score for the question.
     * @uthor Manar Almaliki and Karam Kallab
     */
    public int getScore() {
        return score;
    }
}
