import java.util.ArrayList;

import javafx.scene.control.skin.TextInputControlSkin.Direction;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Snake {

    private final int SQUARE_SIZE;
    private Direction direction;
    private ArrayList<Rectangle> tail;
    private boolean canMove;
    private Image headImage;

    Snake(int SQUARE_SIZE) {
        this.SQUARE_SIZE = SQUARE_SIZE;
        this.direction = Direction.RIGHT;
        this.tail = new ArrayList<>();
        canMove = true;

    }

    public void addNewSnakePart() {
        Rectangle newSegment = new Rectangle(SQUARE_SIZE, SQUARE_SIZE, Color.web("4775EB"));
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

        // sets the position based on which direction the snake moves
        switch (direction) {
            // if its moving up it will put the square at the same x cooridnate since that
            // doesnt change
            // but put the y cooridnate to be one square belowe the last part and so on for
            // the rest of the directions
            case UP:
                newPart.setX(lastPart.getX());
                newPart.setY(lastPart.getY() + SQUARE_SIZE);
                break;
            case DOWN:
                newPart.setX(lastPart.getX());
                newPart.setY(lastPart.getY() - SQUARE_SIZE);
                break;
            case LEFT:
                newPart.setX(lastPart.getX() + SQUARE_SIZE);
                newPart.setY(lastPart.getY());
                break;
            case RIGHT:
                newPart.setX(lastPart.getX() - SQUARE_SIZE);
                newPart.setY(lastPart.getY());
                break;
            default:
                break;
        }
        // returns the new part that was just added with its correct positions
        return newPart;
    }

    public void setDirection(Direction newDirection) {
        this.direction = newDirection;
    }

    public void updateSnakePosition(Direction newDirection) {
        switch (newDirection) {

            case UP:
                moveUp();
                changeHeadShape("images//HeadUp.png");
                break;

            case DOWN:
                moveDown();
                changeHeadShape("images//HeadDown.png");
                break;

            case LEFT:
                moveLeft();
                changeHeadShape("images//HeadLeft.png");
                break;

            case RIGHT:
                moveRight();
                changeHeadShape("images//HeadRight.png");
                break;
            default:
                break;
        }
    }

    public Direction getDirection() {
        return this.direction;
    }

    public void moveRight() {
        // gets the head sets the x to be the current x position + the square size
        getHead().setX(getHead().getX() + SQUARE_SIZE);
    }

    public void moveLeft() {
        getHead().setX(getHead().getX() - SQUARE_SIZE);
    }

    public void moveUp() {
        getHead().setY(getHead().getY() - SQUARE_SIZE);
    }

    public void moveDown() {
        getHead().setY(getHead().getY() + SQUARE_SIZE);
    }

    public void moveTail() {
        for (int i = tail.size() - 1; i > 0; i--) {
            Rectangle currentPart = tail.get(i);
            Rectangle nextPart = tail.get(i - 1);
            currentPart.setX(nextPart.getX());
            currentPart.setY(nextPart.getY());
        }
    }

    public void changeHeadShape(String newHeadPosition) {
        this.headImage = new Image(newHeadPosition);
        getHead().setFill(new ImagePattern(headImage));
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
