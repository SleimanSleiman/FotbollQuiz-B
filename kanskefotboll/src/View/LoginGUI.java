package View;

import Control.QuizController;
import javax.swing.*;
import java.awt.*;

public class LoginGUI {
    private JFrame frame;
    private JLabel nameLabel;
    private JTextField nameField;
    private JToggleButton[] categoryButtons;
    private JLabel categoryLabel;
    private JPanel categoryPanel;
    private JButton startButton;
    private JButton increaseVolumeButton;
    private JButton decreaseVolumeButton;
    private JButton muteButton;

    private JButton instructionsButton;

    private QuizController quizController;

    /**
     * Skapar ett nytt LoginGUI.
     *
     * @param quizController Kontrollern som hanterar quizlogiken.
     * @author Manar Almaliki, Karam Kallab, Sleiman Sleiman, Ali Farhan, Elias Celayir
     */
    public LoginGUI(QuizController quizController) {
        this.quizController = quizController;

        frame = new JFrame("Quiz Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(850, 450);

        ImageIcon backgroundIcon = new ImageIcon(getClass().getResource("/images/background.png"));
        BackgroundPanel backgroundPanel = new BackgroundPanel(backgroundIcon.getImage());
        backgroundPanel.setLayout(null);
        frame.setContentPane(backgroundPanel);

        nameLabel = new JLabel("Name");
        nameLabel.setBounds(400, 60, 300, 30);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 18));
        frame.add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(275, 100, 300, 30);
        frame.add(nameField);

        categoryLabel = new JLabel("Category");
        categoryLabel.setBounds(390, 140, 300, 30);
        categoryLabel.setFont(new Font("Arial", Font.BOLD, 18));
        frame.add(categoryLabel);

        categoryPanel = new JPanel();
        categoryPanel.setBounds(275, 180, 300, 120);
        categoryPanel.setBackground(new Color(0, 0, 0, 0));
        categoryPanel.setLayout(new GridLayout(0, 1));

        String[] categories = {"Bundesliga", "Premier League", "Laliga", "Serie A", "Ligue 1"};
        categoryButtons = new JToggleButton[categories.length];

        for (int i = 0; i < categories.length; i++) {
            categoryButtons[i] = new JToggleButton(categories[i]);
            categoryButtons[i].setFont(new Font("Arial", Font.PLAIN, 16));
            categoryButtons[i].setBackground(new Color(255, 255, 255));
            categoryButtons[i].setForeground(Color.BLACK);
            categoryButtons[i].setOpaque(true);
            categoryButtons[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
            categoryButtons[i].setFocusPainted(false);
            categoryButtons[i].addActionListener(e -> handleToggleButton((JToggleButton) e.getSource()));
            categoryPanel.add(categoryButtons[i]);
        }

        frame.add(categoryPanel);

        startButton = new JButton("Start Quiz");
        startButton.setBounds(350, 320, 150, 40);
        frame.add(startButton);

        increaseVolumeButton = new JButton("+ Volume");
        increaseVolumeButton.setBounds(750, 300, 80, 40);
        frame.add(increaseVolumeButton);

        decreaseVolumeButton = new JButton("- Volume");
        decreaseVolumeButton.setBounds(750, 350, 80, 40);
        frame.add(decreaseVolumeButton);

        muteButton = new JButton("Mute");
        muteButton.setBounds(750, 250, 80, 40);
        frame.add(muteButton);

        instructionsButton = new JButton("Instruktioner");
        instructionsButton.setBounds(50, 350, 150, 40);
        frame.add(instructionsButton);

        startButton.addActionListener(e -> {
            String playerName = nameField.getText();
            String selectedCategory = null;
            for (JToggleButton button : categoryButtons) {
                if (button.isSelected()) {
                    selectedCategory = button.getText();
                    break;
                }
            }
            if (playerName.isEmpty() && selectedCategory == null) {
                JOptionPane.showMessageDialog(frame, "Please enter your name and select a category. ");
            } else if (playerName.isEmpty()) {
                JOptionPane.showMessageDialog(frame,"Please enter your name. ");
            } else if (selectedCategory == null) {
                JOptionPane.showMessageDialog(frame,"Please select a category. ");

            } else {
                quizController.onStartQuiz(playerName, selectedCategory);
                frame.dispose();
            }
        });

        increaseVolumeButton.addActionListener(e -> quizController.increaseVolume());
        decreaseVolumeButton.addActionListener(e -> quizController.decreaseVolume());
        muteButton.addActionListener(e -> quizController.mute());
        instructionsButton.addActionListener(e -> new InstructionsGUI());
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }


    /**
     * Hanterar toggleknappens beteende när en kategori väljs.
     * Avmarkerar alla andra knappar och markerar endast den valda knappen.
     *
     * @param selectedButton Den valda JToggleButton.
     * @author Ali Farhan
     */
    private void handleToggleButton(JToggleButton selectedButton) {
        for (JToggleButton button : categoryButtons) {
            if (button != selectedButton) {
                button.setSelected(false);
                button.setBackground(new Color(255, 255, 255));
            } else {
                button.setBackground(new Color(200, 200, 200));
            }
        }
    }

    public Component getFrame() {
        return frame;
    }
}
