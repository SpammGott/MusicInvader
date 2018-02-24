package Game.GameUtils.Entity;

import Game.GameUtils.Utils.Helper;
import Game.GameUtils.Utils.Hitbox;
import Game.GameUtils.Utils.MathUtils;
import Game.GameUtils.Utils.Vector2D;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Projectiles which are used by every other entity
 */
public class Projectile extends Entity {

    private boolean isFromPlayer;
    private double rotate;

    /**
     * Konstruktor für Projectile,
     *
     */
    public Projectile(Vector2D pos, Vector2D direction, Image image){
        this.pos = pos.clone();
        this.direction = MathUtils.getEinheitsvektor(direction);
        this.isFromPlayer = false;
        height = 0.01;
        width = 0.005;
        body = new ImageView(image);
        body.setX(Helper.getAbsoluteWidth(pos.getX()));
        body.setY(Helper.getAbsoluteWidth(pos.getY()));
        rotate = MathUtils.angleForProjectils(direction);
        body.setRotate(direction.getX() >= 0 ? -rotate : rotate);
        defSpeed = 0.1;
        speed = defSpeed;
        init();
        hitbox = new Hitbox(pos, 4, height, width, direction);
    }

    public Projectile(Vector2D pos, Vector2D direction, Image image, boolean isFromPlayer){
        this.pos = pos.clone();
        this.direction = MathUtils.getEinheitsvektor(direction);
        this.isFromPlayer = isFromPlayer;
        height = 0.01;
        width = 0.005;
        body = new ImageView(image);
        body.setX(Helper.getAbsoluteWidth(pos.getX()));
        body.setY(Helper.getAbsoluteHeight(pos.getY()));
        defSpeed = 0.2;
        if(isFromPlayer)
            speed = defSpeed;
        else
            speed = defSpeed / 2;
        init();
        hitbox = new Hitbox(pos, 4, height, width, direction);
    }

    @Override
    public void move() {
        Vector2D tempDirection = MathUtils.multVector(direction, speed);
        pos.add(tempDirection);
        hitbox.update(tempDirection);
        body.setX(Helper.getAbsoluteWidth(pos.getX()));
        body.setY(Helper.getAbsoluteHeight(pos.getY()));
        center.add(tempDirection);
    }
}
