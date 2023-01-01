import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.Glow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Board {
    // the stage is never intiliased until we set the stage here to be the same
    // stage as the controller one. therfore whenever the controller starts the game
    // we set the stage to be the same stage in controlled to avoid a window on top
    // of another
    private Stage stage;
    private Scene boardScene;
    private Group root;
    private Text scoreText;
    private int scoreCount;
    private Text highScoreText;
    private int highScoreCount;
    private final int WIDTH;
    private final int HEIGHT;
    private final int ROWS;
    private final int COLUMNS;
    private final int SQUARE_SIZE;
    private Snake snake;
    private Rectangle apple;

    Board(String squareColor1, String squareColor2) {
        this.HEIGHT = Util.WINDOWS_HEIGHT_WIDTH;
        this.WIDTH = Util.WINDOWS_HEIGHT_WIDTH;
        this.COLUMNS = Util.NUMBER_OF_COLMUNS_ROWS;
        this.ROWS = Util.NUMBER_OF_COLMUNS_ROWS;
        this.SQUARE_SIZE = Util.SQUARE_SIZE;

        this.snake = new Snake(SQUARE_SIZE);
        this.apple = new Rectangle(SQUARE_SIZE, SQUARE_SIZE);

        this.root = new Group();

        highScoreCount = Data.readState();
        // numbers just place the highscore text exactly where it needs to be top right
        highScoreText = new Text(560, 37, Integer.toString(highScoreCount));
        // same here but top left
        scoreCount = 0;
        scoreText = new Text(0, 25, "Score: " + scoreCount);

        stage = App.mainStage;
        this.boardScene = drawBoard(squareColor1, squareColor2);

        addApple();
        initalizeSnake();
    }

    public Scene drawBoard(String squareColor1, String squareColor2) {

        Scene scene = new Scene(root);
        final Canvas canvas = new Canvas(WIDTH, HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        InnerShadow innerShadow = new InnerShadow();

        // Setting the offset values of the inner shadow aka how much inner shadows are
        // there
        innerShadow.setOffsetX(1);
        innerShadow.setOffsetY(1);
        gc.setEffect(innerShadow);

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                if ((i + j) % 2 == 0) {
                    gc.setFill(Color.web(squareColor1));
                } else {
                    gc.setFill(Color.web(squareColor2));
                }
                gc.fillRect(i * SQUARE_SIZE, j * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);

            }

        }
        // styles the score text
        styleScores();
        // makes the trophy and adds it to top right
        Image im = new Image("images//trophy.png");
        ImageView view = new ImageView(im);
        view.setFitHeight(50);
        view.setFitWidth(50);
        view.setX(500);
        view.setY(0);

        String css = this.getClass().getResource("score.css").toExternalForm();
        scene.getStylesheets().add(css);

        root.getChildren().addAll(canvas);
        root.getChildren().addAll(scoreText);
        root.getChildren().addAll(view);
        root.getChildren().addAll(highScoreText);

        return scene;
    }

    public void styleScores() {
        highScoreText.setId("highscore");
        scoreText.setId("scoreText");
        Glow glow = new Glow(1.0);
        scoreText.setEffect(glow);
    }

    public void initalizeSnake() {
        addSnakeHead();
        // 3 body parts is the most optimal number for the snake to start as
        for (int i = 0; i < 4; i++) {
            addSnakePart(snake.grow());
        }
    }

    public void addSnakeHead() {
        // the start cooridnate is the square size so that the snake always is precisly
        // on the grid even if we want to change the number of rows and columns
        int startCoordinate = SQUARE_SIZE;
        // adds the head
        snake.addNewSnakePart();

        snake.getHead().setX(startCoordinate);
        snake.getHead().setY(startCoordinate);

        root.getChildren().add(snake.getHead());
    }

    public void addSnakePart(Rectangle newPart) {

        root.getChildren().add(newPart);
    }

    public void generateNewApple() {

        this.apple.setX(Util.generateRandomPosition());
        this.apple.setY(Util.generateRandomPosition());
    }

    public void incrementScore() {
        scoreCount++;
        scoreText.setText("Score: " + scoreCount);

        if (scoreCount >= highScoreCount) {
            highScoreCount = scoreCount;
            highScoreText.setText(Integer.toString(highScoreCount));
        }
    }

    public void addApple() {
        generateNewApple();
        Image im = new Image("images//apple.png");
        ImagePattern pattern = new ImagePattern(im);
        apple.setFill(pattern);

        root.getChildren().addAll(apple);
    }

    public void setStage() {
        stage.setScene(this.boardScene);
        stage.setResizable(false);
    }

    public Scene getBoardScene() {
        return boardScene;
    }

    public int getSquareSize() {
        return SQUARE_SIZE;
    }

    public Snake getSnake() {
        return snake;
    }

    public Rectangle getApple() {
        return apple;
    }

    public int getWidth() {
        return WIDTH;
    }

    public int getHEIGHT() {
        return HEIGHT;
    }

    public int getHighScoreCount() {
        return highScoreCount;
    }

    public int getScoreCount() {
        return scoreCount;
    }

    public Stage getStage() {
        return stage;
    }

}
