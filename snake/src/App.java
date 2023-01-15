import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class App extends Application {
    public static Stage mainStage;
    // ideally would be in controller only but every attribute there doesnt work/is
    // null
    public static Controls userControls = new Controls();

    public static void main(String[] args) {
        launch(args);
    }

    @Override // starts the game
    public void start(Stage stage) {

        try {
            // sets the stage
            mainStage = stage;
            stage.setTitle("Snake");
            // sets the icon
            Image icon = new Image("images//gameIcon.png");
            stage.getIcons().add(icon);

            new Controller().showMainMenu();

            stage.setResizable(false);
            stage.show();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

}
