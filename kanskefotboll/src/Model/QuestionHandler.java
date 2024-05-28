package Model;

import java.util.List;

public class QuestionHandler {
    private List<Question> questions;
    private int currentQuestionIndex;

    /**
     * Skapar ett nytt Quiz-objekt med en lista av frågor och sätter den aktuella frågeindex till 0.
     *
     * @param questions Listan av frågor för quizet.
     * @author Manar Almaliki och Karam Kallab
     */
    public QuestionHandler(List<Question> questions) {
        this.questions = questions;
        this.currentQuestionIndex = 0;
    }

    /**
     * Kontrollerar om det finns fler frågor kvar i quizet.
     *
     * @return true om det finns fler frågor, annars false.
     * @author Manar Almaliki och Karam Kallab
     */
    public boolean hasNextQuestion() {
        return currentQuestionIndex < questions.size();
    }

    /**
     * Returnerar den aktuella frågan om det finns fler frågor kvar.
     *
     * @return Den aktuella frågan eller null om det inte finns fler frågor.
     * @author Manar Almaliki och Karam Kallab
     */
    public Question getCurrentQuestion() {
        if (hasNextQuestion()) {
            return questions.get(currentQuestionIndex);
        } else {
            return null;
        }
    }

    /**
     * Flyttar till nästa fråga i quizet genom att öka det aktuella frågeindexet med ett.
     *
     * @author Manar Almaliki och Karam Kallab
     */
    public void moveToNextQuestion() {
        currentQuestionIndex++;
    }

    /**
     * Returnerar listan av alla frågor i quizet.
     *
     * @return Listan av frågor.
     * @author Ali Farhan
     */
    public List<Question> getQuestions() {
        return questions;
    }

}


