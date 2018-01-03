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
        if(direction.getX() != 0)
            this.direction.setX(1);
        if(direction.getY() != 0)
            this.direction.setY(1);
        this.isFromPlayer = false;
        body.setCenterX(Helper.getAbsoluteWidth(pos.getX()));
        body.setCenterY(Helper.getAbsoluteWidth(pos.getY()));
        body.setFill(Color.WHITE);
        defSpeed = 0.2;
        speed = defSpeed;
    }

    public Projectile(Vector2D pos, Vector2D direction, boolean isFromPlayer){
        this.pos = pos;
        if(direction.getX() != 0)
            this.direction.setX(direction.getX() > 0 ? 1 : -1);
        if(direction.getY() != 0)
            this.direction.setY(direction.getY() > 0 ? 1 : -1);
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
