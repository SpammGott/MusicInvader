package Game.GameUtils.Entity;

import Game.GameUtils.Utils.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 * Enemy class
 * Contains almost everything related to an enemy like spawnpoint, image, size, direction, usw
 */
public class Enemy extends Ship {

    private Spawnpoint homeSpawnPoint;
    private Image explosion[];

    public Enemy(Spawnpoint homeSpawnPoint, Image image, Image explosion[]){
        this.homeSpawnPoint = homeSpawnPoint;
        this.pos = new Vector2D(this.homeSpawnPoint.getPos());
        this.direction = new Vector2D(this.homeSpawnPoint.getRandomDirection());
        this.explosion = explosion;
        height = 0.6;
        width = 0.6;
        body = new ImageView(image);
        defSpeed = 0.125;
        speed = defSpeed;
        init();
        hitbox = new Hitbox(pos, 3, height, width);
    }

    @Override
    public void move() {
        Vector2D tempDirection = MathUtils.multVector(direction, speed);
        pos.add(tempDirection);
        hitbox.update(tempDirection);
        if(!pos.xinRange(-1, 17) || !pos.yinRange(-1, 17)){
            pos.setVec(homeSpawnPoint.getPos().getX(), homeSpawnPoint.getPos().getY());
            hitbox.update(pos, direction);
        }
        body.setX(Helper.getAbsoluteWidth(pos.getX()));
        body.setY(Helper.getAbsoluteHeight(pos.getY()));
        center.setVec(pos.getX() + width/2, pos.getY() + height/2);
    }

    @Override
    public Projectile[] fireProjectile(Image image) {
        Projectile temp[] = {new Projectile(center, new Vector2D(0, -1), image)};
        return temp;

    }

    public Projectile[] fireProjectile(Image image, Vector2D direction){
        Projectile temp[] = {new Projectile(center, direction, image)};
        return temp;
    }

    public ImageView getBody(){return this.body;}
}
