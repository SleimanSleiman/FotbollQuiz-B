package View;


import javax.swing.*;
import java.awt.*;


/**
 * A JPanel that allows a background image to be drawn on the panel.
 *
 * @uthor Ali Farhan
 */
public class BackgroundPanel extends JPanel {
    private Image backgroundImage;


    /**
     * Creates a BackgroundPanel with a specified background image.
     *
     * @param backgroundImage The image to be used as background.
     * @uthor Ali Farhan
     */
    public BackgroundPanel(Image backgroundImage) {
        this.backgroundImage = backgroundImage;
    }


    /**
     * Draws the component and its background image. The background image is scaled to fill the entire panel.
     *
     * @param g The graphics context used to draw the component.
     * @uthor Ali Farhan
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}
