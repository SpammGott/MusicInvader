package Game.GameUtils;

import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

public class Projectile extends Ellipse {

    public Projectile(Player player){
        super(8, 15);
        setCenterX(player.getX() + player.getWidth() / 2);
        setCenterY(player.getY());
        setFill(Color.WHITE);
    }
}
