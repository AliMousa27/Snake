import java.util.Random;

import javafx.scene.paint.Color;

public class Util {
    // game speeds
    public final static int SLOW_SPEED = 100;
    public final static int NORMAL_SPEED = 75;
    public final static int FAST_SPEED = 40;
    // grid sizes
    public final static int SMALL_GRID_SIZE = 20;
    public final static int MEDIUM_GRID_SIZE = 25;
    public final static int LARGE_GRID_SIZE = 30;

    public final static int WINDOWS_HEIGHT_WIDTH = 600;
    // colors

    public static Color snakeColor = Color.web("#7f00ff");
    public static Color gridRectangle1 = Color.web("#bd1b5a");
    public static Color gridRectangle2 = Color.web("#ff5335");

    public static int gameSpeed = NORMAL_SPEED;// starts as normal speed and normal grid size
    public static int gameGridSize = MEDIUM_GRID_SIZE;

    public static int generateRandomPosition(int gridSize, int squareSize) {
        // makes it such that it generates random number between an interval of the
        // number of columns
        // and rows * the square size to spawn the apple anywhere on the board

        Random random = new Random();

        final int SPACE_BETWEEN_SQUARES = squareSize;

        int randomNumber = random.nextInt(gridSize);
        // the while loop is to prevent the apple from spawning on top of the score row
        while (randomNumber <= 2) {
            randomNumber = random.nextInt(gridSize);

        }
        return randomNumber * SPACE_BETWEEN_SQUARES;
    }

}
