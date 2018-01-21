package Game.GameUtils.Entity;

import Game.GameUtils.Utils.Hitbox;
import javafx.scene.image.Image;

public abstract class Ship extends Entity{

    public abstract Projectile[] fireProjectile(Image image);


    protected boolean isHit(Hitbox hitbox2){
        return hitbox.isHit(hitbox2);
    }

    //public abstract boolean isHit(Hitbox enemyHitbox);
}
