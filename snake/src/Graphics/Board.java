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

    Board(Color snakeColor, Color squareColor1, Color squareColor2, int gridSize) {

        this.HEIGHT = Util.WINDOWS_HEIGHT_WIDTH;
        this.WIDTH = Util.WINDOWS_HEIGHT_WIDTH;
        this.COLUMNS = gridSize;
        this.ROWS = gridSize;
        // makes the squares more mudlar such that we can change the size when we want
        this.SQUARE_SIZE = HEIGHT / gridSize;
        // makes the snake with the desierd size and color
        this.snake = new Snake(SQUARE_SIZE, snakeColor);
        // create the rectangle that is the apple
        this.apple = new Rectangle(SQUARE_SIZE, SQUARE_SIZE);

        this.root = new Group();
        // gets the highscore saved data. If theres no highscore data yet then its 0
        highScoreCount = Data.readState();
        // numbers just place the highscore text exactly where it needs to be top right
        highScoreText = new Text(550, 37, Integer.toString(highScoreCount));
        // same here but top left
        scoreCount = 0;
        scoreText = new Text(0, 25, "Score: " + scoreCount);
        // this is done to not have another stage pop up on top of the main menu
        stage = App.mainStage;
        // draws board with the desierd colors
        this.boardScene = drawBoard(squareColor1, squareColor2);

        // adds apple and snake to board
        addApple();
        initalizeSnake();
    }

    public Scene drawBoard(Color squareColor1, Color squareColor2) {

        Scene scene = new Scene(root);
        final Canvas canvas = new Canvas(WIDTH, HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        InnerShadow innerShadow = new InnerShadow();

        // Setting the offset values of the inner shadow aka how much inner shadows are
        // there
        innerShadow.setOffsetX(1);
        innerShadow.setOffsetY(1);
        gc.setEffect(innerShadow);

        // the for loop is to create rectangles that alternate. so if i=0 and j=0 then
        // we add color1 otherwise if i=0 and j=1 we add square to. this way it
        // alternates the pattern
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                if ((i + j) % 2 == 0) {
                    gc.setFill(squareColor1);
                } else {
                    gc.setFill(squareColor2);
                }
                gc.fillRect(i * SQUARE_SIZE, j * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);

            }

        }
        // styles the score text with css file
        styleScores();
        // makes the trophy and adds it to top right
        Image image = new Image("images//trophy.png");
        ImageView trophyIcon = new ImageView(image);
        // 50 is just the desierd size of the image such that it doesnt obscure too much
        // of the screen
        trophyIcon.setFitHeight(50);
        trophyIcon.setFitWidth(50);
        // places it on the top left
        trophyIcon.setX(500);
        trophyIcon.setY(0);
        // gets the correct styling
        String css = this.getClass().getResource("score.css")
                .toExternalForm();
        scene.getStylesheets().add(css);
        // adds everything to the scene
        root.getChildren().addAll(canvas, scoreText, trophyIcon, highScoreText);
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

        snake.getHead().setEffect(new Glow(0.5));
        snake.addNewSnakePart();

        Rectangle s = snake.getTail().get(1);
        // the coordinate doesnt matter since once the snake starts moving it will be
        // put behind the head to give the illsuion of a snake anyways
        s.setX(0);
        s.setY(SQUARE_SIZE);

        root.getChildren().add(snake.getHead());
        root.getChildren().add(s);
    }

    public void addSnakePart(Rectangle newPart) {
        // adds the new part whenever the snake grows
        root.getChildren().add(newPart);
    }

    public void generateNewApple() {

        this.apple.setX(Util.generateRandomPosition(ROWS, SQUARE_SIZE));
        this.apple.setY(Util.generateRandomPosition(ROWS, SQUARE_SIZE));
    }

    public void incrementScore() {
        // increments the score and changes the scores text
        scoreCount++;
        scoreText.setText("Score: " + scoreCount);
        // if current score is bigger than highscore we update highscore always
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
        // sets the scene really
        stage.setScene(this.boardScene);
        stage.setResizable(false);
    }

    public Scene getBoardScene() {
        return boardScene;
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

    public Group getRoot() {
        return root;
    }

}
