package Model;


import java.util.List;


public class QuestionHandler {
    private List<Question> questions;
    private int currentQuestionIndex;


    /**
     * Creates a new Quiz object with a list of questions and sets the current question index to 0.
     *
     * @param questions The list of questions for the quiz.
     * @uthor Manar Almaliki and Karam Kallab
     */
    public QuestionHandler(List<Question> questions) {
        this.questions = questions;
        this.currentQuestionIndex = 0;
    }


    /**
     * Checks if there are more questions remaining in the quiz.
     *
     * @return true if there are more questions, otherwise false.
     * @uthor Manar Almaliki and Karam Kallab
     */
    public boolean hasNextQuestion() {
        return currentQuestionIndex < questions.size();
    }


    /**
     * Returns the current question if there are more questions remaining.
     *
     * @return The current question or null if there are no more questions.
     * @uthor Manar Almaliki and Karam Kallab
     */
    public Question getCurrentQuestion() {
        if (hasNextQuestion()) {
            return questions.get(currentQuestionIndex);
        } else {
            return null;
        }
    }


    /**
     * Moves to the next question in the quiz by incrementing the current question index by one.
     *
     * @uthor Manar Almaliki and Karam Kallab
     */
    public void moveToNextQuestion() {
        currentQuestionIndex++;
    }


    /**
     * Returns the list of all questions in the quiz.
     *
     * @return The list of questions.
     * @uthor Ali Farhan
     */
    public List<Question> getQuestions() {
        return questions;
    }
}
