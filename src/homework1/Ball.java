package homework1;

import java.awt.*;
import java.util.Random;

public class Ball extends Sprites {

    Random random = new Random();

    private float speedX; // �������� �� X
    private float speedY; // �������� �� Y
    private final Color color; // ����

    Ball() {
        halfHeight = 20 + (float) (Math.random() * 50f);
        halfWidth = halfHeight;
        color = new Color(random.nextInt());
        speedX = (float) (100f + (Math.random() * 200f));
        speedY = (float) (100f + (Math.random() * 200f));
    }

    @Override
    void update(GameCanvas canvas, float deltaTime) {
        x += speedX * deltaTime;
        y += speedY * deltaTime;

        if (getLeft() < canvas.getLeft()) {
            setLeft(canvas.getLeft());
            speedX = -speedX;
        }
        if (getRight() > canvas.getRight()) {
            setRight(canvas.getRight());
            speedX = -speedX;
        }
        if (getTop() < canvas.getTop()) {
            setTop(canvas.getTop());
            speedY = -speedY;
        }
        if (getBottom() > canvas.getBottom()) {
            setBottom(canvas.getBottom());
            speedY = -speedY;
        }
    }

    @Override
    void render(GameCanvas canvas, Graphics g) {
        g.setColor(color);
        g.fillOval((int) getLeft(), (int) getTop(), (int) getWidth(), (int) getHeight());
    }
}
