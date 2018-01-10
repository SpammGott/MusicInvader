package Game.GameUtils.Utils;

public class Vector2D{



    private double x;
    private double y;

    public Vector2D(){
        x = 0;
        y = 0;
    }

    public Vector2D(double x, double y){
        this.x = x;
        this.y = y;
    }

    public Vector2D(Vector2D vec){
        this.x = vec.getX();
        this.y = vec.getY();
    }

    public void setVec(double x, double y){
        this.x = x;
        this.y = y;
    }

    public boolean xinRange(double min, double max){
        return x <= max && x >= min;
    }

    public boolean yinRange(double min, double max){
        return y <= max && y >= min;
    }

    @Override
    public Vector2D clone(){
        return new Vector2D(x, y);
    }

    public double getX() {return x;}

    public void setX(double x) {this.x = x;}

    public void setXAdd(double x) {this.x += x;}

    public double getY() {return y;}

    public void setY(double y) {this.y = y;}

    public void setYAdd(double y){this.y += y;}

    public double getVectorLength(){
        return Math.sqrt(x*x + y*y);
    }

    public String toString(){
        return "X: " + x + " Y: " + y;
    }

    public Vector2D getRightOrthogonal(){
        return new Vector2D(y, -x);
    }

    public Vector2D getLeftOrthogonal(){
        return new Vector2D(-y, x);
    }
}
