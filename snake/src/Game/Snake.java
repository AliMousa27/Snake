import java.util.ArrayList;

import javafx.scene.control.skin.TextInputControlSkin.Direction;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Snake {

    private final int SQUARE_SIZE;
    private Direction direction;
    private ArrayList<Rectangle> tail;
    private boolean canMove;
    private Color snakeColor;

    Snake(int SQUARE_SIZE, Color snakeColor) {
        this.SQUARE_SIZE = SQUARE_SIZE;
        this.direction = Direction.RIGHT;
        this.tail = new ArrayList<>();
        canMove = true;
        this.snakeColor = snakeColor;

    }

    public void addNewSnakePart() {
        Rectangle newSegment = new Rectangle(SQUARE_SIZE, SQUARE_SIZE, snakeColor);
        tail.add(newSegment);
    }

    public Rectangle grow() {
        // saves the last part with its positions with a refrence named "lastPart"
        int snakeSize = tail.size() - 1;
        Rectangle lastPart = tail.get(snakeSize);
        // adds a new rectangle such that the snake size grows
        addNewSnakePart();
        // gets the nlast added part with a refrence to operate on its x and y
        // coordiantes
        snakeSize = tail.size() - 1;
        Rectangle newPart = tail.get(snakeSize);

        newPart.setX(lastPart.getX());
        newPart.setY(lastPart.getY());
        // returns the new part that was just added with its correct positions
        return newPart;
    }

    public void setDirection(Direction newDirection) {
        this.direction = newDirection;
    }

    public Direction getDirection() {
        return this.direction;
    }

    public void move() {
        // moves tail
        for (int i = tail.size() - 1; i > 0; i--) {
            Rectangle currentPart = tail.get(i);
            Rectangle nextPart = tail.get(i - 1);
            currentPart.setX(nextPart.getX());
            currentPart.setY(nextPart.getY());
        }
        // moves head
        switch (direction) {
            case UP:
                getHead().setY(getHead().getY() - SQUARE_SIZE);

                break;
            case DOWN:
                getHead().setY(getHead().getY() + SQUARE_SIZE);

                break;
            case LEFT:
                getHead().setX(getHead().getX() - SQUARE_SIZE);

                break;
            case RIGHT:
                getHead().setX(getHead().getX() + SQUARE_SIZE);

                break;

            default:
                break;
        }
    }

    public ArrayList<Rectangle> getTail() {
        return tail;
    }

    public Rectangle getHead() {
        return this.tail.get(0);
    }

    public boolean getCanMove() {
        return canMove;
    }

    public void setCanMove(Boolean newStatus) {
        this.canMove = newStatus;
    }

}
