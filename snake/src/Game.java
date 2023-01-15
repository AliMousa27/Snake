import java.io.IOException;
import java.util.HashMap;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.skin.TextInputControlSkin.Direction;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Game {

    private Timeline snakeAnimator;
    private AnimationTimer gameOverChecker;
    private Board board;
    private Snake snake;
    private Controls userControls;
    private int gameSpeed;

    Game(Board board, int gameSpeed) {
        this.userControls = App.userControls;
        this.board = board;
        snake = board.getSnake();
        this.gameSpeed = gameSpeed;
    }

    public void startGame() {
        // sets the scene to not be resizable and adds the scene to stage
        board.setStage();

        // Detect when a user presses an arrow key and updates snake position
        updateSnakeDirection();

        // starts an animation timer that checks if the snake has collided with anything
        // at every frame
        gameOver();

        // Create a Timeline that will be called every however many milliseconds wanted
        // (game speed) to animate snake
        this.snakeAnimator = new Timeline(new KeyFrame(Duration.millis(gameSpeed), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                // checks if the snake head ate the apple
                if (isEaten(board.getApple())) {
                    // if true then new apple will randomly spawn and snake will grow
                    board.generateNewApple();
                    board.incrementScore();
                    // snake grows and sets the position to be at the end of the tail
                    Rectangle newPart = snake.grow();
                    board.addSnakePart(newPart);
                }
                // moves the entire snake
                snake.move();

                // sets back the canMove variable to be true again such that the snake doesnt go
                // immediatly left when its heading right for example
                snake.setCanMove(true);

            }
        }));

        // Set the Timeline to repeat indefinitely
        snakeAnimator.setCycleCount(Animation.INDEFINITE);

        // Start the Timeline
        snakeAnimator.play();
    }

    // this method updates the snake direction based on user input
    public void updateSnakeDirection() {
        board.getBoardScene().setOnKeyPressed(event -> {
            KeyCode key = event.getCode();
            if (key == KeyCode.ESCAPE) {
                pauseGame();
            }

            Direction newDirection = userControls.getUserInput().get(key);
            if (newDirection == null) {
                return;
            } else {
                // Update the snake's direction based on the key that was pressed
                switch (newDirection) {
                    // only updates the snake position when it can move in the frame and its not
                    // opposite direction
                    case UP:
                        if (snake.getDirection() != Direction.DOWN && snake.getCanMove()) {
                            snake.setDirection(Direction.UP);
                            snake.setCanMove(false);
                        }
                        break;

                    case DOWN:
                        if (snake.getDirection() != Direction.UP && snake.getCanMove()) {
                            snake.setDirection(Direction.DOWN);
                            snake.setCanMove(false);

                        }
                        break;

                    case LEFT:
                        if (snake.getDirection() != Direction.RIGHT && snake.getCanMove()) {
                            snake.setDirection(Direction.LEFT);
                            snake.setCanMove(false);

                        }
                        break;

                    case RIGHT:
                        if (snake.getDirection() != Direction.LEFT && snake.getCanMove()) {
                            snake.setDirection(Direction.RIGHT);
                            snake.setCanMove(false);
                        }
                        break;
                    default:
                }
            }
            ;
        });
    }

    public boolean isEaten(Rectangle apple) {
        // method to check of the snake is on top of the apple
        if (snake.getHead().getX() == apple.getX() && snake.getHead().getY() == apple.getY()) {
            return true;
        } else {
            return false;
        }
    }

    public void gameOver() {

        this.gameOverChecker = new AnimationTimer() {

            @Override
            public void handle(long arg0) {
                if (hasCollidedWithBoundary() || hasCollidedWithTail()) {
                    getSnakeAnimator().stop();
                    getGameOverChecker().stop();

                    showGameOver();

                    if (getBoard().getHighScoreCount() == getBoard().getScoreCount()) {
                        Data.writeState(getBoard().getHighScoreCount());
                    }
                }
            }

        };
        gameOverChecker.start();
    }

    public boolean hasCollidedWithBoundary() {
        double x = snake.getHead().getX();
        double y = snake.getHead().getY();
        if (x >= board.getWidth() || x < 0) {
            return true;
        } else if (y >= board.getHEIGHT() || y < 0) {
            return true;
        } else {
            return false;
        }

    }

    public void continueGame(Group backGround, Button quitButton, Button restartButton, Button continueButton) {
        // removes the buttons and blur
        quitButton.setVisible(false);
        restartButton.setVisible(false);
        continueButton.setVisible(false);
        backGround.setEffect(null);
        // starts the game again
        snakeAnimator.play();
        gameOverChecker.start();

    }

    public void pauseGame() {
        // stops the game
        snakeAnimator.stop();
        gameOverChecker.stop();

        board.getRoot().setEffect(new GaussianBlur(20));
        // creates the button and sets the layout
        Button continueButton = new Button("Continue");
        setButtonPrefs(continueButton, 200, 150, createInnerShadow());

        HashMap<String, Button> buttons = createGameOverButtons(createInnerShadow());
        Button restartButton = buttons.get("restartButton");
        Button quitButton = buttons.get("quitButton");
        // create a foreground with the buttons, add the background then only blur the
        // background root node
        Group foreground = new Group();
        foreground.getChildren().addAll(continueButton, restartButton, quitButton);

        foreground.getChildren().addAll(board.getRoot());
        // sends it to the back and displays buttons on top
        board.getRoot().toBack();
        // board.getroot is the background with the snake and all
        continueButton.setOnAction(
                event -> continueGame(board.getRoot(), quitButton, restartButton, continueButton));

        board.getBoardScene().setRoot(foreground);

    }

    public void showGameOver() {
        board.getRoot().setEffect(new GaussianBlur(20));

        ImageView youDied = new ImageView(new Image("images//youdied.png"));
        youDied.setPreserveRatio(false);
        youDied.setX(-10);
        youDied.setY(-10);

        InnerShadow shadow = createInnerShadow();

        youDied.setFitHeight(300);
        youDied.setFitWidth(600);

        HashMap<String, Button> buttons = createGameOverButtons(shadow);
        Button restartButton = buttons.get("restartButton");
        Button quitButton = buttons.get("quitButton");

        Group foreground = new Group();
        foreground.getChildren().addAll(youDied, restartButton, quitButton);

        foreground.getChildren().addAll(board.getRoot());
        board.getRoot().toBack();

        board.getBoardScene().setRoot(foreground);
        board.getBoardScene().setOnKeyPressed(null);

    }

    public HashMap<String, Button> createGameOverButtons(InnerShadow shadow) {
        // those buttons are common between pausing and dying so might as well have this
        // method
        Button restartButton = new Button("Restart");
        setButtonPrefs(restartButton, 200, 250, shadow);
        restartButton.setOnAction(event -> new Controller().start(event));

        Button quitButton = new Button("Quit");
        setButtonPrefs(quitButton, 200, 350, shadow);
        quitButton.setOnAction(event -> {
            try {
                new Controller().showMainMenu();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        });

        HashMap<String, Button> buttons = new HashMap<>();
        buttons.put("quitButton", quitButton);
        buttons.put("restartButton", restartButton);

        return buttons;
    }

    public InnerShadow createInnerShadow() {
        InnerShadow shadow = new InnerShadow();
        shadow.setOffsetX(1);
        shadow.setOffsetY(1);
        return shadow;
    }

    public void setButtonPrefs(Button button, int xLayOut, int yLayOut, InnerShadow shadow) {
        button.setLayoutX(xLayOut);
        button.setLayoutY(yLayOut);
        button.setPrefHeight(50);
        button.setPrefWidth(200);
        button.setEffect(shadow);
    }

    public boolean hasCollidedWithTail() {

        for (int i = 1; i < snake.getTail().size(); i++) {
            Rectangle currentPart = snake.getTail().get(i);
            double xForHead = snake.getHead().getX();
            double yForHead = snake.getHead().getY();

            if (xForHead == currentPart.getX() && yForHead == currentPart.getY()) {
                return true;
            }

        }
        return false;
    }

    public Timeline getSnakeAnimator() {
        return snakeAnimator;
    }

    public AnimationTimer getGameOverChecker() {
        return gameOverChecker;
    }

    public Board getBoard() {
        return board;
    }

}
