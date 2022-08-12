package homework1.games_circles.bricks;

import lesson2.games_circles.common.GameCanvas;
import lesson2.games_circles.common.GameCanvasListener;
import lesson2.games_circles.common.GameObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainBricks extends JFrame implements GameCanvasListener {

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
    GameObject[] gameObjects = new GameObject[5];

    public static void main(String[] args) {
        new MainBricks();
    }

    private MainBricks() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(POS_X, POS_Y, WIDTH_WINDOW, HEIGHT_WINDOW);
        setTitle("Bricks");
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
                gameObjects = createNewObject(new Bricks());
                break;
            case 3:
                gameObjects = removeObject(new Bricks());
                break;
            default:
                throw new RuntimeException("Unexpected mouse button.");
        }
    }

    /**
     * Метод для инициализации элементов.
     */

    private void initApplication() {
        for (int i = 0; i < gameObjects.length; i++) {
            gameObjects[i] = new Bricks();
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
        for (int i = 0; i < gameObjects.length; i++) {
            gameObjects[i].update(canvas, deltaTime);
        }
    }

    /**
     * Отрисовка элементов.
     *
     * @param canvas - холст.
     * @param g      - graphics.
     */

    private void render(GameCanvas canvas, Graphics g) {
        for (int i = 0; i < gameObjects.length; i++) {
            gameObjects[i].render(canvas, g);
        }
    }

    /**
     * Метод для добавления новых шаров.
     * Метод копирует массив спрайтов в массив countSprites, размер которого изменяется на единицу.
     *
     * @param ball - новый шар.
     * @return - Возвращается новый массив спрайтов.
     */

    private GameObject[] createNewObject(Bricks ball) {
        GameObject[] countSprites = new GameObject[gameObjects.length + 1];
        System.arraycopy(gameObjects, 0, countSprites, 0, gameObjects.length);
        countSprites[gameObjects.length] = ball;
        return countSprites;
    }

    /**
     * Метод для удаления шаров.
     * Метод копирует массив спрайтов в массив countSprites, размер которого изменяется на единицу.
     *
     * @param ball - шар массива.
     * @return - Возвращается новый массив спрайтов.
     */

    private GameObject[] removeObject(Bricks ball) {

        // Проверяем, соответствует ли размер массива массиву 0 если да, то возвращаем неизмененный массив спрайтов.
        if (gameObjects.length == 0) {
            return gameObjects;
        }

        // Иначе создаем новый массив countSprites

        GameObject[] countSprites = new GameObject[gameObjects.length - 1];
        System.arraycopy(gameObjects, 0, countSprites, 0, gameObjects.length - 1);
        return countSprites;
    }
}
