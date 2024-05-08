package View;

import Control.QuizController;
import Model.Question;
import Model.Music;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import javax.swing.plaf.ColorUIResource;

public class QuizGUI {
    private JFrame frame;
    private JLabel questionLabel;
    private JLabel scoreLabel;
    private JRadioButton[] optionButtons;
    private JButton submitButton;
    private JLabel livesLabel;
    private JLabel timerLabel; // @author Ali Farhan
    private JButton exitButton; //@author Manar Majid Hasan Al-Maliki
    private ButtonGroup buttonGroup;

    private QuizController quizController;
    private Timer timer; // @author Ali Farhan
    private int timeLeft = 30; // @author Ali Farhan

    public QuizGUI(QuizController quizController) {
        this.quizController = quizController;
        UIManager.put("RadioButton.focus", new ColorUIResource(new Color(0xC0FFC1)));

        frame = new JFrame("Quiz Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(850, 400);
        frame.setLayout(null);
        frame.getContentPane().setBackground(new Color(192, 255, 193));
        questionLabel = new JLabel();
        questionLabel.setBounds(50, 50, 800, 30);
        frame.add(questionLabel);

        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setBounds(400, 20, 200, 30);
        frame.add(scoreLabel);

        livesLabel = new JLabel("Lives: 3");
        livesLabel.setBounds(50, 20, 200, 30);
        frame.add(livesLabel);

        timerLabel = new JLabel("Tid: 30"); // @author Ali Farhan
        timerLabel.setBounds(700,20, 100, 30); // @author Ali Farhan
        frame.add(timerLabel); // @author Ali Farhan

        optionButtons = new JRadioButton[4];
        buttonGroup = new ButtonGroup();

        for (int i = 0; i < optionButtons.length; i++) {
            optionButtons[i] = new JRadioButton();
            optionButtons[i].setBounds(50, 100 + i * 50, 500, 30);
            optionButtons[i].setForeground(Color.WHITE);
            optionButtons[i].setBackground(Color.black);
            frame.add(optionButtons[i]);
            buttonGroup.add(optionButtons[i]);
        }

        submitButton = new JButton("Submit");
        submitButton.setBounds(250, 300, 100, 40);
        submitButton.setBackground(new Color(12, 12, 12));
        submitButton.setForeground(Color.WHITE);
        frame.add(submitButton);


        exitButton = new JButton("Avsluta");
        exitButton.setBounds(370,300,100,40);
        exitButton.setBackground(new Color(12, 12, 12));
        exitButton.setForeground(Color.WHITE);
        frame.add(exitButton);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedOption = -1;
                for (int i = 0; i < optionButtons.length; i++) {
                    if (optionButtons[i].isSelected()) {
                        selectedOption = i;
                        break;
                    }
                }
                quizController.submitAnswer(selectedOption);
                resetTimer(); // @author Ali Farhan
            }
        });

        /*
        En metod som gör så att programmet avslutat när användaren klickar på "avsluta" knappen.
        @author Manar Majid Hasan Al-Maliki
         */
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0); //Avsluta programmet när kanppen klickas.
            }
        });

        setupTimer(); // @author Ali Farhan
        frame.setVisible(true);
    }

    public void showQuestion(Question question) {
        questionLabel.setText(question.getText());
        String[] options = question.getOptions();
        for (int i = 0; i < optionButtons.length; i++) {
            optionButtons[i].setText(options[i]);
            optionButtons[i].setVisible(true);
        }
        resetTimer(); // @author Ali Farhan
    }

    public void hideOptions() {
        for (int i = 0; i < optionButtons.length; i++) {
            optionButtons[i].setVisible(false);
        }
    }


    /**
     * @author Ali Farhan
     */
    private void setupTimer() {
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeLeft--;
                timerLabel.setText("Time left: " + timeLeft);
                if (timeLeft == 0) {
                    timer.stop();
                    quizController.handleTimeOut();
                }
            }
        });
    }


    /**
     * @author Ali Farhan
     */
    public void resetTimer() {
        timeLeft = 30;
        timerLabel.setText("Time left: " + timeLeft);
        timer.start();
    }


    public void updateScore(int score) {
        scoreLabel.setText("Score: " + score);
    }

    public void updateLives(int lives) {
        livesLabel.setText("Lives: " + lives);
    }
    public JFrame getFrame() {
        return frame;
     }
}
