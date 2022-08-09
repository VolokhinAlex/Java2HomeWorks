package homework1;

import java.awt.*;
import java.util.Random;

public class BackGround extends Sprites {
    Random random = new Random();

    private Color color;

    @Override
    void update(GameCanvas canvas, float deltaTime) {
        color = new Color(random.nextInt());
    }

    @Override
    void render(GameCanvas canvas, Graphics g) {
//        canvas.setBackground(color);
    }

}
