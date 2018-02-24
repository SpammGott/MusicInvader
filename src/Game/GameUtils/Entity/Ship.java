package Game.GameUtils.Entity;

import Game.GameUtils.Utils.Hitbox;
import javafx.scene.image.Image;

/**
 * Abstract class which extends Entity
 * Can fire and has a Hitbox
 */
public abstract class Ship extends Entity{

    /**
     * feuert ein Projektil ab
     * @param image bild des projectils
     * @return ein Array von Projektillen
     */
    public abstract Projectile[] fireProjectile(Image image);

    /**
     * checkt ob das aktuelle Ship(Gegner oder Player) getroffen wird von einer zweiten Hitbox
     * @param hitbox2 die zu überprüfende Hitbox
     * @return ob getroffen worden oder nicht
     */
    protected boolean isHit(Hitbox hitbox2){
        return hitbox.isHit(hitbox2);
    }
}
