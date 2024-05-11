package View;

import Control.QuizController;
import jdk.jfr.Category;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import View.SettingsGUI;


public class LoginGUI {
    private JFrame frame;
    private JLabel nameLabel;
    private JTextField nameField;
    private JLabel categoryLabel;
    private JComboBox<String> categoryComboBox;
    private JButton startButton;
    private JButton settingsButton;


    private QuizController quizController;

    public LoginGUI(QuizController quizController) {
        this.quizController = quizController;


        frame = new JFrame("Quiz Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 300);
        frame.setLayout(null);
        frame.getContentPane().setBackground(new Color(192, 255, 193));

        nameLabel = new JLabel("Name:");
        nameLabel.setBounds(150, 30, 100, 30);
        frame.add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(210, 30, 200, 30);
        frame.add(nameField);

        categoryLabel = new JLabel("Category:");
        categoryLabel.setBounds(140, 30, 100, 130);
        frame.add(categoryLabel);

        categoryComboBox = new JComboBox<>(new String[]{"Bundesliga", "Premier League", "Laliga", "Serie A", "Ligue 1"});
        categoryComboBox.setBounds(210, 80, 200, 30);
        categoryComboBox.setForeground(Color.BLACK);
        frame.add(categoryComboBox);

        startButton = new JButton("Start Quiz");
        startButton.setBounds(250, 180, 100, 40);
        startButton.setBackground(new Color(255, 255, 255, 255));
        startButton.setForeground(Color.black);
        frame.add(startButton);

        settingsButton = new JButton("Settings");
        settingsButton.setBounds(470, 180, 100, 40);
        settingsButton.setBackground(new Color(255, 255, 255));
        settingsButton.setForeground(Color.BLACK);
        frame.add(settingsButton);

        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openSettingsWindow();
            }
        });

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String playerName = nameField.getText();
                String selectedCategory = (String) categoryComboBox.getSelectedItem();
                quizController.onStartQuiz(playerName, selectedCategory);
                frame.dispose(); // Stäng inloggningssidan när quizet börjar
            }
        });

        frame.setLocationRelativeTo(null); // Centrera GUI @Author Ali Farhan & Elias Celyir

        frame.setVisible(true);
    }

    private void openSettingsWindow() {
        SettingsGUI settingsGUI = new SettingsGUI();
        settingsGUI.displaySettings();
    }


    /**
     * @Author Ali Farhan
     */
    public Component getFrame() {
        return frame;
    }
    }


