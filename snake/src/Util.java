import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class Util {

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
        //the while loop is to prevent the apple from spawning on top of the score 
        while(randomNumber<=2){
             randomNumber = random.nextInt(NUMBER_OF_COLMUNS_ROWS);

        }
        return randomNumber * SPACE_BETWEEN_SQUARES;
    }

    public static int readState() {
        int highScore = 0;
        try {
            Scanner highScoreGetter = new Scanner(new File("C:\\Users\\Jafar\\Desktop\\Snake\\snake\\src\\highscore.txt"));
            highScore = highScoreGetter.nextInt();
            highScoreGetter.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return highScore;

    }

    public static void writeState(int highscore) {
        try {
            FileWriter highScoreWriter = new FileWriter("C:\\Users\\Jafar\\Desktop\\Snake\\snake\\src\\highscore.txt");
            highScoreWriter.write(Integer.toString(highscore));
            highScoreWriter.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
