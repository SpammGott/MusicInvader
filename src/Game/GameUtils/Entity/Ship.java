package Game.GameUtils.Entity;

import Game.GameUtils.Utils.Helper;

public abstract class Ship extends Entity{

    public abstract void fireProjectile();

    protected void init(){
        body.setPreserveRatio(true);
        body.setFitWidth(Helper.getAbsoluteWidth(width));
        body.setFitHeight(Helper.getAbsoluteHeight(height));
        body.setX(Helper.getAbsoluteWidth(pos.getX()));
        body.setY(Helper.getAbsoluteHeight(pos.getY()));
    }

    protected boolean isHit(Hitbox hitbox2){
        return hitbox.isHit(hitbox2);
    }

    //public abstract boolean isHit(Hitbox enemyHitbox);
}
