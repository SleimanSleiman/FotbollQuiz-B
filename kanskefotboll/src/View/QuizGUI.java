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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class QuizGUI {
    private JFrame frame;
    private JTextArea questionArea;
    private JLabel scoreLabel;
    private JButton submitButton;
    private JLabel livesLabel;
    private JLabel timerLabel; // @author Ali Farhan
    private JButton exitButton; //@author Manar Majid Hasan Al-Maliki
    private JPanel[] optionPanels;  // Byt till JPanel för alternativ
    private JLabel[] optionLabels;
    private QuizController quizController;
    private Timer timer; // @author Ali Farhan
    private int timeLeft = 30; // @author Ali Farhan
    private int selectedOption = -1;
    private JButton increaseVolumeButton;  // @author Ali Farhan
    private JButton decreaseVolumeButton;  // @author Ali Farhan
    private JButton muteButton;            // @author Ali Farhan


    /**
     * @author Manar, Karam, Sleiman, Elias, Ali
     */
    public QuizGUI(QuizController quizController) {
        this.quizController = quizController;
        UIManager.put("CheckBox.focus", new ColorUIResource(new Color(0xC0FFC1)));  // Uppdaterad till CheckBox

        frame = new JFrame("Quiz Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(850, 450);

        // Ladda bakgrundsbilden
        ImageIcon backgroundIcon = new ImageIcon(getClass().getResource("/images/background.png"));
        BackgroundPanel backgroundPanel = new BackgroundPanel(backgroundIcon.getImage());
        backgroundPanel.setLayout(null);
        frame.setContentPane(backgroundPanel);

        questionArea = new JTextArea();
        questionArea.setBounds(50, 50, 750, 60); // Justera höjden för att rymma fler rader
        questionArea.setFont(new Font("Arial", Font.BOLD, 25));  // Gör frågetexten fetstilad
        questionArea.setLineWrap(true);  // Gör så att texten bryts till nästa rad
        questionArea.setWrapStyleWord(true);  // Gör så att texten bryts vid hela ord
        questionArea.setOpaque(false);  // Gör bakgrunden transparent
        questionArea.setEditable(false);  // Gör textområdet oeditable
        frame.add(questionArea);

        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setBounds(400, 20, 200, 30);
        frame.add(scoreLabel);

        livesLabel = new JLabel("Lives: 3");
        livesLabel.setBounds(50, 20, 200, 30);
        frame.add(livesLabel);

        timerLabel = new JLabel("Time: 30"); // @author Ali Farhan
        timerLabel.setBounds(700,20, 100, 30); // @author Ali Farhan
        frame.add(timerLabel); // @author Ali Farhan

        optionPanels = new JPanel[4];
        optionLabels = new JLabel[4];

        for (int i = 0; i < optionPanels.length; i++) {
            optionPanels[i] = new JPanel(new GridBagLayout());
            optionPanels[i].setBounds(50 + (i % 2) * 400, 130 + (i / 2) * 100, 350, 80);  // Placera alternativen två per rad
            optionPanels[i].setBackground(new Color(0xC0FFC1));
            optionPanels[i].setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

            optionLabels[i] = new JLabel("", SwingConstants.CENTER);
            optionLabels[i].setFont(new Font("Arial", Font.PLAIN, 20));
            optionPanels[i].add(optionLabels[i], new GridBagConstraints());

            int optionIndex = i;
            optionPanels[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    selectOption(optionIndex);  // Hantera klick på alternativ
                    submitButton.setEnabled(true);
                }
            });


            frame.add(optionPanels[i]);
        }

        submitButton = new JButton("Submit");
        submitButton.setBounds(300, 360, 100, 40);  // Justerad position för att undvika överlappning
        submitButton.setBackground(new Color(255, 255, 255));
        submitButton.setForeground(Color.BLACK);
        submitButton.setEnabled(false); //@author Ali Farhan
        frame.add(submitButton);


        exitButton = new JButton("Exit");
        exitButton.setBounds(450, 360, 100, 40);
        exitButton.setBackground(new Color(255, 255, 255));
        exitButton.setForeground(Color.BLACK);
        frame.add(exitButton);


        submitButton.addActionListener(e -> {
            if (selectedOption != -1) {
                quizController.submitAnswer(selectedOption);
                submitButton.setEnabled(false);
                resetTimer();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0); //Avsluta programmet när kanppen klickas.
            }
        });

        exitButton.addActionListener(e -> System.exit(0));
        increaseVolumeButton = new JButton("+ Volume");                             // @author Ali Farhan
        increaseVolumeButton.setBounds(750, 340, 90, 30);           // @author Ali Farhan
        frame.add(increaseVolumeButton);                                                // @author Ali Farhan

        decreaseVolumeButton = new JButton("- Volume");                         // @author Ali Farhan
        decreaseVolumeButton.setBounds(750, 380, 90, 30);       // @author Ali Farhan
        frame.add(decreaseVolumeButton);                                        // @author Ali Farhan

        muteButton = new JButton("Mute");                               // @author Ali Farhan
        muteButton.setBounds(660, 360, 90, 30);           // @author Ali Farhan
        frame.add(muteButton);                                               // @author Ali Farhan

        increaseVolumeButton.addActionListener(e -> quizController.increaseVolume());       // @author Ali Farhan
        decreaseVolumeButton.addActionListener(e -> quizController.decreaseVolume());       // @author Ali Farhan
        muteButton.addActionListener(e -> quizController.mute());                           // @author Ali Farhan

        setupTimer(); // @author Ali Farhan

        frame.setLocationRelativeTo(null); // Centrera GUI @Author Ali Farhan & Elias Celyir
        frame.setVisible(true);

    }


    /**
     * @author Ali Farhan
     * @param question
     */
    public void showQuestion(Question question) {
        SwingUtilities.invokeLater(() -> {
            questionArea.setText(question.getText());
            String[] options = question.getOptions();

            selectedOption = -1;
            submitButton.setEnabled(false);
            for (int i = 0; i < optionPanels.length; i++) {
                optionLabels[i].setText(options[i]);
                optionPanels[i].setVisible(true);
                optionPanels[i].setBackground(new Color(0xC0FFC1));
            }
            resetTimer();
        });
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
                if (timeLeft <= 0) {
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
        timer.restart(); // Startar om timern
    }



    /**
     * @author Ali Farhan
     * @param index
     */
    private void selectOption(int index) {
        selectedOption = index;
        for (int i = 0; i < optionPanels.length; i++) {
            optionPanels[i].setBackground(i == index ? new Color(0x00FF00) : new Color(0xC0FFC1)); // Markera det valda alternativet med stark grön färg
        }
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
