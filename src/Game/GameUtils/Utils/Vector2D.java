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

    public void setVec(double x, double y){
        this.x = x;
        this.y = y;
    }

    public double getX() {return x;}

    public void setX(double x) {this.x = x;}

    public void setXAdd(double x) {this.x += x;}

    public double getY() {return y;}

    public void setY(double y) {this.y = y;}

    public void setYAdd(double y){this.y += y;}
}