package Game.GameUtils.Entity;

import Game.GameUtils.Utils.Helper;
import Game.GameUtils.Utils.MathUtils;
import Game.GameUtils.Utils.Vector2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;


public class Projectile extends Entity {

    private boolean isFromPlayer;
    private Ellipse body = new Ellipse(8,15);
    private Vector2D direction;

    public Projectile(Vector2D pos, Vector2D direction){
        this.pos = pos.clone();
        this.direction = MathUtils.getEinheitsvektor(direction);
        this.isFromPlayer = false;
        body.setCenterX(Helper.getAbsoluteWidth(pos.getX()));
        body.setCenterY(Helper.getAbsoluteWidth(pos.getY()));
        body.setFill(Color.WHITE);
        defSpeed = 0.1;
        speed = defSpeed;
    }

    public Projectile(Vector2D pos, Vector2D direction, boolean isFromPlayer){
        this.pos = pos.clone();
        this.direction = MathUtils.getEinheitsvektor(direction);
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
