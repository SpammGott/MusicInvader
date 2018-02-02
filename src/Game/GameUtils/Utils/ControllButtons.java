package Game.GameUtils.Utils;

import javafx.scene.input.KeyCode;

/**
 * Allows easier handling of different control patterns
 */
public enum ControllButtons {
    UP, DOWN, LEFT, RIGHT;

    public KeyCode getKeyCode(){
        KeyCode key;
        if(this == UP){
            if(Helper.getControls())
                return key = KeyCode.W;
            else
                return key = KeyCode.UP;
        }
        if(this == DOWN){
            if(Helper.getControls())
                return key = KeyCode.S;
            else
                return key = KeyCode.DOWN;
        }
        if(this == LEFT){
            if(Helper.getControls())
                return key = KeyCode.A;
            else
                return key = KeyCode.LEFT;
        }
        if(this == RIGHT){
            if(Helper.getControls())
                return key = KeyCode.D;
            else
                return key = KeyCode.RIGHT;
        }
        return null;
    }
}
