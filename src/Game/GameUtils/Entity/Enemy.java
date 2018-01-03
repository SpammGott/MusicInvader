package Game.GameUtils.Entity;

import Game.GameUtils.Utils.Helper;
import Game.GameUtils.Utils.Vector2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Enemy extends Ship {

    private Rectangle body = new Rectangle(Helper.getAbsoluteWidth(0.4),Helper.getAbsoluteHeight(0.4));
    private Vector2D movementDirection = new Vector2D();
    private Spawnpoint homeSpawnPoint;

    public Enemy(Spawnpoint homeSpawnPoint){

        this.homeSpawnPoint = homeSpawnPoint;
        this.pos = homeSpawnPoint.getPos();
        this.movementDirection = homeSpawnPoint.getRandomDirection();
        body.setX(Helper.getAbsoluteWidth(pos.getX()-0.125));
        body.setY(Helper.getAbsoluteHeight(pos.getY()));
        body.setFill(Color.RED);
        defSpeed = 0.05;
        speed = defSpeed;
    }

    @Override
    public void move() {
        pos.setXAdd(movementDirection.getX() * speed);
        pos.setYAdd(movementDirection.getY() * speed);
        if(pos.xinRange(0, 16) && pos.yinRange(0, 16)){
            body.setX(Helper.getAbsoluteWidth(pos.getX()));
            body.setY(Helper.getAbsoluteHeight(pos.getY()));
        } else{
            body.setX(Helper.getAbsoluteWidth(homeSpawnPoint.getPos().getX()));
            body.setY(Helper.getAbsoluteHeight(homeSpawnPoint.getPos().getY()));
        }
        body.setFill(Color.RED);
        System.out.println("Enemy:" + toString() + "\tpos: " + pos.toString() + "\tspeed: " + speed);
    }

    @Override
    public void fireProjectile() {

    }

    public void fireProjectile(Vector2D direction){

    }

    public Rectangle getBody(){return this.body;}
}
