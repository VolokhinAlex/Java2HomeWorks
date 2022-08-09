package homework1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;

public class MainCircles extends JFrame {

    private static final int POS_X = 400;
    private static final int POS_Y = 200;
    private static final int WIDTH_WINDOW = 800;
    private static final int HEIGHT_WINDOW = 600;
    Sprites[] sprites = new Sprites[5];
    BackGround backGround = new BackGround();

    public static void main(String[] args) {
        new MainCircles();
    }

    private MainCircles() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(POS_X, POS_Y, WIDTH_WINDOW, HEIGHT_WINDOW);
        setTitle("Circles");
        GameCanvas canvas = new GameCanvas(this);
        initApplication();
        add(canvas);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                checkMouseButton(e);
            }
        });
        setVisible(true);
    }
    public void checkMouseButton(MouseEvent event) {
        switch (event.getButton()) {
            case 1:
                sprites = createNewBall(new Ball());
                break;
            case 3:
                sprites = removeBall(new Ball());
                break;
            default:
                throw new RuntimeException("Unexpected mouse button.");
        }
    }

    private void initApplication() {
        for (int i = 0; i < sprites.length; i++) {
            sprites[i] = new Ball();
        }
    }

    public void onDrawFrame(GameCanvas canvas, Graphics g, float deltaTime) {
        update(canvas, deltaTime);
        render(canvas, g);
    }

    private void update(GameCanvas canvas, float deltaTime) {
        for (int i = 0; i < sprites.length; i++) {
            sprites[i].update(canvas, deltaTime);
        }
        backGround.update(canvas, deltaTime);
    }

    private void render(GameCanvas canvas, Graphics g) {
        for (int i = 0; i < sprites.length; i++) {
            sprites[i].render(canvas, g);
        }
        backGround.render(canvas, g);
    }

    private Sprites[] createNewBall(Ball ball)
    {
        Sprites[] countSprites = new Sprites[sprites.length + 1];
        System.arraycopy(sprites, 0, countSprites, 0, sprites.length);
        countSprites[sprites.length] = ball;
        return countSprites;
    }

    private Sprites[] removeBall(Ball ball) {
        Sprites[] countSprites = new Sprites[sprites.length - 1];
        System.arraycopy(sprites, 0, countSprites, 0, sprites.length - 1);
        return countSprites;
    }
}
