package Game.GameUtils.Entity;

import Game.GameUtils.Utils.Vector2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Enemy extends Ship {

    private Rectangle body = new Rectangle(25,25);
    private Vector2D movementDirection = new Vector2D();

    public Enemy(Vector2D pos, Vector2D movementDirection){
        this.pos = pos;
        this.movementDirection = movementDirection;
        body.setX(pos.getX());
        body.setY(pos.getY());
        body.setFill(Color.RED);
        defSpeed = 7;
        speed = defSpeed;
    }

    @Override
    public void move() {
        pos.setXAdd(movementDirection.getX() * speed);
        pos.setYAdd(movementDirection.getY() * speed);
        body.setX(pos.getX());
        body.setY(pos.getY());
        body.setFill(Color.RED);
    }

    @Override
    public void fireProjectile() {

    }

    public void fireProjectile(Vector2D direction){

    }
}
