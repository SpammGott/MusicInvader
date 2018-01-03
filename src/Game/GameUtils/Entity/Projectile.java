package Game.GameUtils.Entity;

import Game.GameUtils.Utils.Vector2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;


public class Projectile extends Entity {

    private boolean isFromPlayer;
    private Ellipse body = new Ellipse(8,15);
    private Vector2D direction = new Vector2D();

    public Projectile(Vector2D pos, Vector2D direction){
        this.pos = pos;
        if(direction.getX() != 0)
            this.direction.setX(1);
        if(direction.getY() != 0)
            this.direction.setY(1);
        this.isFromPlayer = false;
        body.setCenterX(pos.getX());
        body.setCenterY(pos.getY());
        body.setFill(Color.WHITE);
        defSpeed = 5;
        speed = defSpeed;
    }

    public Projectile(Vector2D pos, Vector2D direction, boolean isFromPlayer){
        this.pos = pos;
        this.direction = direction;
        this.isFromPlayer = isFromPlayer;
        body.setCenterX(pos.getX());
        body.setCenterY(pos.getY());
        body.setFill(Color.WHITE);

    }

    @Override
    public void move() {
        pos.setXAdd(direction.getX() * speed);
        pos.setYAdd(direction.getY() * speed);
        body.setCenterX(pos.getX());
        body.setCenterY(pos.getY());
        body.setFill(Color.WHITE);
    }

    public boolean isFromPlayer(){return this.isFromPlayer;}

    public Ellipse getBody(){return this.body;}
}
