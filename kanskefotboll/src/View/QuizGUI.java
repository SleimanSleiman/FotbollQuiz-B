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

    private JButton increaseVolumeButton;  // @author Ali Farhan
    private JButton decreaseVolumeButton;  // @author Ali Farhan
    private JButton muteButton;            // @author Ali Farhan


    /**
     * @author Manar, Karam, Sleiman, Elias, Ali
     */
    public QuizGUI(QuizController quizController) {
        this.quizController = quizController;
        UIManager.put("RadioButton.focus", new ColorUIResource(new Color(0xC0FFC1)));

        frame = new JFrame("Quiz Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(850, 450);

        // Ladda bakgrundsbilden
        ImageIcon backgroundIcon = new ImageIcon(getClass().getResource("/images/background.png"));
        BackgroundPanel backgroundPanel = new BackgroundPanel(backgroundIcon.getImage());
        backgroundPanel.setLayout(null);
        frame.setContentPane(backgroundPanel);

        questionLabel = new JLabel();
        questionLabel.setBounds(50, 50, 800, 30);
        frame.add(questionLabel);

        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setBounds(400, 20, 200, 30);
        frame.add(scoreLabel);

        livesLabel = new JLabel("Lives: 3");
        livesLabel.setBounds(50, 20, 200, 30);
        frame.add(livesLabel);

        timerLabel = new JLabel("Time: 30"); // @author Ali Farhan
        timerLabel.setBounds(700,20, 100, 30); // @author Ali Farhan
        frame.add(timerLabel); // @author Ali Farhan

        optionButtons = new JRadioButton[4];
        buttonGroup = new ButtonGroup();

        for (int i = 0; i < optionButtons.length; i++) {
            optionButtons[i] = new JRadioButton();
            optionButtons[i].setBounds(50, 100 + i * 50, 500, 30);
            optionButtons[i].setForeground(Color.BLACK);
            optionButtons[i].setBackground(new Color(0xC0FFC1));
            frame.add(optionButtons[i]);
            buttonGroup.add(optionButtons[i]);


           //@author Ali Farhan
            optionButtons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    submitButton.setEnabled(true);
                }
            });

        }

        submitButton = new JButton("Submit");
        submitButton.setBounds(300, 300, 100, 40);
        submitButton.setBackground(new Color(255, 255, 255));
        submitButton.setForeground(Color.BLACK);
        submitButton.setEnabled(false); //@author Ali Farhan
        frame.add(submitButton);


        exitButton = new JButton("Exit");
        exitButton.setBounds(450,300,100,40);
        exitButton.setBackground(new Color(255, 255, 255));
        exitButton.setForeground(Color.BLACK);
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
                quizController.submitAnswer(selectedOption); //@author Ali Farhan
                submitButton.setEnabled(false); //@Author Ali Farhan
                resetTimer(); // @author Ali Farhan
            }
        });


        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0); //Avsluta programmet när kanppen klickas.
            }
        });


        increaseVolumeButton = new JButton("+ Volume");                             // @author Ali Farhan
        increaseVolumeButton.setBounds(750, 300, 90, 30);           // @author Ali Farhan
        frame.add(increaseVolumeButton);                                                // @author Ali Farhan

        decreaseVolumeButton = new JButton("- Volume");                         // @author Ali Farhan
        decreaseVolumeButton.setBounds(750, 340, 90, 30);       // @author Ali Farhan
        frame.add(decreaseVolumeButton);                                        // @author Ali Farhan

        muteButton = new JButton("Mute");                               // @author Ali Farhan
        muteButton.setBounds(750, 380, 90, 30);           // @author Ali Farhan
        frame.add(muteButton);                                               // @author Ali Farhan

        increaseVolumeButton.addActionListener(e -> quizController.increaseVolume());       // @author Ali Farhan
        decreaseVolumeButton.addActionListener(e -> quizController.decreaseVolume());       // @author Ali Farhan
        muteButton.addActionListener(e -> quizController.mute());                           // @author Ali Farhan

        setupTimer(); // @author Ali Farhan

        frame.setLocationRelativeTo(null); // Centrera GUI @Author Ali Farhan & Elias Celyir
        frame.setVisible(true);

    }

    public void showQuestion(Question question) {
        questionLabel.setText(question.getText());
        String[] options = question.getOptions();

        buttonGroup.clearSelection(); // Ta bort tidigare val innan nästa fråga visas @author Ali Farhan
        submitButton.setEnabled(false); // @author Ali Farhan


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


    /**
     * @author Manar och Karam
     */
    public void updateScore(int score) {
        scoreLabel.setText("Score: " + score);
    }


    /**
     * @author Manar och Karam
     */
    public void updateLives(int lives) {
        livesLabel.setText("Lives: " + lives);
    }


    /**
     * @author Manar och Karam
     */
    public JFrame getFrame() {
        return frame;
     }
}
