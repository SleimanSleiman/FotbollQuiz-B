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
     * Skapar ett nytt QuizGUI.
     *
     * @param quizController Kontrollern som hanterar quizlogiken.
     * @author Manar Almaliki, Karam Kallab, Sleiman Sleiman, Elias Celayir, Ali Farhan
     */
    public QuizGUI(QuizController quizController) {
        this.quizController = quizController;
        UIManager.put("CheckBox.focus", new ColorUIResource(new Color(0xC0FFC1)));

        frame = new JFrame("Quiz Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(850, 450);

        // Ladda bakgrundsbilden
        ImageIcon backgroundIcon = new ImageIcon(getClass().getResource("/images/background.png"));
        BackgroundPanel backgroundPanel = new BackgroundPanel(backgroundIcon.getImage());
        backgroundPanel.setLayout(null);
        frame.setContentPane(backgroundPanel);

        questionArea = new JTextArea();
        questionArea.setBounds(50, 50, 750, 60);
        questionArea.setFont(new Font("Arial", Font.BOLD, 25));
        questionArea.setLineWrap(true);
        questionArea.setWrapStyleWord(true);
        questionArea.setOpaque(false);
        questionArea.setEditable(false);
        frame.add(questionArea);

        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setBounds(400, 20, 200, 30);
        frame.add(scoreLabel);

        livesLabel = new JLabel("Lives: 3");
        livesLabel.setBounds(50, 20, 200, 30);
        frame.add(livesLabel);

        timerLabel = new JLabel("Time: 30");
        timerLabel.setBounds(700,20, 100, 30);
        frame.add(timerLabel); // @author Ali Farhan

        optionPanels = new JPanel[4];
        optionLabels = new JLabel[4];

        for (int i = 0; i < optionPanels.length; i++) {
            optionPanels[i] = new JPanel(new GridBagLayout());
            optionPanels[i].setBounds(50 + (i % 2) * 400, 130 + (i / 2) * 100, 350, 80);
            optionPanels[i].setBackground(new Color(0xC0FFC1));
            optionPanels[i].setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

            optionLabels[i] = new JLabel("", SwingConstants.CENTER);
            optionLabels[i].setFont(new Font("Arial", Font.PLAIN, 20));
            optionPanels[i].add(optionLabels[i], new GridBagConstraints());

            int optionIndex = i;
            optionPanels[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    selectOption(optionIndex);
                    submitButton.setEnabled(true);
                }
            });


            frame.add(optionPanels[i]);
        }

        submitButton = new JButton("Submit");
        submitButton.setBounds(300, 360, 100, 40);
        submitButton.setBackground(new Color(255, 255, 255));
        submitButton.setForeground(Color.BLACK);
        submitButton.setEnabled(false);
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
                System.exit(0);
            }
        });

        exitButton.addActionListener(e -> System.exit(0));
        increaseVolumeButton = new JButton("+ Volume");
        increaseVolumeButton.setBounds(750, 340, 90, 30);
        frame.add(increaseVolumeButton);

        decreaseVolumeButton = new JButton("- Volume");
        decreaseVolumeButton.setBounds(750, 380, 90, 30);
        frame.add(decreaseVolumeButton);

        muteButton = new JButton("Mute");
        muteButton.setBounds(660, 360, 90, 30);
        frame.add(muteButton);

        increaseVolumeButton.addActionListener(e -> quizController.increaseVolume());
        decreaseVolumeButton.addActionListener(e -> quizController.decreaseVolume());
        muteButton.addActionListener(e -> quizController.mute());

        setupTimer();

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }


    /**
     * Visar en fråga och alternativen på skärmen.
     *
     * @param question Frågan som ska visas.
     * @author Ali Farhan
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
     * Ställer in timern som räknar ner från 30 sekunder och hanterar tidsgränsen.
     *
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
     * Återställer timern till 30 sekunder och startar om den.
     *
     * @author Ali Farhan
     */
    public void resetTimer() {
        timeLeft = 30;
        timerLabel.setText("Time left: " + timeLeft);
        timer.restart(); // Startar om timern
    }



    /**
     * Markerar det valda alternativet och avmarkerar de andra.
     *
     * @param index Index för det valda alternativet.
     * @author Ali Farhan
     */
    private void selectOption(int index) {
        selectedOption = index;
        for (int i = 0; i < optionPanels.length; i++) {
            optionPanels[i].setBackground(i == index ? new Color(0x00FF00) : new Color(0xC0FFC1)); // Markera det valda alternativet med stark grön färg
        }
    }


    /**
     * Uppdaterar poängen som visas på skärmen.
     *
     * @param score Den nya poängen.
     * @author Manar Almaliki och Karam Kallab
     */
    public void updateScore(int score) {
        scoreLabel.setText("Score: " + score);
    }


    /**
     * Uppdaterar antalet liv som visas på skärmen.
     *
     * @param lives Det nya antalet liv.
     * @author Manar Almaliki och Karam Kallab
     */
    public void updateLives(int lives) {
        livesLabel.setText("Lives: " + lives);
    }


    /**
     * Returnerar JFrame-komponenten.
     *
     * @return JFrame-komponenten.
     * @author Manar Almaliki och Karam Kallab
     */
    public JFrame getFrame() {
        return frame;
     }
}
