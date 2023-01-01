
import javafx.application.Application;

import javafx.scene.image.Image;
import javafx.stage.Stage;

public class App extends Application {
    public static Stage mainStage;
    public static Controls userControls = new Controls();

    public static void main(String[] args) {
        launch(args);
    }

    @Override // starts the game
    public void start(Stage stage) {

        try {
            // intiaoizes default controls with no custome user input

            mainStage = stage;
            stage.setTitle("Snake");
            // CHANGE WHEN U GOT TIME THE NAME FROM LOL TO SOMETHING
            Image icon = new Image("images//gameIcon.png");
            stage.getIcons().add(icon);

            Controller controller = new Controller();
            controller.showMainMenu();
            stage.setResizable(false);
            stage.show();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

}
