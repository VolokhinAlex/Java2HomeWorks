package homework1;

import javax.swing.*;
import java.awt.*;

public class GameCanvas extends JPanel {
    long lastFrameTime;
    MainCircles mainCircles;

    GameCanvas(MainCircles mainCircles) {
        this.mainCircles = mainCircles;
        lastFrameTime = System.nanoTime();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        long currentTime = System.nanoTime();
        float deltaTime = (currentTime - lastFrameTime) * 0.000000001f;
        lastFrameTime = currentTime;
        mainCircles.onDrawFrame(this, g, deltaTime);
        try {
            Thread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        repaint();
    }

    /**
     * @return - левая граница холста.
     */

    public int getLeft() {
        return 0;
    }

    /**
     * @return - правая граница холста.
     */

    public int getRight() {
        return getWidth() - 1;
    }

    /**
     * @return - верхняя граница холста.
     */

    public int getTop() {
        return 0;
    }

    /**
     * @return - нижняя граница холста.
     */

    public int getBottom() {
        return getHeight() - 1;
    }

}
