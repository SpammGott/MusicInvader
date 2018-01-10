package Game.GameUtils.Entity;

import Game.GameUtils.Utils.MathUtils;
import Game.GameUtils.Utils.Vector2D;

public class Hitbox{

    private Vector2D center;
    private Vector2D point[];
    private Vector2D axis[];

    public Hitbox(Vector2D pos, int anzahlPoints, double height, double width) {
        if(anzahlPoints == 4)
            viereck(pos, height, width, null);
        else if(anzahlPoints == 3)
            dreieck(pos, height, width, null);
        //throws exception?
    }

    public Hitbox(Vector2D pos, int anzahlPoints, double height, double width, Vector2D direction){
        if(anzahlPoints == 4)
            viereck(pos, height, width, direction);
        else if(anzahlPoints == 3)
            dreieck(pos, height, width, direction);
        //throws exception?
    }

    private void viereck(Vector2D pos, double height, double width, Vector2D direction){
        point = new Vector2D[4];
        if(direction != null){
            center = new Vector2D(MathUtils.addVector(pos, MathUtils.multVector(direction, width/2)));
            center = MathUtils.addVector(center, MathUtils.multVector(direction.getRightOrthogonal(), height/2));
            point[0] = new Vector2D(pos.getX(), pos.getY());
            point[1] = new Vector2D(MathUtils.addVector(pos, MathUtils.multVector(direction, width)));
            point[2] = new Vector2D(MathUtils.addVector(point[1], MathUtils.multVector(direction.getRightOrthogonal(), height)));
            point[3] = new Vector2D(MathUtils.addVector(pos, MathUtils.multVector(direction, height)));
        } else {
            center = new Vector2D(pos.getX() + width/2, pos.getY() + height/2);
            point[0] = new Vector2D(pos.getX(), pos.getY());
            point[1] = new Vector2D(pos.getX() + width, pos.getY());
            point[2] = new Vector2D(point[1].getX(), point[1].getY() + height);
            point[3] = new Vector2D(pos.getX(), pos.getY() + height);
        }
    }

    private void dreieck(Vector2D pos, double height, double width, Vector2D direction){
        point = new Vector2D[3];
        if(direction != null){
            center = new Vector2D(MathUtils.addVector(pos, MathUtils.multVector(direction, width/2)));
            center = MathUtils.addVector(center, MathUtils.multVector(direction.getRightOrthogonal(), height/2));
            point[0] = new Vector2D(pos.getX(), pos.getY());
            point[1] = new Vector2D(MathUtils.addVector(pos, MathUtils.multVector(direction, width)));
            point[2] = new Vector2D(MathUtils.addVector(pos, MathUtils.multVector(direction.getRightOrthogonal(), height)));
            point[2] = MathUtils.addVector(point[2], MathUtils.multVector(direction, width));
        } else {
            center = new Vector2D(pos.getX() + width/2, pos.getY() + height/2);
            point[0] = new Vector2D(pos.getX(), pos.getY());
            point[1] = new Vector2D(pos.getX() + width, pos.getY());

            point[2] = new Vector2D(pos.getX() + width/2, pos.getY() + height);
        }

    }

}
