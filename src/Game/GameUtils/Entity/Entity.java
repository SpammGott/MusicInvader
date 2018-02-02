package Game.GameUtils.Entity;

import Game.GameUtils.Utils.Helper;
import Game.GameUtils.Utils.Hitbox;
import Game.GameUtils.Utils.Vector2D;
import javafx.scene.image.ImageView;


public abstract class Entity {

    double defSpeed;
    double speed;
    Vector2D direction;
    double height;
    double width;
    Hitbox hitbox;

    protected ImageView body;
    protected Vector2D center;
    protected Vector2D pos;

    public abstract void move();

    void init(){
        body.setPreserveRatio(true);
        body.setFitWidth(Helper.getAbsoluteWidth(width));
        body.setFitHeight(Helper.getAbsoluteHeight(height));
        body.setX(Helper.getAbsoluteWidth(pos.getX()));
        body.setY(Helper.getAbsoluteHeight(pos.getY()));
        center = new Vector2D(pos.getX() + width/2, pos.getY() + height/2);
    }

    public Vector2D getPos() {return pos;}

    public void setPos(Vector2D pos) {
        this.pos = pos;
        body.setX(Helper.getAbsoluteWidth(pos.getX()));
        body.setY(Helper.getAbsoluteHeight(pos.getY()));
        center = new Vector2D(pos.getX() + width/2, pos.getY() + height/2);
        hitbox = new Hitbox(pos, 4, height, width);
    }

    public double getHeight() {return height;}

    public double getWidth() {return width;}

    public ImageView getBody() {return body;}

    public Hitbox getHitbox() {return hitbox;}

    public void delHitbox(){hitbox.update(new Vector2D(-1000, -1000));}
}
