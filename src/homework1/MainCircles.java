package homework1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;

public class MainCircles extends JFrame {

    /**
     * ДЗ:
     * 1. Полностью разобраться с кодом.
     * 2. Написать класс Бэкграунд, изменяющий цвет канвы в зависимости от времени.
     * 3.* Реализовать добавление новых кружков по клику, используя только массивы.
     * 4.** Реализовать по клику другой кнопки удаление кружков (никаких ArrayList).
     * <p>
     * Решение:
     * Для задания 3 и 4 добавлены методы:
     * <method> checkMouseButton </method> - проверяет какая кнопка мыши была нажата.
     * <method> createNewBall </method> - Создает новый шар.
     * <method> removeBall </method> - Удаляет шар.
     */

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

    /**
     * Метод проверяет какая кнопка была нажата.
     * Если нажать левую кнопку мыши, создается новый шар.
     * Если нажать правую кнопку мыши, удаляется шар.
     *
     * @param event - Событие нажатия кнопки мыши.
     */

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

    /**
     * Метод для инициализации элементов.
     */

    private void initApplication() {
        for (int i = 0; i < sprites.length; i++) {
            sprites[i] = new Ball();
        }
    }

    /**
     * Действия которые должны проходить после отрисовки холста.
     *
     * @param canvas    - холст.
     * @param g         - graphics.
     * @param deltaTime - время скорости отклика.
     */

    public void onDrawFrame(GameCanvas canvas, Graphics g, float deltaTime) {
        update(canvas, deltaTime);
        render(canvas, g);
    }

    /**
     * Обновление холста.
     *
     * @param canvas    - холст.
     * @param deltaTime - время скорости отклика.
     */

    private void update(GameCanvas canvas, float deltaTime) {
        for (int i = 0; i < sprites.length; i++) {
            sprites[i].update(canvas, deltaTime);
        }
        backGround.update(canvas, deltaTime);
    }

    /**
     * Отрисовка элементов.
     *
     * @param canvas - холст.
     * @param g      - graphics.
     */

    private void render(GameCanvas canvas, Graphics g) {
        for (int i = 0; i < sprites.length; i++) {
            sprites[i].render(canvas, g);
        }
        backGround.render(canvas, g);
    }

    /**
     * Метод для добавления новых шаров.
     * Метод копирует массив спрайтов в массив countSprites, размер которого изменяется на единицу.
     *
     * @param ball - новый шар.
     * @return - Возвращается новый массив спрайтов.
     */

    private Sprites[] createNewBall(Ball ball) {
        Sprites[] countSprites = new Sprites[sprites.length + 1];
        System.arraycopy(sprites, 0, countSprites, 0, sprites.length);
        countSprites[sprites.length] = ball;
        return countSprites;
    }

    /**
     * Метод для удаления шаров.
     * Метод копирует массив спрайтов в массив countSprites, размер которого изменяется на единицу.
     *
     * @param ball - шар массива.
     * @return - Возвращается новый массив спрайтов.
     */

    private Sprites[] removeBall(Ball ball) {

        // Проверяем, соответствует ли размер массива массиву 0 если да, то возвращаем неизмененный массив спрайтов.
        if (sprites.length == 0) {
            return sprites;
        }

        // Иначе создаем новый массив countSprites

        Sprites[] countSprites = new Sprites[sprites.length - 1];
        System.arraycopy(sprites, 0, countSprites, 0, sprites.length - 1);
        return countSprites;
    }
}
