package Game.GameUtils.Entity;

import Game.GameUtils.Utils.ProjectileHandler;
import Game.GameUtils.Utils.Vector2D;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import Game.GameUtils.Utils.Helper;
import Game.GameUtils.Utils.ControllButtons;

public class Player extends Ship {

    private Scene scene;
    private Rectangle body = new Rectangle(50,50);
    //needed to manage movement
    private ProjectileHandler projectileHandler;
    private BooleanProperty up = new SimpleBooleanProperty();
    private BooleanProperty down = new SimpleBooleanProperty();
    private BooleanProperty left = new SimpleBooleanProperty();
    private BooleanProperty right = new SimpleBooleanProperty();

    public Player(Scene scene, ProjectileHandler projectileHandler){
        this.scene = scene;
        this.projectileHandler = projectileHandler;
        body.setFill(Color.WHITE);
        body.setStyle("-fx-background-color: white");

        defSpeed = 7;
        speed = defSpeed;
        //pos = new Vector2D((scene.getWidth() / 2) - (body.getWidth() / 2), (scene.getHeight()));
        pos = new Vector2D((scene.getWidth() / 2) - (body.getWidth() / 2), (scene.getHeight()/2));
        body.setX(pos.getX());
        body.setY(pos.getY());
    }


    @Override
    public void move() {
        if (up.get() && pos.getY() > 0){
            pos.setYAdd(-speed);
            body.setY(pos.getY());
        }
        if (left.get() && pos.getX() > -body.getWidth() /2) {
            pos.setXAdd(-speed);
            body.setX(pos.getX());
        }
        if (down.get() && pos.getY() + Helper.getHeight() * 1.25 < scene.getHeight()){
            pos.setYAdd(speed);
            body.setY(pos.getY());
        }
        if (right.get() && pos.getX() + Helper.getWidth() * 1.25 < scene.getWidth()) {
            pos.setXAdd(speed);
            body.setX(pos.getX());
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
        Projectile temp = new Projectile(new Vector2D(pos.getX() + body.getWidth()/2, pos.getY()), new Vector2D(0,-5), true);
        projectileHandler.addProjectile(temp);
    }

    public Rectangle getBody(){return this.body;}

}
