package Game.GameUtils.Entity;

import Game.GameUtils.Utils.Helper;
import Game.GameUtils.Utils.Vector2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;


public class Projectile extends Entity {

    private boolean isFromPlayer;
    private Ellipse body = new Ellipse(8,15);
    private Vector2D direction = new Vector2D();

    public Projectile(Vector2D pos, Vector2D direction){
        this.pos = pos;
        if(direction.getX() == 1 || direction.getX() == -1)
            this.direction.setX(direction.getX());
        else if(direction.getX() != 0)
            this.direction.setX(direction.getX() - (int)direction.getX());
        if(direction.getY() == 1 || direction.getY() == -1)
            this.direction.setY(direction.getY());
        else if(direction.getY() != 0)
            this.direction.setY(direction.getY() - (int)direction.getY());
        this.isFromPlayer = false;
        body.setCenterX(Helper.getAbsoluteWidth(pos.getX()));
        body.setCenterY(Helper.getAbsoluteWidth(pos.getY()));
        body.setFill(Color.WHITE);
        defSpeed = 0.2;
        speed = defSpeed;
    }

    public Projectile(Vector2D pos, Vector2D direction, boolean isFromPlayer){
        this.pos = pos;
        if(direction.getX() == 1 || direction.getX() == -1)
            this.direction.setX(direction.getX());
        else if(direction.getX() != 0)
            this.direction.setX(direction.getX() - (int)direction.getX());
        if(direction.getY() == 1 || direction.getY() == -1)
            this.direction.setY(direction.getY());
        else if(direction.getY() != 0)
            this.direction.setY(direction.getY() - (int)direction.getY());
        this.isFromPlayer = isFromPlayer;
        body.setCenterX(Helper.getAbsoluteWidth(pos.getX()));
        body.setCenterY(Helper.getAbsoluteHeight(pos.getY()));
        body.setFill(Color.WHITE);
        defSpeed = 0.2;
        speed = defSpeed;
    }

    @Override
    public void move() {
        pos.setXAdd(direction.getX() * speed);
        pos.setYAdd(direction.getY() * speed);
        body.setCenterX(Helper.getAbsoluteWidth(pos.getX()));
        body.setCenterY(Helper.getAbsoluteHeight(pos.getY()));
        body.setFill(Color.WHITE);
    }

    public boolean isFromPlayer(){return this.isFromPlayer;}

    public Ellipse getBody(){return this.body;}
}
