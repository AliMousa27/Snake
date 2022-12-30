import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.skin.TextInputControlSkin.Direction;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Game {

    Timeline snakeAnimator;
    AnimationTimer gameOverChecker;
    Board board;
    Snake snake;

    Game(String squareColor1, String squareColor2) {
        board = new Board(squareColor1, squareColor2);
        snake = board.getSnake();
    }

    public void startGame(int gameSpeed) {
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
                // checks if the snake head at the apple
                if (isEaten(board.getApple())) {
                    // if true then new apple will randomly spawn and snake will grow
                    board.generateNewApple();
                    board.incrementScore();
                    // snake grows and sets the position to be at the end of the tail
                    Rectangle newPart = snake.grow();
                    board.addSnakePart(newPart);
                }

                // move the tail first such that the tail does not lag behind the head to give
                snake.moveTail();
                snake.updateSnakePosition(board.getSnake().getDirection());
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

            // Update the snake's direction based on the key that was pressed

            switch (key) {
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
