
import javafx.application.Application;

import javafx.scene.image.Image;
import javafx.stage.Stage;

public class App extends Application {
    public static Stage mainStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override // starts the game
    public void start(Stage stage) {

        try {
            // Game game = new Game("#76ded9", "#0080FF");

            // game.startGame(Util.NORMAL_SPEED);

            mainStage = stage;
            stage.setTitle("Snake");
            // CHANGE WHEN U GOT TIME THE NAME FROM LOL TO SOMETHING
            Image icon = new Image("images//gameIcon.png");
            stage.getIcons().add(icon);
            Controller controller = new Controller();
            controller.showMainMenu();
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

    }

}
