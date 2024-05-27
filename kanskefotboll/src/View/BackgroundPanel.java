package View;

import javax.swing.*;
import java.awt.*;


/**
 * En JPanel som tillåter att en bakgrundsbild ritas på panelen.
 * @author Ali Farhan
 */
public class BackgroundPanel extends JPanel {
    private Image backgroundImage;


    /**
     * Skapar en BackgroundPanel med en specificerad bakgrundsbild.
     *
     * @param backgroundImage Bilden som ska användas som bakgrund.
     * @author Ali Farhan
     */
    public BackgroundPanel(Image backgroundImage) {
        this.backgroundImage = backgroundImage;
    }


    /**
     * Ritar komponenten och dess bakgrundsbild. Bakgrundsbilden skalas för att fylla hela panelen.
     *
     * @param g Grafikkontexten som används för att rita komponenten.
     * @author Ali Farhan
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}
