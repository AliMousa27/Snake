import java.util.HashMap;

import javafx.scene.control.skin.TextInputControlSkin.Direction;
import javafx.scene.input.KeyCode;

public class Controls {

    private KeyCode up;
    private KeyCode down;
    private KeyCode right;
    private KeyCode left;
    private boolean hasDuplicate;
    private HashMap<KeyCode, Direction> userInput;

    Controls() {
        // starts with the normal controls
        up = KeyCode.UP;
        down = KeyCode.DOWN;
        right = KeyCode.RIGHT;
        left = KeyCode.LEFT;
        // there are no duplicates so its false
        hasDuplicate = false;
        userInput = new HashMap<>();
        // puts the controls in the map
        initalizeControls();

    }

    public void updateKey(String fieldName, KeyCode newKey) {
        // depending on the field name that has called this method, we will assing a new
        // key and check if its a
        // duplicate
        switch (fieldName) {
            case "upField":// if the field id that called is the up field we change the up key
                // checks for if the key is a duplicate
                hasDuplicate = checkForDuplicate(newKey, down, left, right);
                // replaces the up key with the new desired key in the map
                replaceControl(up, newKey);
                // makes up the new key
                up = newKey;
                break;

            case "downField":
                hasDuplicate = checkForDuplicate(newKey, up, left, right);
                replaceControl(down, newKey);
                down = newKey;
                break;

            case "rightField":
                hasDuplicate = checkForDuplicate(newKey, down, left, up);
                replaceControl(right, newKey);
                right = newKey;
                break;

            case "leftField":
                hasDuplicate = checkForDuplicate(newKey, down, up, right);
                replaceControl(left, newKey);
                left = newKey;
                break;
            default:
        }
    }

    // for now this is more modular for when the user wants to replace something but
    // the old ones work as tested
    public void replaceControl(KeyCode oldKey, KeyCode newKey) {

        // if statment to validate if the map doesnt kontain the new key and the old key
        // value value isnt null
        // old value paiur needs to not be null otherwise the user can press many keys
        // at once
        // which will bypass the first part of the if statment since they are new keys
        // but the value of the old key will become null when they do that
        if (!userInput.containsKey(newKey) && userInput.get(oldKey) != null) {
            Direction direction = userInput.get(oldKey);
            userInput.remove(oldKey);
            userInput.put(newKey, direction);

        }
    }

    public boolean checkForDuplicate(KeyCode newKey, KeyCode firsKeyCode, KeyCode secondKeyCode, KeyCode thirdKeyCode) {
        if (newKey == firsKeyCode || newKey == secondKeyCode || newKey == thirdKeyCode) {
            return true;
        }

        return false;
    }

    public void initalizeControls() {
        // adds the defualt controls with the needed directions
        userInput.put(up, Direction.UP);
        userInput.put(down, Direction.DOWN);
        userInput.put(right, Direction.RIGHT);
        userInput.put(left, Direction.LEFT);

    }

    public HashMap<KeyCode, Direction> getUserInput() {
        return userInput;
    }

    public boolean getHasDuplicate() {
        return hasDuplicate;
    }

    public KeyCode getUp() {
        return up;
    }

    public KeyCode getDown() {
        return down;
    }

    public KeyCode getRight() {
        return right;
    }

    public KeyCode getLeft() {
        return left;
    }

}
