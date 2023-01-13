import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.image.ImageView;

public class Controller {
    @FXML
    private Button startButton, exiButton, backButton, skinsButton, gameSettingsButton;
    @FXML
    private ImageView imageGif;
    @FXML
    private TextField upField, downField, leftField, rightField;
    @FXML
    private ColorPicker snakeColorPicker, gridRectangColorPicker1, gridRectangColorPicker2;

    private Stage stage = App.mainStage;
    private Scene scene;
    private Parent root;

    private Game game;
    private int gameSpeed = Util.gameSpeed;
    private int gridSize = Util.gameGridSize;
    private Controls controls = App.userControls;

    public void start(ActionEvent e) {
        Board board = new Board(Util.snakeColor, Util.gridRectangle1, Util.gridRectangle2, gridSize);
        game = new Game(board, gameSpeed);
        game.startGame();
    }

    public void exit(ActionEvent e) {
        stage.close();
    }

    public void showMainMenu() throws IOException {
        showCorrectWindow(new ActionEvent(), "Scenes\\Main Menu.fxml");
    }

    public void showControlsMenu() throws IOException {

        showCorrectWindow(new ActionEvent(), "Scenes\\Controls.fxml");

        // displays the correct chosen options in the text fields
        showCorrectChosenOption();

    }

    public void showCorrectChosenOption() {
        for (Node node : root.getChildrenUnmodifiable()) {
            if (node instanceof TextField || node instanceof ColorPicker) {
                // control is the common parent between color picker and text field
                Control caller = (Control) node;
                String id = caller.getId();
                switch (id) {
                    case "upField":
                        ((TextField) caller).setText(controls.getUp().toString());
                        break;
                    case "downField":
                        ((TextField) caller).setText(controls.getDown().toString());
                        break;
                    case "rightField":
                        ((TextField) caller).setText(controls.getRight().toString());
                        break;
                    case "leftField":
                        ((TextField) caller).setText(controls.getLeft().toString());
                        break;

                    case "snakeColorPicker":
                        ((ColorPicker) caller).setValue(Util.snakeColor);
                        break;
                    case "gridRectangColorPicker1":
                        ((ColorPicker) caller).setValue(Util.gridRectangle1);
                        break;
                    case "gridRectangColorPicker2":
                        ((ColorPicker) caller).setValue(Util.gridRectangle2);
                        break;

                    default:

                }

            }

        }
    }

    public void changeControl(KeyEvent keyDetector) {
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
        // checks if there are any duplicate control

        if (!controls.getHasDuplicate()) {
            showMainMenu();
        } else {// if there are we display an error message and user cant quit until they set
                // correct controls
            Alert alert = new Alert(AlertType.ERROR);
            alert.setHeaderText("Error, Do not put duplicate keys in the controls.");
            alert.setContentText(
                    "Please check that there are no duplicate keys in any text field.");
            alert.show();
        }
    }

    public void showSkinsMenu() throws IOException {
        showCorrectWindow(new ActionEvent(), "Scenes\\skins.fxml");

        showCorrectChosenOption();
    }

    public ImageView getImageGif() {
        return imageGif;
    }

    public void applySkin(ActionEvent e) {
        Control commonParent = (Control) e.getSource();
        String id = commonParent.getId();

        switch (id) {
            case "volcanoTextButton":
                applyPresetColors(Color.web("#f1373a"), Color.web("#6e3535"), Color.web("#ff5335"));
                break;

            case "iceTextButton":
                applyPresetColors(Color.web("#f7f7f7"), Color.web("#dcf0e8"), Color.web("#2ddeeb"));
                break;

            case "grassTextButton":
                applyPresetColors(Color.web("#77f284"), Color.web("#0c4a13"), Color.web("#10e628"));
                break;
            case "chessTextButton":
                applyPresetColors(Color.web("#e9f0e9"), Color.web("#ffffff"), Color.web("#000000"));
                break;
            case "snakeColorPicker":
                Util.snakeColor = snakeColorPicker.getValue();
                break;
            case "gridRectangColorPicker1":
                Util.gridRectangle1 = gridRectangColorPicker1.getValue();
                break;

            case "gridRectangColorPicker2":
                Util.gridRectangle2 = gridRectangColorPicker2.getValue();
                break;

            default:
        }

    }

    public void applyPresetColors(Color snakeColor, Color gridRectangle1Color, Color gridRectangle2Color) {
        Util.snakeColor = snakeColor;
        Util.gridRectangle1 = gridRectangle1Color;
        Util.gridRectangle2 = gridRectangle2Color;
    }

    public void showGameSettings(ActionEvent e) throws IOException {
        showCorrectWindow(e, "Scenes\\GameSettings.fxml");
    }

    public void setGameSpeed(int newSpeed) {
        this.gameSpeed = newSpeed;
    }

    public void showCorrectWindow(ActionEvent e, String fileName) throws IOException {
        root = FXMLLoader.load(getClass().getResource(fileName));
        scene = new Scene(root);
        stage.setScene(scene);
    }
}