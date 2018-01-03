package Game.GameUtils.Entity;

import Game.GameUtils.Utils.Vector2D;
import javafx.scene.shape.Shape;

public abstract class Entity {

    protected double defSpeed;
    protected double speed;
    protected Vector2D pos;
    protected Shape body;
    //private Hitbox hitbox;

    public abstract void move();

    //public abstract void changeSpeed(double x);


    public double getDefSpeed() {return defSpeed;}

    public double getSpeed() {return speed;}

    public void setSpeed(double speed) {this.speed = speed;}

    public Vector2D getPos() {return pos;}

    public void setPos(Vector2D pos) {this.pos = pos;}
}
