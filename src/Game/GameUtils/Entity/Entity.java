package Game.GameUtils.Entity;

import Game.GameUtils.Utils.Helper;
import Game.GameUtils.Utils.Hitbox;
import Game.GameUtils.Utils.Vector2D;
import javafx.scene.image.ImageView;

/**
 * The Entity class is a abstract class to handle entitys.
 * Things like speed, pos, size and direction
 */
public abstract class Entity {

    protected double defSpeed;
    protected double speed;
    protected Vector2D pos;
    protected Vector2D direction;
    protected Vector2D center;
    protected double height;
    protected double width;
    protected ImageView body;
    protected Hitbox hitbox;

    public abstract void move();

    //public abstract void changeSpeed(double x);

    protected void init(){
        body.setPreserveRatio(true);
        body.setFitWidth(Helper.getAbsoluteWidth(width));
        body.setFitHeight(Helper.getAbsoluteHeight(height));
        body.setX(Helper.getAbsoluteWidth(pos.getX()));
        body.setY(Helper.getAbsoluteHeight(pos.getY()));
        center = new Vector2D(pos.getX() + width/2, pos.getY() + height/2);
    }


    public double getDefSpeed() {return defSpeed;}

    public double getSpeed() {return speed;}

    public void setSpeed(double speed) {this.speed = speed;}

    public Vector2D getPos() {return pos;}

    public void setPos(Vector2D pos) {
        this.pos = pos;
        body.setX(Helper.getAbsoluteWidth(pos.getX()));
        body.setY(Helper.getAbsoluteHeight(pos.getY()));
        center = new Vector2D(pos.getX() + width/2, pos.getY() + height/2);
        hitbox = new Hitbox(pos, 4, height, width);
    }

    public double getHeight() {return height;}

    //public void setHeight(double height) {this.height = height;}

    public double getWidth() {return width;}

    //public void setWidth(double width) {this.width = width;}

    public ImageView getBody() {return body;}

    //public void setBody(ImageView body) {this.body = body;}

    public Hitbox getHitbox() {return hitbox;}

    public void delHitbox(){hitbox.update(new Vector2D(-1000, -1000));}
}
