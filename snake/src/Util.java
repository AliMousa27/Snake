import java.util.Random;

public class Util {
    public final static int NORMAL_SPEED = 75;
    public final static int NUMBER_OF_COLMUNS_ROWS = 25;
    public final static int WINDOWS_HEIGHT_WIDTH = 600;
    public final static int SQUARE_SIZE = WINDOWS_HEIGHT_WIDTH / NUMBER_OF_COLMUNS_ROWS;

    public static int generateRandomPosition() {
        // makes it such that it generates random number between an interval of the
        // number of columns
        // and rows * the square size to spawn the apple anywhere on the board
        final int SPACE_BETWEEN_SQUARES = SQUARE_SIZE;
        Random random = new Random();

        int randomNumber = random.nextInt(NUMBER_OF_COLMUNS_ROWS);
        // the while loop is to prevent the apple from spawning on top of the score
        while (randomNumber <= 2) {
            randomNumber = random.nextInt(NUMBER_OF_COLMUNS_ROWS);

        }
        return randomNumber * SPACE_BETWEEN_SQUARES;
    }

}
