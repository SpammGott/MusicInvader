package Game.GameUtils.Entity;

import Game.GameUtils.Utils.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class Enemy extends Ship {

    private Spawnpoint homeSpawnPoint;

    public Enemy(Spawnpoint homeSpawnPoint, Image image){
        this.homeSpawnPoint = homeSpawnPoint;
        this.pos = new Vector2D(this.homeSpawnPoint.getPos());
        this.direction = new Vector2D(this.homeSpawnPoint.getRandomDirection());
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
    public Projectile fireProjectile(Image image) {
        return new Projectile(center, new Vector2D(0, -1), image);

    }

    public Projectile fireProjectile(Image image, Vector2D direction){
        return new Projectile(center, direction, image);
    }

    public ImageView getBody(){return this.body;}

    public String toString2(){
        return "Enemy:" + toString() + "\tpos: " + pos.toString() + "\tspeed: " + speed + "\trichtung: " + direction.toString();
    }
}
