package Game.GameUtils.Entity;

import Game.GameUtils.Utils.Helper;
import Game.GameUtils.Utils.ProjectileHandler;
import Game.GameUtils.Utils.Vector2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Enemy extends Ship {

    private Rectangle body = new Rectangle(Helper.getAbsoluteWidth(0.4),Helper.getAbsoluteHeight(0.4));
    private Vector2D direction;
    private Spawnpoint homeSpawnPoint;
    private ProjectileHandler projectileHandler;

    public Enemy(Spawnpoint homeSpawnPoint, ProjectileHandler projectileHandler){
        this.homeSpawnPoint = homeSpawnPoint;
        this.pos = this.homeSpawnPoint.getPos();
        this.direction = this.homeSpawnPoint.getRandomDirection();
        this.projectileHandler = projectileHandler;
        body.setX(Helper.getAbsoluteWidth(pos.getX()-0.2));
        body.setY(Helper.getAbsoluteHeight(pos.getY()));
        body.setFill(Color.RED);
        defSpeed = 0.05;
        speed = defSpeed;
    }

    @Override
    public void move() {
        pos.setXAdd(direction.getX() * speed);
        pos.setYAdd(direction.getY() * speed);
        if(!pos.xinRange(0, 16) && !pos.yinRange(0, 16)){
            pos.setVec(homeSpawnPoint.getPos().getX(), homeSpawnPoint.getPos().getY());
        }
        body.setX(Helper.getAbsoluteWidth(pos.getX()));
        body.setY(Helper.getAbsoluteHeight(pos.getY()));
        body.setFill(Color.RED);
        //System.out.println("Enemy:" + toString() + "\tpos: " + pos.toString() + "\tspeed: " + speed + "\trichtung: " + direction.toString());
    }

    @Override
    public void fireProjectile() {

    }

    public void fireProjectile(Vector2D direction){
        Projectile temp = new Projectile(pos, direction);
        projectileHandler.addProjectile(temp);
    }

    public Rectangle getBody(){return this.body;}

    public String toString2(){
        return "Enemy:" + toString() + "\tpos: " + pos.toString() + "\tspeed: " + speed + "\trichtung: " + direction.toString();
    }
}
