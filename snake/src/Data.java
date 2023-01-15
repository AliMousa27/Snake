import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Data {
    public static int readState() {
        int highScore = 0;
        try {
            // this is done so that path isnt hardcoded to the local machine
            String Path = new File("").getAbsolutePath();
            Scanner highScoreGetter = new Scanner(new File(Path + "/Snake/snake/src/highscore.txt"));
            highScore = highScoreGetter.nextInt();
            highScoreGetter.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return highScore;

    }

    public static void writeState(int highscore) {
        try {
            String Path = new File("").getAbsolutePath();
            FileWriter highScoreWriter = new FileWriter(Path + "/Snake/snake/src/highscore.txt");
            highScoreWriter.write(Integer.toString(highscore));
            highScoreWriter.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
