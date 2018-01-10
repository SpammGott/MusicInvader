package Game.GameUtils.Entity;

import Game.GameUtils.Utils.Helper;
import Game.GameUtils.Utils.MathUtils;
import Game.GameUtils.Utils.Vector2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;


public class Projectile extends Entity {

    private boolean isFromPlayer;
    private Rectangle body = new Rectangle(4,8);
    private Vector2D direction;

    public Projectile(Vector2D pos, Vector2D direction){
        this.pos = pos.clone();
        this.direction = MathUtils.getEinheitsvektor(direction);
        this.isFromPlayer = false;
        body.setX(Helper.getAbsoluteWidth(pos.getX()));
        body.setY(Helper.getAbsoluteWidth(pos.getY()));
        body.setFill(Color.WHITE);
        double rotate = MathUtils.angleForProjectils(direction);
        body.setRotate(direction.getX() >= 0 ? -rotate : rotate);
        defSpeed = 0.1;
        speed = defSpeed;
    }

    public Projectile(Vector2D pos, Vector2D direction, boolean isFromPlayer){
        this.pos = pos.clone();
        this.direction = MathUtils.getEinheitsvektor(direction);
        this.isFromPlayer = isFromPlayer;
        body.setX(Helper.getAbsoluteWidth(pos.getX()));
        body.setY(Helper.getAbsoluteHeight(pos.getY()));
        body.setFill(Color.WHITE);
        defSpeed = 0.2;
        speed = defSpeed;
    }

    @Override
    public void move() {
        pos.setXAdd(direction.getX() * speed);
        pos.setYAdd(direction.getY() * speed);
        body.setX(Helper.getAbsoluteWidth(pos.getX()));
        body.setY(Helper.getAbsoluteHeight(pos.getY()));
        body.setFill(Color.WHITE);
    }

    public boolean isFromPlayer(){return this.isFromPlayer;}

    public Rectangle getBody(){return this.body;}
}
