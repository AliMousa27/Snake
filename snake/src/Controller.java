import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.image.ImageView;

public class Controller {
    @FXML
    private Button startButton, exiButton, backButton;
    @FXML
    private ImageView imageGif;
    @FXML
    TextField upField, downField, leftField;
    @FXML
    TextField rightField;

    Stage stage = App.mainStage;
    Scene scene;
    Parent root;
    Controls controls = App.userControls;

    public void start(ActionEvent e) {
        Game game = new Game("#bd1b5a", "#ff5335");
        game.startGame(Util.NORMAL_SPEED);
    }

    public void exit(ActionEvent e) {
        stage.close();
    }

    public void showMainMenu() throws IOException {
        root = FXMLLoader.load(getClass().getResource("Main Menu.fxml"));
        scene = new Scene(root);
        stage.setScene(scene);

    }

    public void showControlsMenu() throws IOException {

        root = FXMLLoader.load(getClass().getResource("Controls.fxml"));
        // method to add the current controls to each text field
        scene = new Scene(root);
        stage.setScene(scene);

        // the for loop with swithc to display the correct default controls
        for (Node node : root.getChildrenUnmodifiable()) {

            if (node instanceof TextField) {
                TextField textField = (TextField) node;
                String fieldName = textField.getId();

                switch (fieldName) {
                    case "upField":
                        textField.setText(controls.getUp().toString());
                        break;
                    case "downField":
                        textField.setText(controls.getDown().toString());
                        break;
                    case "rightField":
                        textField.setText(controls.getRight().toString());
                        break;
                    case "leftField":
                        textField.setText(controls.getLeft().toString());
                        break;
                    default:

                }

            }

        }

    }

    public void changeText(KeyEvent keyDetector) {
        // gets the source of whos calling
        Object source = keyDetector.getSource();
        KeyCode keyPressed = keyDetector.getCode();

        // checks if the one calling is a text field
        if (source instanceof TextField) {
            TextField textFieldCalling = (TextField) source;
            // sets the text to the user pressed key
            textFieldCalling.setText(keyPressed.getName());
            // updates the control to the key pressed
            controls.updateKey(textFieldCalling.getId(), keyPressed);

            // if statment to avoid a bug where the if a digit or letter is typed its
            // displayed twice
            if (keyPressed.isLetterKey() || keyPressed.isDigitKey()) {
                textFieldCalling.deleteText(0, 1);
            }

        }

    }

    public void validateControlsExit(ActionEvent e) throws IOException {
        // checks if there are any duplicate controls
        if (!controls.getHasDuplicate()) {
            showMainMenu();
        } else {// if there are we display an error message and user cant quit until they set
                // correct controls
            Alert alert = new Alert(AlertType.ERROR);
            alert.setHeaderText("Error, Do not put duplicate keys in the controls.");
            alert.setContentText(
                    "Each key needs to be unique. Please check that there are no duplicate keys in any text field.");
            alert.show();
        }
    }

    public ImageView getImageGif() {
        return imageGif;
    }

}