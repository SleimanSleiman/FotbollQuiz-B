package View;

import Model.Music;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class SettingsGUI {
    private JFrame frame;
    private JSlider volumeSlider;
    private JCheckBox muteCheckbox;
    private Music music;

    public SettingsGUI(Music music) {
        this.music = music;
    }

    public void displaySettings() {
        frame = new JFrame("Settings");
        frame.setSize(400, 200);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(new Color(192, 255, 193));
        frame.setLocationRelativeTo(null);

        JPanel settingsPanel = new JPanel();
        settingsPanel.setLayout(new GridLayout(2, 1));

        volumeSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
        volumeSlider.setMajorTickSpacing(10);
        volumeSlider.setMinorTickSpacing(5);
        volumeSlider.setPaintTicks(true);
        volumeSlider.setPaintLabels(true);

        muteCheckbox = new JCheckBox("Mute");

        settingsPanel.add(volumeSlider);
        settingsPanel.add(muteCheckbox);

        frame.add(settingsPanel, BorderLayout.CENTER);

        addListeners();

        frame.setVisible(true);
    }

    private void addListeners() {
        volumeSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (music != null) {
                    int volume = volumeSlider.getValue();
                    float volumeFloat = volume / 100.0f;
                    music.setVolume(volumeFloat);
                
                }
            }
        });

        muteCheckbox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (music != null) {
                    boolean muted = muteCheckbox.isSelected();
                    music.mute(muted);

                }
            }
        });
    }  }