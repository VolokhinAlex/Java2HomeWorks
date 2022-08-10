package homework1;

import java.awt.*;
import java.util.Random;

public class BackGround extends Sprites {

    /**
     * Класс изменяет цвет канвы в зависимости от времени.
     */

    Random random = new Random();
    private float range = 0.15f; // min = 0; max = 0.2;
    private Color color;

    @Override
    void update(GameCanvas canvas, float deltaTime) {
        if (deltaTime < 0.1) {
            deltaTime *= 10;
        }
        color = new Color(deltaTime + random.nextFloat(range), deltaTime + random.nextFloat(range), deltaTime + random.nextFloat(range));
    }

    @Override
    void render(GameCanvas canvas, Graphics g) {
        canvas.setBackground(color);
    }

}
