import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;

public class GameSettingsController implements Initializable {
    @FXML
    ChoiceBox<String> gameSpeedChoiceBox, gridSizeChoiceBox;

    private final Controller controller = new Controller();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gameSpeedChoiceBox.getItems().addAll("Fast", "Normal", "Slow");
        gameSpeedChoiceBox.setOnAction(this::setGameSpeed);
        // set to default
        gridSizeChoiceBox.getItems().addAll("Large", "Medium", "Small");
        gridSizeChoiceBox.setOnAction(this::setGridSize);

        setCorrectGameSpeedText();
        setCorrectGridSizeText();

    }

    public void setGridSize(ActionEvent event) {
        String chosenGridSize = gridSizeChoiceBox.getValue();
        switch (chosenGridSize) {
            case "Large":
                Util.gameGridSize = Util.LARGE_GRID_SIZE;
                break;

            case "Medium":
                Util.gameGridSize = Util.MEDIUM_GRID_SIZE;
                break;
            case "Small":
                Util.gameGridSize = Util.SMALL_GRID_SIZE;

        }
    }

    public void setGameSpeed(ActionEvent event) {

        String chosenSpeed = gameSpeedChoiceBox.getValue();
        switch (chosenSpeed) {
            case "Fast":
                Util.gameSpeed = Util.FAST_SPEED;
                break;

            case "Normal":
                Util.gameSpeed = Util.NORMAL_SPEED;
                break;
            case "Slow":
                Util.gameSpeed = Util.SLOW_SPEED;

        }
    }

    public void retrunToMainMenu() throws IOException {
        controller.showMainMenu();
    }

    public void setCorrectGameSpeedText() {

        switch (Util.gameSpeed) {
            case Util.FAST_SPEED:
                gameSpeedChoiceBox.setValue("Fast");
                break;

            case Util.NORMAL_SPEED:
                gameSpeedChoiceBox.setValue("Normal");
                break;

            case Util.SLOW_SPEED:
                gameSpeedChoiceBox.setValue("Slow");
                break;
            default:
        }
    }

    public void setCorrectGridSizeText() {
        switch (Util.gameGridSize) {
            case Util.LARGE_GRID_SIZE:
                gridSizeChoiceBox.setValue("Large");
                break;

            case Util.MEDIUM_GRID_SIZE:
                gridSizeChoiceBox.setValue("Medium");
                break;

            case Util.SMALL_GRID_SIZE:
                gridSizeChoiceBox.setValue("Small");
                break;
            default:
        }
    }
}
