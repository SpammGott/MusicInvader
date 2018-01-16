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
        axis = new Vector2D[2];
        if(direction != null){
            //mittelpunkt auf der y-achse berechnen. Pos + Richtung*(breite/2)
            center = new Vector2D(MathUtils.addVector(pos, MathUtils.multVector(direction, width/2)));
            //mittelpunkt auf der x-achse berechnen. Pos + Rechte Normale der Richtung * Höhe/2
            center = MathUtils.addVector(center, MathUtils.multVector(direction.getRightOrthogonal(), height/2));

            point[0] = new Vector2D(pos.getX(), pos.getY());
            //Punkt0 + Richtung*Breite
            point[1] = new Vector2D(MathUtils.addVector(pos, MathUtils.multVector(direction, width)));
            //Punkt1 + Rechte Normale der Richtung * Höhe
            point[2] = new Vector2D(MathUtils.addVector(point[1], MathUtils.multVector(direction.getRightOrthogonal(), height)));
            //Punkt0 + Rechte Normale der Richtung * Höhe
            point[3] = new Vector2D(MathUtils.addVector(pos, MathUtils.multVector(direction, height)));

            axis[0] = new Vector2D(direction.getRightOrthogonal());
            axis[1] = new Vector2D(direction);
        } else {
            center = new Vector2D(pos.getX() + width/2, pos.getY() + height/2);

            point[0] = new Vector2D(pos.getX(), pos.getY());
            point[1] = new Vector2D(pos.getX() + width, pos.getY());
            point[2] = new Vector2D(point[1].getX(), point[1].getY() + height);
            point[3] = new Vector2D(pos.getX(), pos.getY() + height);

            axis[0] = new Vector2D(1 , 0);
            axis[1] = new Vector2D(0, 1);
        }
    }

    private void dreieck(Vector2D pos, double height, double width, Vector2D direction){
        point = new Vector2D[3];
        axis = new Vector2D[3];
        if(direction != null){
            //mittelpunkt auf der y-achse berechnen. Pos + Richtung*(breite/2)
            center = new Vector2D(MathUtils.addVector(pos, MathUtils.multVector(direction, width/2)));
            //mittelpunkt auf der x-achse berechnen. Pos + Rechte Normale der Richtung * Höhe/2
            center = MathUtils.addVector(center, MathUtils.multVector(direction.getRightOrthogonal(), height/2));

            point[0] = new Vector2D(pos.getX(), pos.getY());
            //Punkt0 + Richtung*Breite
            point[1] = new Vector2D(MathUtils.addVector(pos, MathUtils.multVector(direction, width)));
            //Punkt0 + Rechte Normale der Richtung * Höhe
            point[2] = new Vector2D(MathUtils.addVector(pos, MathUtils.multVector(direction.getRightOrthogonal(), height)));
            //Ans + Richtung * (breite/2)
            point[2] = MathUtils.addVector(point[2], MathUtils.multVector(direction, width/2));

            axis[0] = direction.getLeftOrthogonal();
            //Vektor zwischen Punkt1 und Punkt2
            axis[1] = MathUtils.addVector(MathUtils.multVector(direction.getRightOrthogonal(), height), MathUtils.multVector(direction.getInverted(), width/2));
            axis[1] = axis[1].getLeftOrthogonal();
            //Vektor zwischen Punkt0 und Punkt2
            axis[2] = MathUtils.addVector(MathUtils.multVector(direction.getRightOrthogonal(), height), MathUtils.multVector(direction, width/2));
            axis[2] = axis[2].getRightOrthogonal();
        } else {
            center = new Vector2D(pos.getX() + width/2, pos.getY() + height/2);

            point[0] = new Vector2D(pos.getX(), pos.getY());
            point[1] = new Vector2D(pos.getX() + width, pos.getY());
            point[2] = new Vector2D(pos.getX() + width/2, pos.getY() + height);

            axis[0] = new Vector2D(0, -1);
            axis[1] = MathUtils.getEinheitsvektor(new Vector2D(height, -(width/2))).getLeftOrthogonal();
            axis[2] = MathUtils.getEinheitsvektor(new Vector2D(height, width/2)).getRightOrthogonal();

        }

    }

}
