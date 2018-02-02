package Game.GameUtils.Utils;

/**
 * Math utils which are needed to move and aim projectiles and other entitys
 */
public class MathUtils {

    public static Vector2D getEinheitsvektor(Vector2D vec){
        double length = vec.getVectorLength();
        double x = vec.getX() / length;
        double y = vec.getY() / length;
        return new Vector2D(x, y);
    }

    public static double skalarprodukt(Vector2D a, Vector2D b){
        return a.getX() * b.getX() + a.getY() * b.getY();
    }

    public static double angleForProjectils(Vector2D direction){
        return angleBetweenVectors(direction, new Vector2D(0, 1));
    }

    public static double angleBetweenVectors(Vector2D a, Vector2D b){
        double ret = skalarprodukt(a, b);
        ret = ret / (a.getVectorLength() * b.getVectorLength());
        ret =  Math.toDegrees(Math.acos(ret));
        return ret;
    }

    public static Vector2D addVector(Vector2D a, Vector2D b){
        Vector2D ret = new Vector2D();
        ret.setX(a.getX() + b.getX());
        ret.setY(a.getY() + b.getY());
        return ret;
    }

    public static Vector2D multVector(Vector2D a, double multiplikator){
        Vector2D ret = new Vector2D();
        ret.setX(a.getX() * multiplikator);
        ret.setY(a.getY() * multiplikator);
        return ret;
    }
}
