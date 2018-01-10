package Game.GameUtils.Entity;

import Game.GameUtils.Utils.Helper;
import Game.GameUtils.Utils.ProjectileHandler;
import Game.GameUtils.Utils.Vector2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class Enemy extends Ship {

    private Vector2D direction;
    private Spawnpoint homeSpawnPoint;
    private ProjectileHandler projectileHandler;

    public Enemy(Spawnpoint homeSpawnPoint, ProjectileHandler projectileHandler, Image image){
        this.homeSpawnPoint = homeSpawnPoint;
        this.pos = new Vector2D(this.homeSpawnPoint.getPos());
        this.direction = new Vector2D(this.homeSpawnPoint.getRandomDirection());
        this.projectileHandler = projectileHandler;
        height = 0.4;
        width = 0.4;
        body = new ImageView(image);
        defSpeed = 0.05;
        speed = defSpeed;
        init();
    }

    @Override
    public void move() {
        pos.setXAdd(direction.getX() * speed);
        pos.setYAdd(direction.getY() * speed);
        if(!pos.xinRange(-1, 17) || !pos.yinRange(-1, 17)){
            pos.setVec(homeSpawnPoint.getPos().getX(), homeSpawnPoint.getPos().getY());
        }
        body.setX(Helper.getAbsoluteWidth(pos.getX()));
        body.setY(Helper.getAbsoluteHeight(pos.getY()));
    }

    @Override
    public void fireProjectile() {

    }

    public void fireProjectile(Vector2D direction){
        Projectile temp = new Projectile(pos, direction);
        projectileHandler.addProjectile(temp);
    }

    public ImageView getBody(){return this.body;}

    public String toString2(){
        return "Enemy:" + toString() + "\tpos: " + pos.toString() + "\tspeed: " + speed + "\trichtung: " + direction.toString();
    }
}
