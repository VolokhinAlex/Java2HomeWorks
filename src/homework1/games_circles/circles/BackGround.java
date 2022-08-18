package homework1.games_circles.circles;

import homework1.games_circles.common.GameCanvas;
import homework1.games_circles.common.GameObject;

import java.awt.*;
import java.util.Random;

public class BackGround implements GameObject {

    /**
     * Класс изменяет цвет канвы в зависимости от времени.
     */

    Random random = new Random();
    private float range = 0.15f; // min = 0; max = 0.2;
    private Color color;

    @Override
    public void update(GameCanvas canvas, float deltaTime) {
        if (deltaTime < 0.1) {
            deltaTime *= 10;
        }
        color = new Color(deltaTime + random.nextFloat(range), deltaTime + random.nextFloat(range), deltaTime + random.nextFloat(range));
    }

    @Override
    public void render(GameCanvas canvas, Graphics g) {
        canvas.setBackground(color);
    }

}
