package Model;

public class Question {
    private String text;
    private String[] options;
    private int correctAnswerIndex;
    private int score;

    /**
     * Skapar en ny fråga med den angivna texten, alternativen, indexet för rätt svar och poängen för frågan.
     *
     * @param text  Texten för frågan.
     * @param options  Alternativen för frågan.
     * @param correctAnswerIndex Indexet för det rätta svaret i alternativarrayen.
     * @param score  Poängen för frågan.
     * @author Manar Almaliki och Karam Kallab
     */
    public Question(String text, String[] options, int correctAnswerIndex, int score) {
        this.text = text;
        this.options = options;
        this.correctAnswerIndex = correctAnswerIndex;
        this.score = score;
    }

    /**
     * Returnerar texten för frågan.
     *
     * @return Texten för frågan.
     * @author Manar Almaliki och Karam Kallab
     */
    public String getText() {
        return text;
    }

    /**
     * Returnerar alternativen för frågan.
     *
     * @return En array med alternativen för frågan.
     * @author Manar Almaliki och Karam Kallab
     */
    public String[] getOptions() {
        return options;
    }

    /**
     * Returnerar indexet för det rätta svaret i alternativarrayen.
     *
     * @return Indexet för det rätta svaret.
     * @author Manar Almaliki och Karam Kallab
     */
    public int getCorrectAnswerIndex() {
        return correctAnswerIndex;
    }

    /**
     * Returnerar poängen för frågan.
     *
     * @return Poängen för frågan.
     * @author Manar Almaliki och Karam Kallab
     */
    public int getScore() {
        return score;
    }
}