package Game.GameUtils.Utils;

import javafx.scene.input.KeyCode;

public enum ControllButtons {
    UP, DOWN, LEFT, RIGHT;

    public KeyCode getKeyCode(){
        if(this == UP){
            if(Helper.getControls())
                return KeyCode.W;
            else
                return KeyCode.UP;
        }
        if(this == DOWN){
            if(Helper.getControls())
                return KeyCode.S;
            else
                return KeyCode.DOWN;
        }
        if(this == LEFT){
            if(Helper.getControls())
                return KeyCode.A;
            else
                return KeyCode.LEFT;
        }
        if(this == RIGHT){
            if(Helper.getControls())
                return KeyCode.D;
            else
                return KeyCode.RIGHT;
        }
        return null;
    }
}
