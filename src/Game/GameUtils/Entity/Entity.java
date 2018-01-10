package Game.GameUtils.Entity;

import Game.GameUtils.Utils.Vector2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public abstract class Entity {

    protected double defSpeed;
    protected double speed;
    protected Vector2D pos;
    protected double height;
    protected double width;
    protected ImageView body;
    protected Image image;
    //private Hitbox hitbox;

    public abstract void move();

    //public abstract void changeSpeed(double x);


    public double getDefSpeed() {return defSpeed;}

    public double getSpeed() {return speed;}

    public void setSpeed(double speed) {this.speed = speed;}

    public Vector2D getPos() {return pos;}

    public void setPos(Vector2D pos) {this.pos = pos;}

    public double getHeight() {return height;}

    //public void setHeight(double height) {this.height = height;}

    public double getWidth() {return width;}

    //public void setWidth(double width) {this.width = width;}
}
