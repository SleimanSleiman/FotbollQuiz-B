package Control;

import Model.Question;

import java.util.List;

public class Quiz {
    private List<Question> questions;
    private int currentQuestionIndex;

    public Quiz(List<Question> questions) {
        this.questions = questions;
        this.currentQuestionIndex = 0;
    }

    public boolean hasNextQuestion() {
        return currentQuestionIndex < questions.size();
    }

    public Question getCurrentQuestion() {
        if (hasNextQuestion()) {
            return questions.get(currentQuestionIndex);
        } else {
            return null;
        }
    }

    public void moveToNextQuestion() {
        currentQuestionIndex++;
    }
}


