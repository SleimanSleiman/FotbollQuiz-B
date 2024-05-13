package Model;

import Model.Question;

import java.util.List;

public class Quiz {
    private List<Question> questions;
    private int currentQuestionIndex;

    /**
     * @author Manar och Karam
     */
    public Quiz(List<Question> questions) {
        this.questions = questions;
        this.currentQuestionIndex = 0;
    }

    /**
     * @author Manar och Karam
     */
    public boolean hasNextQuestion() {
        return currentQuestionIndex < questions.size();
    }

    /**
     * @author Manar och Karam
     */
    public Question getCurrentQuestion() {
        if (hasNextQuestion()) {
            return questions.get(currentQuestionIndex);
        } else {
            return null;
        }
    }

    /**
     * @author Manar och Karam
     */
    public void moveToNextQuestion() {
        currentQuestionIndex++;
    }

    /**
     * @Author Ali Farhan
     */
    public List<Question> getQuestions() {
        return questions;
    }

}


