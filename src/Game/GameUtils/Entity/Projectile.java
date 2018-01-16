package Game.GameUtils.Entity;

import Game.GameUtils.Utils.Helper;
import Game.GameUtils.Utils.MathUtils;
import Game.GameUtils.Utils.Vector2D;
import javafx.scene.paint.Color;

import javafx.scene.shape.Rectangle;


public class Projectile extends Entity {

    private boolean isFromPlayer;
    private Rectangle body = new Rectangle(4,8);
    private double rotate;

    public Projectile(Vector2D pos, Vector2D direction){
        this.pos = pos.clone();
        this.direction = MathUtils.getEinheitsvektor(direction);
        this.isFromPlayer = false;
        body.setX(Helper.getAbsoluteWidth(pos.getX()));
        body.setY(Helper.getAbsoluteWidth(pos.getY()));
        body.setFill(Color.WHITE);
        rotate = MathUtils.angleForProjectils(direction);
        body.setRotate(direction.getX() >= 0 ? -rotate : rotate);
        defSpeed = 0.1;
        speed = defSpeed;
        hitbox = new Hitbox(pos, 4, height, width, direction);
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
        hitbox = new Hitbox(pos, 4, height, width, direction);
    }

    @Override
    public void move() {
        Vector2D tempDirection = MathUtils.multVector(direction, speed);
        pos.add(tempDirection);
        hitbox.update(tempDirection);
        body.setX(Helper.getAbsoluteWidth(pos.getX()));
        body.setY(Helper.getAbsoluteHeight(pos.getY()));
        body.setFill(Color.WHITE);
    }

    public boolean isFromPlayer(){return this.isFromPlayer;}

    public Rectangle getBody(){return this.body;}
}
