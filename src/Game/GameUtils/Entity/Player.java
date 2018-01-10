package Game.GameUtils.Entity;

import Game.GameUtils.Utils.ProjectileHandler;
import Game.GameUtils.Utils.Vector2D;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import Game.GameUtils.Utils.Helper;
import Game.GameUtils.Utils.ControllButtons;


public class Player extends Ship {

    //private Rectangle body;
    //needed to manage movement
    private ProjectileHandler projectileHandler;
    private BooleanProperty up = new SimpleBooleanProperty();
    private BooleanProperty down = new SimpleBooleanProperty();
    private BooleanProperty left = new SimpleBooleanProperty();
    private BooleanProperty right = new SimpleBooleanProperty();

    public Player(ProjectileHandler projectileHandler, Image image){
        this.projectileHandler = projectileHandler;
        height = 0.5;
        width = 0.5;
        body = new ImageView(image);
        defSpeed = 0.125;
        speed = defSpeed;
        pos = new Vector2D(7.5, 15.5);
        init();
    }




    @Override
    public void move() {
        if (up.get() && pos.getY() > 0){
            pos.setYAdd(-speed);
            body.setY(Helper.getAbsoluteHeight(pos.getY()));
        }
        if (left.get() && pos.getX() > 0) {
            pos.setXAdd(-speed);
            body.setX(Helper.getAbsoluteWidth(pos.getX()));
        }
        if (down.get() && pos.getY() < 16 - height){
            pos.setYAdd(speed);
            body.setY(Helper.getAbsoluteHeight(pos.getY()));
        }
        if (right.get() && pos.getX() < 16 - width) {
            pos.setXAdd(speed);
            body.setX(Helper.getAbsoluteWidth(pos.getX()));
        }
    }

    public void changeMovement(KeyEvent keyEvent){
        if(keyEvent.getEventType().equals(KeyEvent.KEY_RELEASED)){
            if(keyEvent.getCode() == ControllButtons.UP.getKeyCode()){
                up.setValue(false);
            }
            if(keyEvent.getCode() == ControllButtons.DOWN.getKeyCode()){
                down.setValue(false);
            }
            if(keyEvent.getCode() == ControllButtons.LEFT.getKeyCode()){
                left.setValue(false);
            }
            if(keyEvent.getCode() == ControllButtons.RIGHT.getKeyCode()){
                right.setValue(false);
            }
        } else if(keyEvent.getEventType().equals(KeyEvent.KEY_PRESSED)){
            if(keyEvent.getCode() == ControllButtons.UP.getKeyCode()){
                up.setValue(true);
            }
            if(keyEvent.getCode() == ControllButtons.DOWN.getKeyCode()){
                down.setValue(true);
            }
            if(keyEvent.getCode() == ControllButtons.LEFT.getKeyCode()){
                left.setValue(true);
            }
            if(keyEvent.getCode() == ControllButtons.RIGHT.getKeyCode()){
                right.setValue(true);
            }
        }
    }

    @Override
    public void fireProjectile() {
        Projectile temp = new Projectile(new Vector2D(pos.getX()+0.25, pos.getY()), new Vector2D(0,-1), true);
        projectileHandler.addProjectile(temp);
    }

    public ImageView getBody(){return this.body;}

}
