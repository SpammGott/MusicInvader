package Game.GameUtils.Entity;

import Game.GameUtils.Utils.*;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;


public class Player extends Ship {

    private IntegerProperty hp = new SimpleIntegerProperty();

    //needed to manage movement
    private BooleanProperty up = new SimpleBooleanProperty();
    private BooleanProperty down = new SimpleBooleanProperty();
    private BooleanProperty left = new SimpleBooleanProperty();
    private BooleanProperty right = new SimpleBooleanProperty();

    public Player(Image image){
        hp.setValue(3);
        height = 0.7;
        width = 0.7;
        body = new ImageView(image);
        body.setPreserveRatio(true);
        body.setFitHeight(70);
        defSpeed = 0.125;
        speed = defSpeed;
        pos = new Vector2D(7.5-(width/2), 16-height);
        init();
        hitbox = new Hitbox(pos, 4, height, width);
    }

    @Override
    public void move() {
        direction = new Vector2D();
        if (up.get() && pos.getY() > 0){
            pos.setYAdd(-speed);
            body.setY(Helper.getAbsoluteHeight(pos.getY()));
            direction.setYAdd(-speed);
        }
        if (left.get() && pos.getX() > 0) {
            pos.setXAdd(-speed);
            body.setX(Helper.getAbsoluteWidth(pos.getX()));
            direction.setXAdd(-speed);
        }
        if (down.get() && pos.getY() < 16 - height){
            pos.setYAdd(speed);
            body.setY(Helper.getAbsoluteHeight(pos.getY()));
            direction.setYAdd(speed);
        }
        if (right.get() && pos.getX() < 16 - width) {
            pos.setXAdd(speed);
            body.setX(Helper.getAbsoluteWidth(pos.getX()));
            direction.setXAdd(speed);
        }
        hitbox.update(direction);
        center.add(direction);
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
    public Projectile[] fireProjectile(Image image) {
        Projectile temp[] = new Projectile[2];
        temp[0] = new Projectile(new Vector2D((center.getX() - width/4), (center.getY() - width/4)), new Vector2D(0,-1), image, true);
        temp[1] = new Projectile(new Vector2D((center.getX() + width/4), (center.getY() + width/4)), new Vector2D(0,-1), image, true);
        return temp;
    }

    public ImageView getBody(){return this.body;}

    public IntegerProperty getHp(){return hp;}

    public void decHp(){hp.setValue(hp.get() - 1);}

    public void changeImage(Image image){
        body.setImage(image);
    }
}
